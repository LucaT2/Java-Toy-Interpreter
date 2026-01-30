package model.statement;

import model.exception.InvalidTypeException;
import model.expression.RelationalExpression;
import model.types.Type;
import state.ProgramState;

import java.util.Map;
import model.expression.Expression;

public record SwitchStatement(Expression exp, Expression exp1, Expression exp2, Statement st1, Statement st2, Statement st3)  implements Statement{
    @Override
    public ProgramState execute(ProgramState state) {
        Statement converted = new IfStatement(new RelationalExpression(exp, exp1, "=="), st1,
                new IfStatement(new RelationalExpression(exp, exp2, "=="), st2, st3));
        state.executionStack().push(converted);
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
       Type t1 = exp.typecheck(typeEnv);
       Type t2 = exp1.typecheck(typeEnv);
       Type t3 = exp2.typecheck(typeEnv);
       if (t1.equals(t2) && t2.equals(t3)) {
           st1.typeCheck(typeEnv);
           st2.typeCheck(typeEnv);
           st3.typeCheck(typeEnv);
           return typeEnv;
       }
       else throw new InvalidTypeException("Expression types are not equal");
    }
}
