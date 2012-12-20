package com.thoughtcomplex.fsm;

import java.util.ArrayList;

import com.thoughtcomplex.signals.CallbackSignature;
import com.thoughtcomplex.signals.Signal;

public class FiniteStateMachine<T> implements StateMachine<T> {
    /**
     * Fires when the machine's state changes.
     * 
     * @signature       onStateChanged(T oldState, T newState);
     */
    @CallbackSignature({Object.class, Object.class})
    public Signal onStateChanged = new Signal();
    
    private ArrayList<T> validStates = new ArrayList<T>();
    private T currentState = null;
    
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
    
}
