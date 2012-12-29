package com.thoughtcomplex.event;

public abstract class SignalEvent {
    
    private Object source = null;
    private String detailMessage = null;
    
    public SignalEvent(Object source, String detailMessage) {
        this.detailMessage = detailMessage;
        this.source = source;
    }
    
    public Object getSource() {
        return source;
    }
    
    public String getDetailMessage() {
        return detailMessage;
    }
}
