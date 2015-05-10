package com.jack.android.tutorial.algorithms.calculator.exceptions;

/**
 * A specific exception to indicate one or more operators were not supported for the Calculator.
 */
public class OperatorNotSupportedException extends CalculationException {
    private static final String ERROR_MESSAGE = "OperatorNotSupportedException";

    public OperatorNotSupportedException() {
        super(ERROR_MESSAGE);
    }
}