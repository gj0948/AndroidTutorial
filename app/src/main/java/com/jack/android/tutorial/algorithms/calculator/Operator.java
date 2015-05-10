package com.jack.android.tutorial.algorithms.calculator;

import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperandException;
import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperatorException;

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
     */
    ValueType calculate(List<ValueType> operands) throws IllegalOperatorException, IllegalOperandException;
}
