package com.thoughtcomplex.game;

import com.thoughtcomplex.event.EventListener;

public abstract class GameSection implements EventListener<TickEvent> {

    public abstract void paint();
    
}
