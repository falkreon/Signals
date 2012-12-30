package com.thoughtcomplex.event;

public interface Observable<T extends SignalEvent> {
    public void addListener(EventListener<T> listener);
    public void removeListener(EventListener<T> listener);
}
