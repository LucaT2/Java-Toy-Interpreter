package model.statement;

import model.exception.InvalidTypeException;
import model.types.Type;
import model.value.IntegerValue;
import model.value.Value;
import state.ProgramState;
import state.exceptions.VariableNotInTableException;

import java.util.Map;

public record NewLock(String variable) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        if (state.symbolTable().isDefined(variable)) {
            if (state.symbolTable().LookUp(variable) instanceof IntegerValue) {
                int address = state.lockTable().add(-1);
                Value newValue = new IntegerValue(address);
                state.symbolTable().updateValue(variable, newValue);
            }
            else{
                throw new InvalidTypeException("Variable " + variable + " is not int");
            }
        }
        else {
            throw new VariableNotInTableException("Variable " + variable + " is not defined");
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        Type typeVar = typeEnv.get(variable);
        if (typeVar == null) {
            throw new Exception("Variable " + variable + " is not defined in the type environment.");
        }

        if (typeVar.equals(new Type.Integer())) {
            return typeEnv;
        } else {
            throw new Exception("NewLock: " + variable + " is not of type Int.");
        }
    }
}
