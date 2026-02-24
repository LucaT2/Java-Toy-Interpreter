package model.statement;

import model.exception.InvalidTypeException;
import model.types.Type;
import state.ProgramState;
import model.expression.Expression;
import state.exceptions.VariableNotInTableException;

import java.util.Map;

public record ConditionalAssignmentStatement(String variable, Expression exp1, Expression exp2, Expression exp3)  implements  Statement{

    @Override
    public ProgramState execute(ProgramState state) {
        Statement converted =
                    new IfStatement(exp1,
                            new AssignmentStatement(variable, exp2),
                            new AssignmentStatement(variable, exp3));
        state.executionStack().push(converted);
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        if (!typeEnv.containsKey(variable)) {
            throw new VariableNotInTableException("Variable " + variable + " was not declared.");
        }
        Type typeVar = typeEnv.get(variable);

        Type typeCond = exp1.typecheck(typeEnv);
        Type type1 = exp2.typecheck(typeEnv);
        Type type2 = exp3.typecheck(typeEnv);

        if (typeCond.equals(new Type.Boolean())) {
            if (type1.equals(typeVar) && type2.equals(typeVar)) {
                return typeEnv;
            } else {
                throw new InvalidTypeException("The types of the then/else expressions do not match the variable type: " + variable);
            }
        } else {
            throw new InvalidTypeException("Conditional Assignment Error: Condition (exp1) must be boolean.");
        }
    }
    @Override
    public String toString(){
        return  variable +"= " + "(" + exp1 + ")" + " ? " + exp2 + ":" + exp3;
    }
}
