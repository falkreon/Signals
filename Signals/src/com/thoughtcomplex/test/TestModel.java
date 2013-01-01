package com.thoughtcomplex.test;

import org.lwjgl.input.Keyboard;

import com.thoughtcomplex.game.GameSection;
import com.thoughtcomplex.game.TickEvent;
import com.thoughtcomplex.image.GLColor;
import com.thoughtcomplex.image.GLDrawingContext;

public class TestModel extends GameSection {
    
    public TestModel() {
    }
    
   

    @Override
    public void onEvent(TickEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
           
            Test.testGame.end();
        }
    }

    @Override
    public void paint() {
        GLDrawingContext.paintLine(0, 0, 100, 100, 3, new GLColor((float)Math.random(),(float)Math.random(),1.0f));
        
        GLDrawingContext.fillRect(100, 150, 100, 100, new GLColor((float)Math.random(),(float)Math.random(), (float)Math.random()));
    }
}
