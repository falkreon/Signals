package com.thoughtcomplex.signals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>Specifies the parameters that a callback method can be responsibly passed.</p>
 * 
 * <p>While a Signal does not technically have to have a strongly-typed CallbackSignature,
 * it is highly recommended; should a Signal's method definition not match the Slot, the
 * Signals system will use the CallbackSignature to provide accurate diagnostics about
 * the point of fault.</p>
 * 
 * <p>To use this annotation, just place it directly above your Signal, listing the
 * Classes of each argument in order. For example, if the Slot method should look like:
 * 
 * <p><code>public void example(String message, int x, int y) {}</code></p>
 * 
 * <p>Then the Signal's CallbackSignature annotation should be:
 * 
 * <p><code>@CallbackSignature({String.class, Integer.class, Integer.class})
 * 
 * <p>The API user can't always see the annotations on a field. It's also recommended to
 * include a @signature javadoc comment in a form identical to an abstract method
 * definition, but without the abstract designation, return values, or access modifiers.
 * For example:
 * 
 * <p><code>@signature    onClicked(int x, int y, int button);</code></p>
 * 
 * <p>This provides a concise, descriptive explanation of how the signal is to be used,
 * in a way that can be inspected and documented by future tools. @param comments aren't
 * appropriate for this, because they don't include any type information.</p>
 * 
 * @author Isaac Ellingson
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CallbackSignature {
    Class<?>[] value();
}
