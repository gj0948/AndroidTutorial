package com.jack.android.tutorial.algorithms.calculator;

import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperandException;
import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperatorException;

import java.util.List;

/**
 * A simple implementation for Calculator, which only provides methods for +, -, *, /.
 */
public class SimpleCalculator implements Calculator<Double> {
    @Override
    public void clear() {

    }

    @Override
    public Double calculate() throws IllegalOperatorException, IllegalOperandException {
        return null;
    }

    @Override
    public boolean setNextOperand(String operand) {
        return false;
    }

    @Override
    public boolean setNextOperands(List<String> operands) {
        return false;
    }

    @Override
    public boolean setNextOperator(Operator<Double> operator) {
        return false;
    }

    @Override
    public boolean setNextOperators(List<Operator<Double>> operators) {
        return false;
    }
}
