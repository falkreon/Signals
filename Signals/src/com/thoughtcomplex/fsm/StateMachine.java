package com.thoughtcomplex.fsm;

import com.thoughtcomplex.event.Observable;

public interface StateMachine<T> extends Observable<StateChangedEvent<T>> {
    public T getState();
    public void setState(T state);
}
