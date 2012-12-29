package com.thoughtcomplex.event;

import java.util.ArrayList;

public class EventMulticaster<T extends SignalEvent> {
    private ArrayList<EventListener<T>> listeners = new ArrayList<EventListener<T>>();
    private boolean threaded = false;
    
    public EventMulticaster() {
        threaded=false;
    }
    
    public EventMulticaster(boolean threaded) {
        this.threaded = threaded;
    }
    
    public void invoke(final T event) {
        for(final EventListener<T> listener : listeners) {
            if (threaded) {
                new Thread( new Runnable() { public void run() { listener.onEvent(event); } }).start();
            } else {
                listener.onEvent(event);
            }
        }
    }
    
    public void addListener(EventListener<T> listener) {
        listeners.add(listener);
    }
    
    public void removeListener(EventListener<T> listener) {
        listeners.remove(listener);
    }
}
