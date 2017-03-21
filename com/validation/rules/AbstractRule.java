package com.medavox.validation.rules;

import android.content.Context;

/**
 * @author Adam Howard
 * @date 07/03/17
 */

/**Skeleton implementation of a {@link ValidationRule}.
 * Concrete subclasses should implement a default constructor,
 * which calls {@link #AbstractRule(Class, Class)} with its type classes,
 * and also implement {@link ValidationRule#validate(Object)}.*/
public abstract class AbstractRule<Tin, Tout> implements ValidationRule<Tin, Tout> {

    Class<Tin> inClass;
    Class<Tout> outClass;
    protected String failureMessage;

    /**Concrete subclasses should implement a default constructor which calls this with their type classes.
     * Eg, for {@code class ConcreteRule extends AbstractRule<String, Object>},
     * {@code public ConcreteRule(){super(String.class, Object.class);}}.*/
    /*public AbstractRule(Class<Tin> inType, Class<Tout> outType) {
        this.inClass = inType;
        this.outClass = outType;
    }*/

    public AbstractRule(Class<Tin> inType, Class<Tout> outType, String failureMessage) {
        this.inClass = inType;
        this.outClass = outType;
        this.failureMessage = failureMessage;
    }

    @Override
    public String toString() {
        return /*super.toString() +*/
                "class: "+getClass().getSimpleName()+
                "; input type: "+inClass.getSimpleName()+
                "; output type: "+outClass.getSimpleName()+
                "; failureMessage: \""+ failureMessage +"\"";
    }

    @Override
    public String getFailureMessage() {
        return failureMessage;
    }

    @Override
    public void onFailure(Tin invalidObject, Context context) { }

    @Override
    public Class<Tin> getTypeIn() {
        return inClass;
    }

    @Override
    public Class<Tout> getTypeOut() {
        return outClass;
    }
}
