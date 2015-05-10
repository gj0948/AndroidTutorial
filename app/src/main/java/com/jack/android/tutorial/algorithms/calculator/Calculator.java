package com.jack.android.tutorial.algorithms.calculator;

import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperandException;
import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperatorException;

import java.util.List;

/**
 * Public interface for executing a calculation. The interface provide methods to reset the status, get result, set operators & operands.
 * Created by Theodore on 2015/5/9.
 */
public interface Calculator<ValueType> {
    /**
     * clear the status of the Calculator.
     */
    public void clear();

    /**
     * calculate the result based on the current operator & operand.
     *
     * @return the result of the calculation.
     * @throws com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperatorException - be thrown while the operators were illegal, and the status will be clear simultaneously.
     * @throws com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperandException  - be thrown while the operands where illegal, and the status will be clear simultaneously.
     */
    public ValueType calculate() throws IllegalOperatorException, IllegalOperandException;

    /**
     * set next operand for the Calculator.
     *
     * @param operand - the next operand for the Calculator. The Calculator may receive multiple calculators for execute the calculation.
     * @return true if the operand is legal, otherwise returns false.
     */
    public boolean setNextOperand(String operand);

    /**
     * set next operands for the Calculator.
     *
     * @param operands - the next operands for the Calculator. The Calculator may receive multiple operands for executing a calculation.
     * @return true if the operands are legal, otherwise returns false.
     */
    public boolean setNextOperands(List<String> operands);

    /**
     * set next operator for the Calculator.
     *
     * @param operator - the next operator for the Calculator. The Calculator may receive multiple operators for executing a calculation.
     * @return true if the operator is legal, otherwise returns false.
     */
    public boolean setNextOperator(Operator<ValueType> operator);

    /**
     * set next operators for the Calculator.
     *
     * @param operators - the next operators for the Calculator. The Calculator may receive multiple operators for executing a calculation.
     * @return true if the operators are legal, otherwise returns false.
     */
    public boolean setNextOperators(List<Operator<ValueType>> operators);
}
