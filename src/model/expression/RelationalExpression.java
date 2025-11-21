package model.expression;

import model.exception.InvalidTypeException;
import model.exception.UnknownOperatorException;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;
import state.Heap;
import state.SymbolTable;

public record RelationalExpression(Expression leftOperand, Expression rightOperand, String operator)
implements Expression{
    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) {
        Value leftValue = leftOperand.evaluate(symbolTable, heap);
        if (!(leftValue instanceof IntegerValue(int leftInteger)))
            throw new InvalidTypeException("Type mismatch");

        Value rightValue = rightOperand.evaluate(symbolTable,  heap);
        if (!(rightValue instanceof IntegerValue(int rightInteger)))
            throw new InvalidTypeException("Type mismatch");

        boolean result = switch (operator) {
            case "<" -> leftInteger < rightInteger;
            case "<=" -> leftInteger <= rightInteger;
            case "==" -> leftInteger == rightInteger;
            case "!=" -> leftInteger != rightInteger;
            case ">" -> leftInteger > rightInteger;
            case ">=" -> leftInteger >= rightInteger;
            default -> throw new UnknownOperatorException();
        };

        return new BooleanValue(result);
    }
    @Override
    public String toString() {
        return leftOperand.toString() + " " + operator + " " + rightOperand.toString();
    }
}
