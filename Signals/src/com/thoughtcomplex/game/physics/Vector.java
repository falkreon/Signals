package com.thoughtcomplex.game.physics;

import java.math.BigDecimal;

public class Vector {
    
    public static BigDecimal PRECISE_TAU = new BigDecimal(
            "6.2831853071795864769252867665590057683943387987502116419498891846156328" +
            "125724179972560696506842341359642961730265646132941876892191011644634507" +
            "188162569622349005682054038770422111192892458979098607639288576219513318" +
            "668922569512964675735663305424038182912971338469206972209086532964267872" +
            "1452049828254744917401321263117634976304184192565850818343072873");
    public static double TAU = PRECISE_TAU.doubleValue();
    
    private double vx = 0;
    private double vy = 0;
    
    private Vector() {}
    private Vector(double xVelocity, double yVelocity) {
        vx = xVelocity;
        vy = yVelocity;
    }
    
    public Vector normalize() {
        double maxScalingFactor = Math.max(vx, vy);
        double minScalingFactor = Math.min(vx, vy);
        
        double scalingFactor = Math.max(Math.abs(maxScalingFactor), Math.abs(minScalingFactor));
        
        if (scalingFactor==0) scalingFactor=1;
        double xp = vx/scalingFactor;
        double yp = vy/scalingFactor;
        
        return new Vector(xp, yp);
    }
    
    public double getXVelocity() {
        return vx;
    }
    
    public double getYVelocity() {
        return vy;
    }
    
    public double getAngle() {
        return Math.atan2(vy, vx);
    }
    
    public Vector add(Vector v) {
        return new Vector(v.vx + this.vx, v.vy + this.vy);
    }
    
    public Vector subtract(Vector v) {
        return new Vector(this.vx - v.vx, this.vy - v.vy);
    }
    
    public Vector multiply(double scalar) {
        return new Vector(this.vx * scalar, this.vy * scalar);
    }
    
    public double dotProduct(Vector v) {
        
        return (this.vx*v.vx) + (this.vy*v.vy);
    }
    
    /**
     * Gets a Vector perpendicular to this one
     * @return
     */
    public Vector getPerpendicular() {
        return new Vector(-vy, vx);
    }
    
    /**
     * Returns this Vector, reflected across the specified normal
     * @param normal
     * @return
     */
    public Vector reflectAcross(Vector normal) {
        //returnValue = this - 2 * normal * (normal DOT this)
        Vector wallNormal = normal.normalize();
        //double s = 2.0D * this.dotProduct(wallNormal);
        //return this.subtract(wallNormal.multiply(s));
        
        
        //continue to eval: Not very fluent!
        return this.subtract( wallNormal.multiply(2.0D * this.dotProduct(wallNormal)) );
        
        //double s = 2.0D * ( (this.vx * wallNormal.vx) + (this.vy * wallNormal.vy) );
        //double xp = this.vx - (s*wallNormal.vx);
        //double yp = this.vy - (s*wallNormal.vy);
        //return new Vector(xp, yp);
    }
    
    /**
     * Constructs a Vector out of an X component scalar and a Y component scalar.
     * @param xVelocity         the X component scalar
     * @param yVelocity         the Y component scalar
     * @return                  a Vector of the specified magnitudes
     */
    public static Vector of(double xVelocity, double yVelocity) {
        return new Vector(xVelocity, yVelocity);
    }
    
    
    /**
     * Constructs a Vector from an angle and a magnitude.
     * @param angle             the direction of "movement"
     * @param length            the magnitude
     * @return                  a Vector of the specified direction and magnitude.
     */
    public static Vector ofPolarCoordinates(double angle, double length) {
        double vx = Math.cos(angle)*length;
        double vy = Math.sin(angle)*length;
        
        return new Vector(vx, vy);
    }
}
