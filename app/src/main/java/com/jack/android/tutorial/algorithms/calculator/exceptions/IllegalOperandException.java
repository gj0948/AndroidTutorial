package com.jack.android.tutorial.algorithms.calculator.exceptions;

/**
 * A specific exception to indicate one or more illegal operands were provided for the Calculator.
 */
public class IllegalOperandException extends CalculationException {
    private static final String ERROR_MESSAGE = "IllegalOperandException";

    public IllegalOperandException() {
        super(ERROR_MESSAGE);
    }

    public IllegalOperandException(String details) {
        super(ERROR_MESSAGE, details);
    }
}
