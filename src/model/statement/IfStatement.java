package model.statement;

import model.exception.InvalidTypeException;
import model.expression.Expression;
import model.value.BooleanValue;
import model.value.Value;
import state.ProgramState;

public record IfStatement
        (Expression condition, Statement thenStatement, Statement elseStatement) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        Value value = condition.evaluate(state.symbolTable(), state.heap());
        if (!(value instanceof BooleanValue(boolean booleanValue))) {
            throw new InvalidTypeException("Type mismatch");
        }

        Statement chosenStatement = booleanValue ? thenStatement : elseStatement;

        state.executionStack().push(chosenStatement);

        return null;
    }
    @Override
    public String toString() {
        return "If: " + condition.toString() + " then " + thenStatement.toString() + " else " + elseStatement.toString();
    }
}
