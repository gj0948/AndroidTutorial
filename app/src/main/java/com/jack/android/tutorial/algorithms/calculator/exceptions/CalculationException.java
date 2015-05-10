package com.jack.android.tutorial.algorithms.calculator.exceptions;

/**
 * Base exception used for Calculator to throw specific exceptions. This should not be used directly. Always use the other Exceptions inherits from this class.
 */
public class CalculationException extends Exception {
    public CalculationException(String errorMessage) {
        super(errorMessage);
    }

    public CalculationException(String errorMessage, String details) {
        super(errorMessage + " : " + details);
    }
}
