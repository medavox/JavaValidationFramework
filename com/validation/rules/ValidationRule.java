package com.medavox.validation.rules;

import android.content.Context;

import com.medavox.validation.ValidationResults;

/**
 * @author Adam Howard
 * @date 06/03/17
 */
/**Subclasses of this class perform some arbitrary check on the object of type {@link Tin}. Either
 * one of {@link #onSuccess(Object, ValidationResults) two} {@link #onFailure(Object, Context) callbacks}
 * are then called, depending on whether the validation passed or failed. */
public interface ValidationRule<Tin, Tout> {

    /**Check that the passed object passes some arbitrary validation criteria,
     * specified by this method's implementation.
     * @param objectToValidate the object to perform the validation check on
     * @return true if the validation test passed, false if it didn't
     * @throws Exception if validation failed so badly that it causes further problems*/
    boolean validate(Tin objectToValidate);

    /**Called after {@link #validate(Tin)}, if it returned true.
     * You can optionally perform some action here, such as transforming the input object,
     * in the case that the object passed validation.
     * If no action is desired here, implementations should return the same object passed.
     * @param validObject the object which passed validation
     * @param results
     * @return an object of the type specified by tge second type parameter {@link Tout} the output type*/
    Tout onSuccess(Tin validObject, ValidationResults results);

    /**Called after {@link #validate(Tin)}, if it returned false. You can perform some action here,
     * in the event that the object failed validation.
     * @param invalidObject the offending object, which failed validation
     * @param context a convenience {@link Context} object, for interacting with the Android system
     *                (eg, to display an error {@link android.widget.Toast}.*/
    void onFailure(Tin invalidObject, Context context);

    /**Returns a human-readable (though not necessarily user-friendly) error message,
     * describing how the validation rule failed. Eg, "the string was null"*/
    String getFailureMessage();

    /**Gets the type of the input object, as a class.
     * @return a {@link Class} object of the type parameter specified as {@link Tin}*/
    Class<Tin> getTypeIn();

    /**Gets the type of the output object, as a class.
     * @return a {@link Class} object of the type parameter specified as {@link Tout}*/
    Class<Tout> getTypeOut();

    //ValidationRule<Tout, ?> chain(Tin validObject);
}
