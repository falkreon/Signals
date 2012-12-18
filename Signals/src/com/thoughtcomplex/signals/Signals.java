package com.thoughtcomplex.signals;

import java.lang.reflect.Field;

public class Signals {
    
    /**
     * Connect a public Signal on the source object to a public @Slot method on
     * the destination object.
     * 
     * @param src
     *            The Object that sends the Signal
     * @param srcMessage
     *            The name of the Signal being connected
     * @param dest
     *            The Object containing a @Slot method.
     * @param destMessage
     *            The name registered in the @Slot annotation, not the name of
     *            the method itself.
     * @throws SignalConnectException
     *             if the signal field or slot method aren't found or
     *             inaccessible, or if the connection cannot be made for any
     *             other reason.
     */
    public static void connect(Object src, String srcMessage, Object dest,
            String destMessage) throws SignalConnectException {
        for (Field field : src.getClass().getFields()) {
            if (Signal.class.isAssignableFrom(field.getType())) {
                try {
                    Signal signal = (Signal) field.get(src);
                    if (signal.getName().equals(srcMessage)) {
                        signal.connectTo(dest, destMessage);
                        return;
                    }
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                }
            }
        }
        throw new SignalConnectException(
                "No public Signal found with that name.", srcMessage,
                destMessage);
    }
}
