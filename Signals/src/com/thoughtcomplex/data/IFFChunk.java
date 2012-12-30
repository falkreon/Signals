package com.thoughtcomplex.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.thoughtcomplex.io.Streams;

public class IFFChunk {
    ByteBuffer data;
    String tag;
    ArrayList<IFFChunk> contents = new ArrayList<IFFChunk>();
    boolean dataContainsHeader = false;
    
    private IFFChunk() {}
    
    private void init(boolean readTagAndSize) {
        data.rewind();
        int length = data.limit();
        if (readTagAndSize) {
            dataContainsHeader = true;
            int intTag = data.getInt();
            tag = "" + (char)(intTag>>24 & 0xFF) + (char)(intTag>>16 & 0xFF) +
                       (char)(intTag>>8 & 0xFF) +  (char)(intTag & 0xFF);
            length = data.getInt() & 0x7FFFFFFF; //byteOrder(data.getInt());
        }
        System.out.println("Raw Length: "+length);
        if (length<0) length=1;
        if (length%2!=0) length++;
        if (dataContainsHeader && length+8>data.limit()) {
            System.out.println("==DATA PANIC==");
            for(int i=0; i<20; i++) {
                byte b = data.get();
                System.out.println("DATA: "+b+": "+(char)b);
            }
        }
        
        //if (length+8<data.limit())
        data.position(0);
        if (dataContainsHeader) {
            System.out.println("Limiting to "+(length+8));
            data.limit(length+8);
            data.position(8);
        } else {
            data.position(0);
        }
        
        System.out.println("Chunk Type: "+tag+" Length: "+(data.limit())+" (incl. header)");
        
        if (tag.equals("FORM")) {
            int formTypeInt = data.getInt();
            String formType = "" + (char)(formTypeInt>>24 & 0xFF) + (char)(formTypeInt>>16 & 0xFF) +
                                   (char)(formTypeInt>>8 & 0xFF) +  (char)(formTypeInt & 0xFF);
            System.out.println("Form Type: "+formType);
            //there are child chunks
            ByteBuffer tempData = data.slice();
            while(tempData.limit()>0) {
                System.out.println("CONSTRUCTING CHILD...");
                IFFChunk child = new IFFChunk();
                child.data = tempData.slice();
                
                child.init(true);
                contents.add(child);
                int dataSize = child.data.limit();
                if (dataSize%2!=0) dataSize++;
                tempData.position(0);
                
                if (dataSize>=tempData.capacity()) {
                    break;
                } else {
                    tempData.position(dataSize);
                    tempData = tempData.slice();
                }
            }
            System.out.println("==END FORM==");
        }
        
        if (tag.equals("COMT")) {
            int timestamp = data.getInt();  //seconds since Jan 1, 1904 (On Amiga, since Jan 1, 1978)
            int markerID = data.getInt();   //the id of the sample frame marker
            int commentLength = data.getShort() & 0x7FFF;
            System.out.println("Raw Comment Length: "+commentLength);
            if (commentLength>data.limit()-data.position()) commentLength = (short) (data.limit()-data.position());
            System.out.println("Comment Length: "+commentLength);
            
            byte[] commentBytes = new byte[commentLength];
            for(int i=0; i<commentLength; i++) {
                commentBytes[i] = data.get();
            }
            String comment = new String(commentBytes);
            System.out.println("Comment: "+comment);
        }

    }
    
    public String getTag() {
        return tag;
    }
    
    public byte[] getData() {
        data.position(0);
        if (dataContainsHeader) {
            data.position(8);
            byte[] result = new byte[data.limit()-8];
            data.get(result, 0, result.length);
            return result;
        } else {
            byte[] result = new byte[data.limit()];
            data.get(result, 0, result.length);
            return result;
        }
    }
    
    public void write(OutputStream out) throws IOException {
        if (!dataContainsHeader) {
            ByteBuffer header = ByteBuffer.allocate(8);
            header.put(tag.getBytes());
            header.putInt(data.limit());
            out.write(header.array());
        }
        data.position(0);
        while(data.position()<data.limit()) {
            out.write(data.get());
        }
    }
    
    public static IFFChunk fromStream(String tag, InputStream in) throws IOException {
        IFFChunk result = new IFFChunk();
        if (tag.length()!=4) throw new IllegalArgumentException("Tag must be 4 characters.");
        result.tag = tag;
        result.data = ByteBuffer.wrap(Streams.readAll(in));
        result.init(false);
        return result;
    }
    
    public static IFFChunk fromStream(InputStream in) throws IOException {
        IFFChunk result = new IFFChunk();
        result.data = ByteBuffer.wrap(Streams.readAll(in));
        result.init(true);
        return result;
    }
    
    public static IFFChunk fromArray(String tag, byte[] in) throws IOException {
        IFFChunk result = new IFFChunk();
        if (tag.length()!=4) throw new IllegalArgumentException("Tag must be 4 characters.");
        result.tag = tag;
        result.data = ByteBuffer.wrap(in);
        result.init(false);
        return result;
    }
}
