package com.thoughtcomplex.event;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class EventMulticaster<T extends SignalEvent> implements Observable<T> {
    private ArrayList<WeakReference<EventListener<T>>> listeners = new ArrayList<WeakReference<EventListener<T>>>();
    
    public EventMulticaster() {
    }
    
    public void invoke(final T event) {
        for (Iterator<WeakReference<EventListener<T>>> iterator = listeners.iterator(); iterator.hasNext();) {
            WeakReference<EventListener<T>> weakRef = iterator.next();
            EventListener<T> listener = weakRef.get();
            if (listener==null) {
                iterator.remove();
            } else {
                listener.onEvent(event);
            }
        }
    }
    
    public void addListener(EventListener<T> listener) {
        listeners.add(new WeakReference<EventListener<T>>(listener));
    }
    
    public void removeListener(EventListener<T> toRemove) {
        for (Iterator<WeakReference<EventListener<T>>> iterator = listeners.iterator(); iterator.hasNext();) {
            WeakReference<EventListener<T>> weakRef = iterator.next();
            EventListener<T> listener = weakRef.get();
            if (listener==null || listener.equals(toRemove)) iterator.remove();
        }
    }
}
