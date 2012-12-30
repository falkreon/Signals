package com.thoughtcomplex.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

import com.thoughtcomplex.data.IFFChunk;

public class IFFTests {
    
    IFFChunk testChunk;
    
    @Before
    public void setUp() throws Exception {
        byte[] iffData = new byte[200];
        for(int i=0; i<200; i++) iffData[i]=(byte)i;
        testChunk = IFFChunk.fromArray("TEST",iffData);
    }
    
    @Test
    public void writeAndReadTest() throws IOException {
        //Write this chunk out
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        testChunk.write(out);
        
        //Read it back in
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        IFFChunk result = IFFChunk.fromStream(in);
        assertEquals(result.getTag(), testChunk.getTag());
        byte[] originalData = testChunk.getData();
        byte[] resultData = result.getData();
        assertEquals(originalData.length, resultData.length);
        for(int i=0; i<originalData.length; i++) {
            assertEquals(originalData[i], resultData[i]);
        }
    }
    
}
