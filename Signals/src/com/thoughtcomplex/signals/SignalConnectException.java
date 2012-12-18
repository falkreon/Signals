package com.thoughtcomplex.signals;

public class SignalConnectException extends RuntimeException {
    String signal = null;
    String slot   = null;
    
    public SignalConnectException(String message, String signal, String slot) {
        super(message);
        this.signal = signal;
        this.slot = slot;
    }
    
    public String getSignal() {
        return signal;
    }
    
    public String getSlot() {
        return slot;
    }
}
