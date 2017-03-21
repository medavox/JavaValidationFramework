package com.medavox.validation.rules;

/**
 * @author Adam Howard
 * @date 07/03/17
 */

import com.medavox.validation.ValidationResults;

/**Useful for when a validation test does not modify the input variable (only checking it in-place),
 * and instead passes it to the next rule.*/
public abstract class InPlaceRule<T> extends AbstractRule<T, T> {
/*
    public InPlaceRule(Class<T> throughType) {
        super(throughType, throughType);
    }
*/
    public InPlaceRule(Class<T> throughType, String reason) {
        super(throughType, throughType, reason);
    }

    @Override
    public T onSuccess(T validObj, ValidationResults results) {
        return validObj;
    }
}