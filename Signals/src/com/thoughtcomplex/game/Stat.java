package com.thoughtcomplex.game;

import com.thoughtcomplex.event.EventListener;
import com.thoughtcomplex.event.EventMulticaster;
import com.thoughtcomplex.state.StateChangedEvent;
import com.thoughtcomplex.state.StateMachine;

/**
 * Represents a numeric state of an object in the game-world, which has a
 * minimum of zero and a predetermined integer maximum. All operations perform
 * saturation math to keep the Stat within the specified range.
 * 
 * Stat also contains convenience logic for poisoning and regenerating the stat over
 * time. In order for this to work, the Stat must receive regular ticks through Ticking.
 * 
 * @author Isaac Ellingson
 */

public class Stat implements StateMachine<Integer>, Ticking {
    private EventMulticaster<StateChangedEvent<Integer>> onChanged = new EventMulticaster<StateChangedEvent<Integer>>();
    
    private volatile double     value          = 20;    // mutable
    private double              maxValue       = 20;    // immutable
    private volatile double     regenRate      = 0;     // mutable
    private volatile double     poisonRate     = 0;     // mutable
    private volatile int        poisonDuration = 0;     // mutable
    
    public Stat(int maxValue) {
        this.value = maxValue;
        this.maxValue = maxValue;
    }
    
    public int get() {
        return (int) value;
    }
    
    public void set(int value) {
        if (((int)this.value)==value) return;
        int oldValue = (int)value;
        if (value > this.maxValue) {
            this.value = this.maxValue;
        } else {
            this.value = value;
        }
        onChanged.invoke(new StateChangedEvent<Integer>(this, oldValue, value, "set"));
    }
    
    public int getMax() {
        return (int) maxValue;
    }
    
    public void increment(int value) {
        if (value==0) return;
        int oldValue = (int)value;
        this.value += value;
        if (this.value > this.maxValue) this.value = this.maxValue;
        onChanged.invoke(new StateChangedEvent<Integer>(this, oldValue, value, "increment"));
    }
    
    public void decrement(int value) {
        if (value==0) return;
        int oldValue = (int)value;
        this.value -= value;
        if (this.value < 0) this.value = 0;
        onChanged.invoke(new StateChangedEvent<Integer>(this, oldValue, value, "decrement"));
    }
    
    public void multiply(int value) {
        if (value==1) return;
        int oldValue = (int)value;
        this.value *= (double) value;
        if (this.value > this.maxValue) this.value = this.maxValue;
        onChanged.invoke(new StateChangedEvent<Integer>(this, oldValue, value, "multiply"));
    }
    
    public void divide(int value) {
        if (value == 0) {
            this.value = Double.MAX_VALUE; // Consider: Double.POSITIVE_INFINITY
            return;
        }
        if (value==1) return;
        
        int oldValue = (int)value;
        
        this.value /= (double) value;
        if (this.value < 0) this.value = 0;
        
        onChanged.invoke(new StateChangedEvent<Integer>(this, oldValue, value, "divide"));
    }
    
    public void increment(Stat s) {
        increment(s.get());
    }
    
    public void decrement(Stat s) {
        decrement(s.get());
    }
    
    public void multiply(Stat s) {
        multiply(s.get());
    }
    
    public void divide(Stat s) {
        divide(s.get());
    }
    
    public void setRegenRate(double rate) {
        regenRate = rate;
    }
    
    public void poison(double rate, int duration) {
        poisonRate = rate;
        poisonDuration = duration;
    }
    
    @Override
    public void tick() {
        int oldValue = (int)value;
        
        if (poisonDuration > 0) {
            value -= poisonRate;
            poisonDuration--;
        }
        
        value += regenRate;
        if (value > maxValue) value = maxValue;
        if (value < 0) value = 0;
        
        if (get()!=oldValue) {
            onChanged.invoke(new StateChangedEvent<Integer>(this, oldValue, (int)value, "tick"));
        }
    }
    
    @Override
    public Integer getState() {
        return (int) value;
    }
    
    @Override
    public void setState(Integer state) {
        set(state);
    }
    
    @Override
    public void addListener(EventListener<StateChangedEvent<Integer>> listener) {
        onChanged.addListener(listener);
    }
    
    @Override
    public void removeListener(EventListener<StateChangedEvent<Integer>> listener) {
        onChanged.removeListener(listener);
    }
}
