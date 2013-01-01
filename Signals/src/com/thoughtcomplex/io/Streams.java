package com.thoughtcomplex.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Streams {
    
    public static void route(final InputStream in, final OutputStream out) throws IOException {
        final int buffersize = 4096;
        final byte[] buffer = new byte[buffersize];
    
        int available = 0;
        while ((available = in.read(buffer)) >= 0)
        {
            out.write(buffer, 0, available);
        }
    }
    
    public static Thread routeAsync(final InputStream in, final OutputStream out){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    route(in,out);
                } catch (IOException ex) {  
                }
            }
        });
        
        t.start();
        return t;
    }
    
    public static byte[] readAll(final InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        route(in, out);
        return out.toByteArray();
    }
}
