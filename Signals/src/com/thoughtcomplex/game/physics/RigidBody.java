package com.thoughtcomplex.game.physics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thoughtcomplex.game.Ticking;

public class RigidBody implements Ticking {
    private Point location = new Point(0,0);
    private double width = 8;
    private double height = 8;
    private Vector velocity = Vector.of(0, 0);
    
    private boolean collisionImpedesMovement = true;
    
    private ArrayList<Point> contrail = new ArrayList<Point>();
    private int contrailSize = 20;
    
    public RigidBody() {}
    public RigidBody(int x, int y, int width, int height) {
        this.location = new Point(x,y);
        this.width = width; this.height = height;
    }
    
    
    public double getX()        { return location.getX(); }
    public double getY()        { return location.getY(); }
    public double getWidth()    { return width; }
    public double getHeight()   { return height; }
    public Vector getVelocity() { return Vector.of(velocity.getXVelocity(), velocity.getYVelocity()); }
    
    public boolean collisionImpedesMovement() {
        return collisionImpedesMovement;
    }
    
    public List<Point> getContrail() { return Collections.unmodifiableList(contrail); }
    
    public void setX(double x)                  { this.location = new Point(x, location.getY()); }
    public void setY(double y)                  { this.location = new Point(location.getX(), y); }
    public void setWidth(double width)          { this.width = width; }
    public void setHeight(double height)        { this.height = height; }
    public void setVelocity(Vector velocity)    { this.velocity = velocity; }
    
    
    @Override
    public void tick() {
        contrail.add(location);
        while(contrail.size()>contrailSize) contrail.remove(0);
        location = location.add(velocity);
    }
}
