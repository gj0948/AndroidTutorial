package com.jack.android.tutorial.algorithms.calculator;

import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperandException;

import java.util.List;

/**
 * Public interface to provide the actual calculation method.
 */
public interface Operator<ValueType> {
    /**
     * The actual calculation will be executed in this method.
     *
     * @param operands - all the operands need for this calculation.
     * @return the result for the calculation.
     * @throws IllegalOperandException - will be thrown if operands were not supported by this operator.
     */
    ValueType calculate(List<ValueType> operands) throws IllegalOperandException;
}
