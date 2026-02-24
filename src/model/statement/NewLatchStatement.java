package model.statement;
import model.exception.InvalidTypeException;
import model.expression.Expression;
import model.types.Type;
import model.value.IntegerValue;
import model.value.Value;
import state.ProgramState;
import state.exceptions.VariableNotInTableException;

import java.util.Map;

public record NewLatchStatement(String variable, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        Value num1 = expression.evaluate(state.symbolTable(), state.heap());
        if (num1 instanceof IntegerValue (int integerValue)){
            if (state.symbolTable().isDefined(variable)){
                if (state.symbolTable().LookUp(variable) instanceof IntegerValue){
                    int address = state.latchTable().add(integerValue);
                    state.symbolTable().updateValue(variable, new IntegerValue(address));
                }
                else{
                    throw new InvalidTypeException("Variable " + variable + " is not int");
                }
            }
            else {
                throw new VariableNotInTableException(variable + "is not in table");
            }
        }
        else {
            throw new InvalidTypeException("Type not valid in NewLatch statement");
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        Type typeVar = typeEnv.get(variable);
        Type typeExp = expression.typecheck(typeEnv);

        if (typeVar.equals(new Type.Integer()) && typeExp.equals(new Type.Integer())) {
            return typeEnv;
        } else {
            throw new InvalidTypeException("Exception occured in NewLatchStatement: variable and expression must be of type Integer.");
        }
    }
}
