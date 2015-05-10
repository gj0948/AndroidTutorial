package com.jack.android.tutorial.algorithms.calculator.exceptions;

/**
 * A specific exception to indicate one or more illegal operators were provided for the Calculator.
 */
public class IllegalOperatorException extends CalculationException {
    private static final String ERROR_MESSAGE = "IllegalOperatorException";

    public IllegalOperatorException() {
        super(ERROR_MESSAGE);
    }
}
