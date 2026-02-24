package model.statement;

import model.exception.InvalidTypeException;
import model.expression.Expression;
import model.expression.NotExpression;
import model.types.Type;
import model.value.BooleanValue;
import model.value.Value;
import state.ProgramState;

import java.util.HashMap;
import java.util.Map;

public record RepeatUntilStatement(Statement statement, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        Statement converted = new CompoundStatement(statement,
                new WhileStatement(new NotExpression(expression), statement)
        );
        state.executionStack().push(converted);

        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        Type typeexp = expression.typecheck(typeEnv);
        if(typeexp.equals(new Type.Boolean())) {
            statement.typeCheck(new HashMap<>(typeEnv));
            return typeEnv;
        }
        else throw new InvalidTypeException("Type mismatch in repeat until statement");
    }
    @Override
    public String toString() {
        return "repeat(" + statement.toString() + ") until (" + expression.toString() + ")";
    }
}
