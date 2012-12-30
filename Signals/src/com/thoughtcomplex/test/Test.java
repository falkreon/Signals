package com.thoughtcomplex.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.thoughtcomplex.event.CollisionEvent;
import com.thoughtcomplex.event.EventListener;
import com.thoughtcomplex.event.EventMulticaster;
import com.thoughtcomplex.signals.Signal;

import static com.thoughtcomplex.signals.Signals.connect;

public class Test {
    public static int ITERATIONS = 80000000;
    
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
        connect(button, "onMouseClicked", model1, "processClick");
        connect(button, "onMouseClicked", model2, "processClick");
        connect(button, "onMouseEntered", model2, "enter");
        connect(button, "onMouseExited", model2, "leave");
        
        // All objects are "configured," so now let's let the system go and see
        // what it does.
        frame.setVisible(true);
        
        /*
         * Instance and configure a typical Generic-POJO-event-handler setup.
         */
        EventMulticaster<CollisionEvent> onCollide = new EventMulticaster<CollisionEvent>();
        onCollide.addListener(new EventListener<CollisionEvent>() {
            @Override
            public void onEvent(CollisionEvent event) {
                //Do Nothing. Just testing the binding speed.
            }
        });
        
        //Send some test signals
        
        timeTest1(button.onMouseEntered);
        timeTest2(onCollide);
    }
    
    public static void timeTest1(Signal s) {
        
        long startTime = System.nanoTime();
        for(int i=0; i<ITERATIONS; i++) {
            s.signal(10,10);
        }
        
        long endTime = System.nanoTime();
        long elapsed = endTime-startTime;
        double average = ((double)elapsed) / ITERATIONS;
        System.out.println("Elapsed: "+elapsed+"ns - average"+average);
    }
    
    public static void timeTest2(EventMulticaster<CollisionEvent> s) {
        long startTime = System.nanoTime();
        CollisionEvent testEvent = new CollisionEvent(s, s, null);
        for(int i=0; i<ITERATIONS; i++) {
            s.invoke(testEvent); //Event collided with itself
        }
        
        long endTime = System.nanoTime();
        long elapsed = endTime-startTime;
        double average = ((double)elapsed) / ITERATIONS;
        System.out.println("Elapsed: "+elapsed+"ns - average"+average);
    }
}
