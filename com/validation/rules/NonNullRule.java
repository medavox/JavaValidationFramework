package com.medavox.validation.rules;

/**
 * @author Adam Howard
 * @date 17/03/2017
 */

/**Rule which checks the input variable of type {@link ThroughType} is not null.*/
public class NonNullRule<ThroughType> extends InPlaceRule<ThroughType> {

    public NonNullRule(Class<ThroughType> t) {
        super(t, "object of type "+t.getClass().getSimpleName()+" was null");
    }

    @Override
    public boolean validate(ThroughType objectToValidate) {
        return objectToValidate != null;
    }
}
