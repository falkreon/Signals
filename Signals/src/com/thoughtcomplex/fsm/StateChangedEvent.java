package com.thoughtcomplex.fsm;

import com.thoughtcomplex.event.SignalEvent;

public class StateChangedEvent<T> extends SignalEvent {

    T oldState;
    T newState;
    
    public StateChangedEvent(StateMachine<T> source, T oldState, T newState, String detailMessage) {
        super(source, detailMessage);
        this.oldState = oldState;
        this.newState = newState;
    }
    
    public T getOldState() { return oldState; }
    public T getNewState() { return newState; }
}
