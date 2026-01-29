package model.statement;

import model.exception.InvalidTypeException;
import model.expression.Expression;
import model.expression.RelationalExpression;
import model.expression.VariableExpression;
import model.types.Type;
import state.ProgramState;

import java.util.HashMap;
import java.util.Map;

public record ForStatement(String varName,Expression exp1, Expression exp2, Expression exp3, Statement statement) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        Statement converted = new CompoundStatement(
                new VariableDeclarationStatement(Type.INTEGER, varName),
                new CompoundStatement(
                        new AssignmentStatement(varName, exp1),
                        new WhileStatement(new RelationalExpression(
                                new VariableExpression(varName),
                                exp2,
                                "<"
                        ),
                                new CompoundStatement(statement,
                                        new AssignmentStatement(varName, exp3))
                        )

                )
        );
        state.executionStack().push(converted);
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        Map<String, Type> temporaryEnv = new HashMap<>(typeEnv);
        temporaryEnv.put(varName, new Type.Integer());

        Type t1 = exp1.typecheck(typeEnv);
        Type t2 = exp2.typecheck(temporaryEnv);
        Type t3 = exp3.typecheck(temporaryEnv);

        if (t1.equals(new Type.Integer()) && t2.equals(new Type.Integer()) && t3.equals(new Type.Integer())) {
            statement.typeCheck(temporaryEnv);
            return typeEnv;
        } else {
            throw new Exception("ForStatement: All expressions must be integers.");
        }
    }
}
