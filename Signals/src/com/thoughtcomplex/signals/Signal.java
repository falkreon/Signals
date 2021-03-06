package com.thoughtcomplex.signals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Isaac Ellingson
 */

public class Signal {
    private String name;
    private ArrayList<Map.Entry<Object, Method>> callbacks =
            new ArrayList<Map.Entry<Object, Method>>();
    private Class<?>[] signature = null;
    
    private static String ERROR_NOT_A_SLOT =
            "Non-@Slot method somehow leaked into this Signal's callback list!";
    
    /**
     * Creates a new Signal to be referred to by its field-name.
     */
    public Signal() {
        this.name = null;
    }
    
    /**
     * Creates a new Signal with the specified name.
     * @param name      The name this Signal will be referenced by in {@link Signals.connect} statements.
     */
    public Signal(String name) {
        this.name = name;
    }
    
    /**
     * @return The name of this signal as referenced by {@link Signals.connect} statements.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the list of callbacks registered with this Signal. Not intended for
     * public/API use, this method is provided for specialized subclasses.
     * @return
     */
    protected List<Map.Entry<Object, Method>> getCallbacks() {
        return callbacks;
    }
    
    /**
     * Add a callback to this Signal's internal callback list. Not intended for public
     * use; this is automatically called by {@link Signals.connect}.
     * @param dest      The Object containing the @Slot method
     * @param slot      The @Slot method
     */
    protected void addCallback(Object dest, Method slot) {
        synchronized(callbacks) {
            callbacks.add(new AbstractMap.SimpleEntry<Object, Method>(dest, slot));
        }
    }
    
    /**
     * Add in the method definition from this Signal's {@link CallbackSignature}
     * annotation. This method is automatically called and not meant for public use.
     * @param signature
     */
    protected void addSignatureDefinition(Class<?>[] signature) {
        this.signature = signature;
    }
    
    /**
     * Activate this signal, automatically notifying any connected @Slot methods with
     * matching method signatures. If there are problems notifying recipients, the Signal
     * will finish its dispatching before throwing an Exception.
     * @param args
     */
    public void signal(Object... args) throws SignalConnectException {
        if (signature!=null) {
          //Check method signature
            boolean valid = true;
            
            if (args.length!=signature.length) {
                valid=false;
            } else {
                
            }
            
            if (!valid) {
                ArrayList<Class<?>> actual = new ArrayList<Class<?>>();
                for(Object o : args) actual.add(o.getClass());
                String argumentsExpected = toClassList(signature);
                String argumentsPassed = toClassList(actual.toArray(new Class<?>[actual.size()]));
                
                throw new SignalConnectException("Invalid arguments sent to Signal: "+
                        "Expected " + argumentsExpected + " found " +
                        argumentsPassed,
                        this.getName(), null);
            }
        }
        
        if (callbacks.isEmpty()) return;
        ArrayList<SignalConnectException> exceptions =
                new ArrayList<SignalConnectException>();
        
        synchronized(callbacks) {
            for (Map.Entry<Object, Method> entry : callbacks) {
                try {
                    
                    entry.getValue().invoke(entry.getKey(), args);
                    
                } catch (IllegalArgumentException e) {
                    Slot slot = entry.getValue().getAnnotation(Slot.class);
                    if (slot==null) {
                        SignalConnectException error =
                                new SignalConnectException(ERROR_NOT_A_SLOT, name, null);
                        exceptions.add(error);
                    } else {
                        String errorMessage = "Invalid method definition in @Slot '" +
                                slot.value() + "' on object " + entry.getKey().toString();
                        SignalConnectException error = new SignalConnectException(
                                        errorMessage, name, slot.value());
                        exceptions.add(error);  
                    }
                } catch (IllegalAccessException e) {
                    Slot slot = entry.getValue().getAnnotation(Slot.class);
                    if (slot==null) {
                        SignalConnectException error =
                                new SignalConnectException(ERROR_NOT_A_SLOT, name, null);
                        exceptions.add(error);
                    } else {
                        String errorMessage = "Access restrictions prevented a message " +
                                "from being delivered to @Slot '" + slot.value() + 
                                "' on object " + entry.getKey().toString();
                        SignalConnectException error = new SignalConnectException(
                                        errorMessage, name, slot.value());
                        exceptions.add(error); 
                    }
                } catch (InvocationTargetException e) {
                    Slot slot = entry.getValue().getAnnotation(Slot.class);
                    if (slot==null) {
                        SignalConnectException error =
                                new SignalConnectException(ERROR_NOT_A_SLOT, name, null);
                        exceptions.add(error);
                    }
                    String errorMessage = "@Slot '" + slot.value() + "' on object " +
                            entry.getKey().toString() + " threw an exception -> " +
                            e.getCause().toString();
                    SignalConnectException error =
                            new SignalConnectException(errorMessage, name, slot.value());
                    exceptions.add(error);
                }
            }
        }
        
        for(SignalConnectException e : exceptions) throw e;
    }
    
    private String toClassList(Class<?>[] definition) {
        String result = "(";
        for(int i=0; i<definition.length; i++) {
            result+=definition[i].getSimpleName();
            if (i<definition.length-1) result+=", ";
        }
        result+=")";
        return result;
    }
    
    
    
    /**
     * Connect this signal to all slots of the designated name on the recipient.
     * 
     * @param o
     *          The object containing the @Slot method to connect to
     * @param slotName
     *          The name of the @Slot to connect to - <i>the name specified in the
     *          annotation, not the name of the method</i>
     * @return
     *          this Signal
     */
    public Signal connectTo(Object o, String slotName) throws SignalConnectException {
        // find candidate methods
        boolean candidatesFound = false;
        for (Method m : o.getClass().getMethods()) {
            Slot s = m.getAnnotation(Slot.class);
            if (s != null) {
                String foundSlotName = s.value();
                if (foundSlotName==null || foundSlotName.isEmpty()) {
                    foundSlotName = m.getName();
                }
                
                if (foundSlotName.equals(slotName)) {
                    // This method is annotated as a Slot with the correct name.
                    addCallback(o, m);
                    candidatesFound = true;
                }
            }
        }
        
        if (!candidatesFound) {
            throw new SignalConnectException("Could not find target @Slot '" + slotName +
                    "' on Object "+o.toString(), name, slotName);
        }
        
        return this;
    }
    
    /* TODO: WORK IN PROGRESS
    public void disconnect(Object o, String slotName) {
        
        synchronized(callbacks) {
            for(Map.Entry<Object, Method> entry : callbacks) {
                if (entry.getKey().equals(o)) {
                    String 
                }
            }
        }
    }*/
    
    public void disconnectAll(Object o) {
        //TODO: implement
    }
    
    public void disconnectAll() {
        //TODO: implement
    }
}
