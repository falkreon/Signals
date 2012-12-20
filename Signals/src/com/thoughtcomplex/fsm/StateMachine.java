package com.thoughtcomplex.fsm;

public interface StateMachine<T> {
    public T getState();
    public void setState(T state);
}
