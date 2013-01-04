package com.thoughtcomplex.test;

import org.lwjgl.input.Keyboard;

import com.thoughtcomplex.game.GameSection;
import com.thoughtcomplex.game.physics.Compartment;
import com.thoughtcomplex.game.physics.Point;
import com.thoughtcomplex.game.physics.RigidBody;
import com.thoughtcomplex.game.physics.Vector;
import com.thoughtcomplex.game.physics.World;
import com.thoughtcomplex.image.GLColor;
import com.thoughtcomplex.image.GLDrawingContext;

public class TestModel extends GameSection {
    
    World world = new World();
    
    public TestModel() {
        Compartment test = new Compartment("TEST", null);
        world.add(test);
        
        for(int i=0; i<200; i++) {
            RigidBody body = new RigidBody();
            body.setX(Math.random()*300);
            body.setY(Math.random()*200);
            body.setWidth(2);
            body.setHeight(2);
            body.setVelocity(Vector.of(Math.random(), Math.random()));
            test.add(body);
        }
    }
    
   

    @Override
    public void tick() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
           
            Test.testGame.end();
        }
        
        for(RigidBody actor : world.get("TEST").getRelevantSet()) {
            
            actor.setVelocity(actor.getVelocity().add(Vector.of(0.0, 0.1)));
            
            actor.tick();
            
            if (actor.getY()>216) {
                actor.setY(216);
                actor.setVelocity(actor.getVelocity().reflectAcross(Vector.of(0.00, -1)));
            }
            
            if (actor.getX()>320) {
                actor.setX(320);
                actor.setVelocity(actor.getVelocity().reflectAcross(Vector.of(-1, 0)));
            }
            
            if (actor.getX()<0) {
                actor.setX(0);
                actor.setVelocity(actor.getVelocity().reflectAcross(Vector.of(1, 0)));
            }
        }
    }

    public void paint(int x, int y) {
        paint();
    }
    
    public void paint(int x, int y, int width, int height) {
        paint();
    }
    
    public void paint() {
        for(RigidBody actor : world.get("TEST").getRelevantSet()) {
            for(Point p : actor.getContrail()) {
                GLDrawingContext.fillRect((int)p.getX(), (int)p.getY(), 2, 2, new GLColor(0.3f, 0f, 0.6f, 0.5f));
            }
            
            GLDrawingContext.fillRect((int)actor.getX(), (int)actor.getY(), 2, 2, new GLColor(0.7f,0.5f,0.9f));
        }
        
        //GLDrawingContext.paintLine(0, 0, 100, 100, 3, new GLColor((float)Math.random(),(float)Math.random(),1.0f));
        
        //GLDrawingContext.fillRect(100, 150, 100, 100, new GLColor((float)Math.random(),(float)Math.random(), (float)Math.random()));
    }
}
