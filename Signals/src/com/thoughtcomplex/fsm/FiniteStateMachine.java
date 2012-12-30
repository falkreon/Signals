package com.thoughtcomplex.fsm;

import java.util.ArrayList;

import com.thoughtcomplex.event.EventListener;
import com.thoughtcomplex.event.EventMulticaster;
import com.thoughtcomplex.signals.CallbackSignature;
import com.thoughtcomplex.signals.Signal;

public class FiniteStateMachine<T> implements StateMachine<T> {
    
    private EventMulticaster<StateChangedEvent<T>> stateChanged = new EventMulticaster<StateChangedEvent<T>>();
    
    /**
     * Fires when the machine's state changes.
     * 
     * @signature       onStateChanged(T oldState, T newState);
     */
    @CallbackSignature({Object.class, Object.class})
    public Signal onStateChanged = new Signal();
    
    private ArrayList<T> validStates = new ArrayList<T>();
    private T currentState = null;
    
    public FiniteStateMachine(T initialState, Class<T> validStates) {
        currentState = initialState;
        if (!validStates.isEnum()) {
            throw new InvalidStateException( "Class '" + validStates.getSimpleName() +
                    "' is not an enum type, and cannot be used as a set of valid states.",
                    null);
        } else {
            for(T state : validStates.getEnumConstants()) {
                this.validStates.add(state);
            
            }
        }
    }
    
    public FiniteStateMachine(T initialState, T... otherValidStates) {
        currentState = initialState;
        validStates.add(initialState);
        for(T state : otherValidStates) {
            validStates.add(state);
        }
    }
    
    @Override
    public T getState() {
        return currentState;
    }

    @Override
    public void setState(T state) {
        if (validStates.contains(state)) {
            T oldState = currentState;
            currentState = state;
            onStateChanged.signal(oldState, state);
            
        } else {
            throw new InvalidStateException("State '" + state.toString() +
                    "' is invalid for this machine.", state);
        }
        
    }

    @Override
    public void addListener(EventListener<StateChangedEvent<T>> listener) {
        stateChanged.addListener(listener);
    }

    @Override
    public void removeListener(EventListener<StateChangedEvent<T>> listener) {
        stateChanged.removeListener(listener);
    }

    
    
}
