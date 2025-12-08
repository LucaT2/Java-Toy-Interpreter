package model.statement;

import model.exception.InvalidTypeException;
import model.exception.VariableNotDefinedException;
import model.expression.Expression;
import model.value.Value;
import state.ProgramState;
import state.SymbolTable;

public record AssignmentStatement
        (String variableName, Expression expression) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTable symbolTable = state.symbolTable();
        if (!symbolTable.isDefined(variableName)) {
            throw new VariableNotDefinedException();
        }

        Value value = expression.evaluate(symbolTable, state.heap());
        if (value.getType() != symbolTable.getVariableType(variableName)) {
            throw new InvalidTypeException("Type mismatch");
        }

        symbolTable.updateValue(variableName, value);
        return null;
    }
    @Override
    public String toString() {
        return variableName + " = " + expression +";";
    }
}
