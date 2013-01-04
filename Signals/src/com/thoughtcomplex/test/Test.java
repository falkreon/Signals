package com.thoughtcomplex.test;

import com.thoughtcomplex.game.Game;
import com.thoughtcomplex.game.physics.Vector;

public class Test {
    public static Game testGame;
    
    public static void main(String[] args) {
        //System.out.println(Math.PI*2.0D);
        //System.out.println(Vector.TAU);
        //System.out.println(Vector.TAU - (Math.PI*2.0D)); //Literally indistinguishable!
        //System.out.println(Vector.PRECISE_TAU.toEngineeringString());
        
        testGame = new Game("Test", 320, 216, new TestModel());
        
        testGame.start();
    }
    
}
