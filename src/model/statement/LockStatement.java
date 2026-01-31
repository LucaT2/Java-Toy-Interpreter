package model.statement;


import model.exception.InvalidTypeException;
import model.types.Type;
import model.value.IntegerValue;
import state.ProgramState;
import state.exceptions.AddressNotInLockTable;
import state.exceptions.VariableNotInTableException;

import java.util.Map;

public record LockStatement(String variable) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        if (state.symbolTable().isDefined(variable)) {
            var value = state.symbolTable().LookUp(variable);
            if (value instanceof IntegerValue IntegerValue) {
                int foundIndex = (Integer) IntegerValue.value();
                synchronized (state.lockTable()) {
                    if (state.lockTable().isDefined(foundIndex)) {
                        int lockValue = state.lockTable().lookUp(foundIndex);
                        if (lockValue == -1) {
                            state.lockTable().update(foundIndex, state.id());

                        } else {
                            state.executionStack().push(this);
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
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv){
        if (!typeEnv.containsKey(variable)) {
            throw new InvalidTypeException("Variable " + variable + " not defined.");
        }
        if (!(typeEnv.get(variable) instanceof Type.Integer())) {
            throw new InvalidTypeException("Variable " + variable + " is not of type Int.");
        }
        return typeEnv;
    }
}
