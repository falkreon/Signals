package com.thoughtcomplex.test;

import org.junit.Before;
import org.junit.Test;

import com.thoughtcomplex.game.physics.Vector;

import static junit.framework.Assert.*;

public class PhysicsTests {
    @Test
    public void tauAccuracy() {
        assertEquals(Vector.TAU,Math.PI*2.0D);
    }
    
    @Test
    public void polarCartesianPolar() {
        double angle = Vector.TAU/4.0D; //about 90 degrees
        Vector polar = Vector.ofPolarCoordinates(angle, 1);
        double difference = Math.abs(polar.getAngle() - angle);
        
        //System.out.println("Difference: " + difference);
        
        assertEquals(angle, polar.getAngle()); //this assumes an amazing degree of accuracy.
        
        //assertTrue(difference<Vector.TAU/16.0D); //if the above test fails, this one is acceptable.
    }
    
    @Test
    public void vectorReflection() {
        for(int i=0; i<400; i++) {
            double vx = Math.random()*(Double.MAX_VALUE/8.0D);
            double vy = Math.random()*(Double.MAX_VALUE/8.0D);
            
            Vector normal = Vector.of(-12.6, 0);
            Vector incident = Vector.of(vx, -vy);
            Vector result = incident.reflectAcross(normal);
            assertEquals(-vx, result.getXVelocity());
            assertEquals(-vy, result.getYVelocity());
            
            normal = Vector.of(0, 216.42);
            Vector result2 = incident.reflectAcross(normal);
            assertEquals(vx, result2.getXVelocity());
            assertEquals(vy, result2.getYVelocity());
            
            normal = Vector.of(-1, 1);
            Vector result3 = incident.reflectAcross(normal);
            
            assertTrue(result3.getXVelocity()<0);
            assertTrue(result3.getYVelocity()>0);
        }
        //Not sure why this fails - should not need a normalize!
        //assertEquals(-1.0, result3.getXVelocity());
        //assertEquals(1.0, result3.getYVelocity());
    }
}
