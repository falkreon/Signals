package com.thoughtcomplex.test;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import static com.thoughtcomplex.signals.Signals.connect;

public class Test {
    
    private static SignalButton button = new SignalButton("I send signals!");
    
    private static TestModel    model1 = new TestModel("A");
    private static TestModel    model2 = new TestModel("B");
    
    public static void main(String[] args) {
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
            connect(button, "onClick", model1, "click");
            connect(button, "onClick", model2, "click");
            connect(button, "onEnter", model2, "enter");
            connect(button, "onLeave", model2, "leave");
        //} catch (SignalConnectException e) {
        //    e.printStackTrace();
        //}
        
        // All objects are "configured," so now let's let the system go and see
        // what it does.
        frame.setVisible(true);
    }
}
