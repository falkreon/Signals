package com.thoughtcomplex.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.thoughtcomplex.io.Streams;

public class IFFChunk {
    ByteBuffer data;
    String tag;
    ArrayList<IFFChunk> contents = new ArrayList<IFFChunk>();
    
    private IFFChunk() {}
    
    private void init() {
        data.rewind();
        int intTag = data.getInt();
        tag = "" + (char)(intTag>>24 & 0xFF) + (char)(intTag>>16 & 0xFF) +
                   (char)(intTag>>8 & 0xFF) +  (char)(intTag & 0xFF);
        int length = data.getInt() & 0x7FFFFFFF; //byteOrder(data.getInt());
        System.out.println("Raw Length: "+length);
        if (length<0) length=1;
        if (length%2!=0) length++;
        if (length+8>data.limit()) {
            System.out.println("==DATA PANIC==");
            for(int i=0; i<20; i++) {
                byte b = data.get();
                System.out.println("DATA: "+b+": "+(char)b);
            }
        }
        System.out.println("Limiting to "+(length+8));
        //if (length+8<data.limit())
        data.position(0);
        data.limit(length+8);
        data.position(8);
        System.out.println("Chunk Type: "+tag+" Length: "+(length+8)+" (incl. header)");
        
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
                
                child.init();
                contents.add(child);
                //tempData.position(child.data.limit());
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
            int markerID = data.getInt(); //bollocks
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
    
    
    
    public static IFFChunk fromStream(InputStream in) throws IOException {
        IFFChunk result = new IFFChunk();
        result.data = ByteBuffer.wrap(Streams.readAll(in));
        result.init();
        return result;
    }
    
    public static IFFChunk fromArray(byte[] in) throws IOException {
        IFFChunk result = new IFFChunk();
        result.data = ByteBuffer.wrap(in);
        result.init();
        return result;
    }
    
    private static int byteOrder(int i) {
        int b1 = i >> 24 & 0xFF;
        int b2 = i >> 16 & 0xFF;
        int b3 = i >>  8 & 0xFF;
        int b4 = i       & 0xFF;
        
        return (b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
    }
}
