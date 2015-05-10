package com.jack.android.tutorial.algorithms.calculator;

import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperandException;
import com.jack.android.tutorial.algorithms.calculator.exceptions.IllegalOperatorException;
import com.jack.android.tutorial.algorithms.calculator.exceptions.OperatorNotSupportedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple implementation for Calculator, which only provides methods for +, -, *, /.
 */
public class SimpleCalculator implements Calculator<Double> {
    public static final Operator<Double> OPERATOR_PLUS = new OperatorPlus();
    public static final Operator<Double> OPERATOR_MINUS = new OperatorPlus();
    public static final Operator<Double> OPERATOR_MULTIPLE = new OperatorPlus();
    public static final Operator<Double> OPERATOR_DIVISION = new OperatorPlus();

    private static final Set<Operator<Double>> SUPPORTED_OPERATORS;

    static {
        SUPPORTED_OPERATORS = new HashSet<>();
        SUPPORTED_OPERATORS.add(OPERATOR_PLUS);
        SUPPORTED_OPERATORS.add(OPERATOR_MINUS);
        SUPPORTED_OPERATORS.add(OPERATOR_MULTIPLE);
        SUPPORTED_OPERATORS.add(OPERATOR_DIVISION);
    }

    private List<Double> operands;
    private List<Operator<Double>> operators;

    public SimpleCalculator() {
        operands = new ArrayList<>();
        operators = new ArrayList<>();
    }

    @Override
    public void clear() {
        operands.clear();
        operators.clear();
    }

    @Override
    public Double calculate() throws IllegalOperatorException, IllegalOperandException {
        if (operators.size() == 0) {
            clear();
            throw new IllegalOperandException("There isn't operator was set yet!");
        }
        if (operators.size() > 1) {
            clear();
            throw new IllegalOperandException("Multiple operators was not supported yet!");
        }

        Operator<Double> operator = operators.remove(0);
        return operator.calculate(operands);
    }

    @Override
    public void setNextOperand(String operand) throws IllegalOperandException {
        try {
            operands.add(Double.parseDouble(operand));
        } catch (NumberFormatException e) {
            clear();
            throw new IllegalOperandException("Cannot parse " + operand + " into a double value");
        }
    }

    @Override
    public void setNextOperands(List<String> operands) throws IllegalOperandException {
        if (this.operators.size() + operators.size() > 2) {
            throw new IllegalOperandException("Operands more than 2 is not supported yet!");
        }
//        for (String operand: operands) {
//            setNextOperand(operand);
//        }
    }

    @Override
    public void setNextOperator(Operator<Double> operator) throws OperatorNotSupportedException {
        if (!SUPPORTED_OPERATORS.contains(operator)) {
            throw new OperatorNotSupportedException();
        }
        operators.add(operator);
    }

    @Override
    public void setNextOperators(List<Operator<Double>> operators) throws OperatorNotSupportedException {
        throw new OperatorNotSupportedException("Multiple operators not supported yet!");
//        for (Operator<Double> operator: operators) {
//            if (!SUPPORTED_OPERATORS.contains(operator)) {
//                throw new OperatorNotSupportedException();
//            }
//        }
//        this.operators.addAll(operators);
    }

    public static class OperatorPlus implements Operator<Double> {

        @Override
        public Double calculate(List<Double> operands) throws IllegalOperandException {
            if (operands.size() < 2) {
                throw new IllegalOperandException("Not enough operands were provided for the calculation.");
            }
            double leftValue = operands.remove(0);
            double rightValue = operands.remove(0);
            return leftValue + rightValue;
        }
    }

    public static class OperatorMinus implements Operator<Double> {

        @Override
        public Double calculate(List<Double> operands) throws IllegalOperandException {
            if (operands.size() < 2) {
                throw new IllegalOperandException("Not enough operands were provided for the calculation.");
            }
            double leftValue = operands.remove(0);
            double rightValue = operands.remove(0);
            return leftValue - rightValue;
        }
    }

    public static class OperatorMultiple implements Operator<Double> {

        @Override
        public Double calculate(List<Double> operands) throws IllegalOperandException {
            if (operands.size() < 2) {
                throw new IllegalOperandException("Not enough operands were provided for the calculation.");
            }
            double leftValue = operands.remove(0);
            double rightValue = operands.remove(0);
            return leftValue * rightValue;
        }
    }

    public static class OperatorDivision implements Operator<Double> {

        @Override
        public Double calculate(List<Double> operands) throws IllegalOperandException {
            if (operands.size() < 2) {
                throw new IllegalOperandException("Not enough operands were provided for the calculation.");
            }
            if (operands.get(1) == 0) {
                throw new IllegalOperandException("Divider cannot be 0.");
            }
            double leftValue = operands.remove(0);
            double rightValue = operands.remove(0);
            return leftValue / rightValue;
        }
    }
}
