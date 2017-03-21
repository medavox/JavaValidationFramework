package com.medavox.validation;

/**
 * @author Adam Howard
 * @date 06/03/17
 */

import android.content.Context;
import android.util.Log;

import com.medavox.validation.rules.ValidationRule;

import java.util.LinkedList;
import java.util.List;

/**Runs a series of {@link ValidationRule}s in order,
 * feeding the result of each rule to the input of the next.
 * If any rule fails, its side-effect method is called.*/
public abstract class Validator<StartType> {

    private Class startType;
    private ValidationResults<Object> results;

    private List<ValidationRule> rules = new LinkedList<>();

    public Validator(Class<StartType> startType) {
        this.startType = startType;
        //this.endType = endType;
    }

    public Validator append(ValidationRule rule) {
        //if the rules list is empty, check that the type of the first rule's Tin is the same as our StartType
        //todo: work out how to do this

        if(rules.size() == 0 && !rule.getTypeIn().equals(startType)) {
            throw new IllegalArgumentException("Type in of first rule must be the same as start type");
        }
        rules.add(rule);
        return this;
    }

    public abstract void onFailure(ValidationRule failedRule, Object failingObject, Context ctx);

    public Object validateAllRulesInOrder(StartType initialInput, Context context) {
        Object workingVariable = initialInput;
        results = new ValidationResults<>();

        for(int i = 0; i < rules.size(); i++) {
            //early check that this rule's output type matches the next rule's input type
            if(i+1 < rules.size()) {
                Class nextIn = rules.get(i + 1).getTypeIn();
                Class out = rules.get(i).getTypeOut();
                if (!nextIn.equals(out) && !nextIn.isInstance(out)) {
                    throw new IllegalStateException
                            ("the output type for each rule must match the input type of the next.\n" +
                                    "current rule: " + rules.get(i) + "\n" +
                                    "next rule   : " + rules.get(i + 1));
                }
            }
            //check that the type of the input object matches the input type of the rule
            // that's about to be run
            boolean isValid = rules.get(i).validate(workingVariable);
            if(isValid) {
                workingVariable = rules.get(i).onSuccess(workingVariable, results);
            }
            else {
                Log.e("Validator", "rule failed! rule: "+rules.get(i));
                onFailure(rules.get(i), workingVariable, context);
                rules.get(i).onFailure(workingVariable, context);
                break;
            }
        }
        //check that the final test has left the working variable with an object of the expected end type
        //if(workingVariable.getClass().equals(rules.get(rules.size()-1).getTypeOut())) {
        return workingVariable;
        //throw new IllegalStateException("final variable was not of expected final type!\n"+
        //"expected final type:"+EndType)
    }

    public ValidationResults getResults() {
        return results;
    }
}
