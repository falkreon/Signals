package com.thoughtcomplex.signals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ParallelSignal extends Signal {
    public ParallelSignal(String name) {
        super(name);
    }
    
    @Override
    public void signal(final Object... args) {
        synchronized(getCallbacks()) {
            for (final Map.Entry<Object, Method> entry : getCallbacks()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            entry.getValue().invoke(entry.getKey(), args);
                        } catch (IllegalArgumentException e) {
                            return;
                        } catch (IllegalAccessException e) {
                            return;
                        } catch (InvocationTargetException e) {
                            return;
                        }
                    }
                }).start();
            }
        }
    }
}
