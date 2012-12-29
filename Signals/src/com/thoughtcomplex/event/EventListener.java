package com.thoughtcomplex.event;

public interface EventListener<T extends SignalEvent> {
    public void onEvent(T event);
}
