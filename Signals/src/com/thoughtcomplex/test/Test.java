package com.thoughtcomplex.test;

import com.thoughtcomplex.game.Game;

public class Test {
    public static Game testGame;
    
    public static void main(String[] args) {

        testGame = new Game("Test", 320, 216, new TestModel());
        
        testGame.start();
    }
    
}
