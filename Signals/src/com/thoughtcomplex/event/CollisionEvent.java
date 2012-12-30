package com.thoughtcomplex.event;

public class CollisionEvent extends SignalEvent {
    Object destination = null;
    
    public CollisionEvent(Object source, Object dest, String detailMessage) {
        super(source, detailMessage);
        destination = dest;
    }
    
}
