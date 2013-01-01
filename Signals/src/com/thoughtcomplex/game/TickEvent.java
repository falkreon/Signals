package com.thoughtcomplex.game;

import com.thoughtcomplex.event.SignalEvent;

public class TickEvent extends SignalEvent {
    public TickEvent(Object source) {
        super(source, "Tick Tock.");
    }
    
}
