package com.thoughtcomplex.data;

import com.thoughtcomplex.event.CollisionEvent;
import com.thoughtcomplex.event.EventListener;
import com.thoughtcomplex.event.EventMulticaster;
import com.thoughtcomplex.event.Observable;

/**
 * Represents a player, enemy, bullet, or level geometry.
 * @author Isaac Ellingson
 */
public class GameObject implements Observable<CollisionEvent>{

    private EventMulticaster<CollisionEvent> onCollided = new EventMulticaster<CollisionEvent>();
    
    @Override
    public void addListener(EventListener<CollisionEvent> listener) {
        onCollided.addListener(listener);
        
    }

    @Override
    public void removeListener(EventListener<CollisionEvent> listener) {
        onCollided.removeListener(listener);
    }
    
}
