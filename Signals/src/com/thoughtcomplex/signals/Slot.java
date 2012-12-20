package com.thoughtcomplex.signals;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.util.HashMap;

/**
 * Indicates that the method can be connected to a signal with the specified name, and
 * will receive messages with the same method definition.
 * 
 * @author Isaac Ellingson
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Slot {
    String value() default "";
    
    /**
     * This field is used internally by the Signal/Slot system to
     */
    HashMap<Signal, Slot> callbacks = new HashMap<Signal, Slot>();
}
