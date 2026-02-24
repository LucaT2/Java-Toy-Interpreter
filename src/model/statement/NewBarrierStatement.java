package model.statement;

import model.exception.VariableNotDefinedException;
import model.types.Type;
import model.value.IntegerValue;
import model.value.Value;
import state.BarrierTable;
import state.BarrierTableMap;
import state.ProgramState;
import model.expression.Expression;
import java.util.Map;

public record NewBarrierStatement(String variableName, Expression expression) implements Statement{
    @Override
    public ProgramState execute(ProgramState state) {

        Value val = expression.evaluate(state.symbolTable(), state.heap());
        int nr = ((IntegerValue) val).value();
        int address = state.barrierTable().add(nr);
        if (state.symbolTable().isDefined(variableName)) {
            state.symbolTable().updateValue(variableName, new IntegerValue(address));
        }
        else {
            throw new VariableNotDefinedException();
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        Type exptype = expression.typecheck(typeEnv);
        if(typeEnv.containsKey(variableName)) {
            Type type = typeEnv.get(variableName);
            if(exptype.equals(new Type.Integer()) && type.equals(new Type.Integer())) {
                return typeEnv;
            }
            else{
                throw new Exception("Variable " + variableName + " is not assignable to " + exptype.toString());
            }
        }
        else{
            throw new Exception("NewBarrier: Variable " + variableName + " is not defined in the type environment!");        }
    }
}
