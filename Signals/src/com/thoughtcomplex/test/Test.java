package com.thoughtcomplex.test;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;

import com.thoughtcomplex.data.IFFChunk;

import static com.thoughtcomplex.signals.Signals.connect;

public class Test {
    
    private static SignalButton button = new SignalButton("I send signals!");
    
    private static TestModel    model1 = new TestModel("A");
    private static TestModel    model2 = new TestModel("B");
    
    public static void main(String[] args) {
        /*
        try {
            File f = new File("test.aif");
            System.out.println(f.getAbsolutePath());
            IFFChunk.fromStream(new FileInputStream(f));
        } catch (IOException ex) {
            System.out.println("Failed to read test file.");
            ex.printStackTrace();
        }*/
        
        JFrame frame = new JFrame("Test");
        frame.setLayout(new BorderLayout());
        frame.add(button, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        
        
        /*
         * Configure objects so that messages pass transparently from producers
         * to consumers, without the objects knowing what objects are at the
         * other end of the line.
         */
        //try {
            connect(button, "onMouseClicked", model1, "processClick");
            connect(button, "onMouseClicked", model2, "processClick");
            connect(button, "onMouseEntered", model2, "enter");
            connect(button, "onMouseExited", model2, "leave");
        //} catch (SignalConnectException e) {
        //    e.printStackTrace();
        //}
        
        // All objects are "configured," so now let's let the system go and see
        // what it does.
        frame.setVisible(true);
        
        //Send some test signals
        
        long startTime = System.nanoTime();
        for(int i=0; i<10000; i++) {
            button.onMouseEntered.signal(10,10);
        }
        
        
        long endTime = System.nanoTime();
        long elapsed = endTime-startTime;
        double average = ((double)elapsed) / 10000;
        System.out.println("Elapsed: "+elapsed+"ns - average"+average);
    }
}
