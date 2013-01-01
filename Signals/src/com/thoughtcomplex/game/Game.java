package com.thoughtcomplex.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.thoughtcomplex.event.EventListener;
import com.thoughtcomplex.image.GLDrawingContext;
import com.thoughtcomplex.state.FiniteStateMachine;

public class Game extends FiniteStateMachine<GameSection> implements EventListener<TickEvent> {

    private String name = "";
    private int width = 320;
    private int height = 216;
    private TimingCore timer = null;
    boolean firstTick = true;
    
    public Game(String name, int width, int height, GameSection initialState, GameSection... gameSections) {
        super(initialState, gameSections);
        this.width = width;
        this.height = height;
        this.name = name;
    }
    
    public void start() {
        if (timer!=null && timer.isActive()) return;
        timer = new TimingCore(12000L);
        timer.addListener(this);
        timer.start();
    }
    
    public void end() {
        timer.pause();
        GLDrawingContext.destroy();
        timer.end();
        timer = null;
        System.exit(0);
    }

    @Override
    public void onEvent(TickEvent event) {
        if (firstTick) {
            GLDrawingContext.initialize(name, width, height);
            Display.setVSyncEnabled(true);
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            GLDrawingContext.display();
            
            firstTick = false;
        }
        
        //distribute ticks to the current state
        Keyboard.poll(); //keep Keyboard up to date.
        getState().onEvent(event);
        
        //paint the screen
        GLDrawingContext.clear();
        GLDrawingContext.screen.activateSurface();
        GLDrawingContext.clear(); //yes, this is necessary.
        getState().paint();
        GLDrawingContext.screen.deactivateSurface();
        GLDrawingContext.display();
        
    }
}
