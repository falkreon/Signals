package com.thoughtcomplex.state;

public class InvalidStateException extends RuntimeException {
    Object invalidState;
    
    public InvalidStateException(String s, Object state) {
        super(s);
        invalidState = state;
    }
    
    public Object getInvalidState() {
        return invalidState;
    }
}
