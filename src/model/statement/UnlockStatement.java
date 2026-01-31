package model.statement;

import model.exception.InvalidTypeException;
import model.types.Type;
import model.value.IntegerValue;
import state.ProgramState;
import state.exceptions.AddressNotInLockTable;
import state.exceptions.VariableNotInTableException;

import java.util.Map;

public record UnlockStatement(String variable) implements Statement {

    @Override
    public ProgramState execute(ProgramState state) {
        if (state.symbolTable().isDefined(variable)) {
            var value = state.symbolTable().LookUp(variable);
            if (value instanceof IntegerValue IntegerValue) {
                int foundIndex = (Integer) IntegerValue.value();
                synchronized (state.lockTable()) {
                    if (state.lockTable().isDefined(foundIndex)) {
                        int lockValue = state.lockTable().lookUp(foundIndex);
                        if (lockValue == state.id()) {
                            state.lockTable().update(foundIndex, -1);
                        }
                    } else {
                        throw new AddressNotInLockTable("Address " + foundIndex + " is not in lock table");
                    }
                }
            }
            else {
                throw new InvalidTypeException(variable + " is not a valid variable");
            }
        }
        else{
            throw new VariableNotInTableException(variable);
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        Type typeVar = typeEnv.get(variable);
        if (typeVar == null) {
            throw new Exception("Unlock: Variable " + variable + " is not defined.");
        }
        if (typeVar.equals(new Type.Integer())) {
            return typeEnv;
        } else {
            throw new Exception("Unlock: Variable " + variable + " must be of type Int.");
        }
    }
}
