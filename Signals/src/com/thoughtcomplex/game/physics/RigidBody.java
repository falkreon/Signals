package com.thoughtcomplex.game.physics;

import com.thoughtcomplex.game.Ticking;

public class RigidBody implements Ticking {
    private double x = 0;
    private double y = 0;
    private double width = 8;
    private double height = 8;
    
    private Vector velocity = Vector.of(0, 0);
    
    public RigidBody() {}
    public RigidBody(int x, int y, int width, int height) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
    }
    
    @Override
    public void tick() {
        
    }
    
}
