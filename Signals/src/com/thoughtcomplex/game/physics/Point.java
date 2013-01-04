package com.thoughtcomplex.game.physics;

/**
 * Immutable two-dimensional Point made of two double-precision scalars
 * @author      Isaac Ellingson
 */
public class Point {
    private double x;
    private double y;
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    /**
     * Returns a new Point whose x and y coordinates are each the sum of this Point's and
     * those of the Point provided e.g. (this.x+p.x, this.y+p.y). The operation does not
     * modify this.
     * @param p         the other point to sum
     * @return          a brand new Point containing the sums the respective coordinates
     */
    public Point add(Point p) {
        return new Point(this.x+p.x, this.y+p.y);
    }
    
    /**
     * Returns a new Point whose x and y coordinates are displaced by the provided
     * Vector. The operation does not modify this.
     * @param v         the Vector to displace this point by.
     * @return          a brand new Point displaced by the Vector.
     */
    public Point add(Vector v) {
        return new Point(this.x + v.getXVelocity(), this.y + v.getYVelocity());
    }
}
