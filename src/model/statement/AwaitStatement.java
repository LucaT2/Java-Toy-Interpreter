package model.statement;

import model.exception.InvalidTypeException;
import model.types.Type;
import model.value.IntegerValue;
import state.ProgramState;
import state.exceptions.VariableNotInTableException;

import java.util.Map;

public record AwaitStatement(String variable)  implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        if (state.symbolTable().isDefined(variable)) {
            if (state.symbolTable().LookUp(variable) instanceof IntegerValue (int foundIndex)) {
                if (state.latchTable().isDefined(foundIndex)) {
                    if (state.latchTable().lookUp(foundIndex) != 0){
                        state.executionStack().push(this);
                    }
                }
                else {
                    throw new VariableNotInTableException(foundIndex + "not in latch");
                }
            }
            else{
                throw new InvalidTypeException("Variable " + variable + " is not int");
            }
        }
        else{
            throw new VariableNotInTableException(variable);
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        if (!typeEnv.containsKey(variable)) {
            throw new VariableNotInTableException("Variable " + variable + " not in type environment.");
        }
        if (!typeEnv.get(variable).equals(new Type.Integer())) {
            throw new InvalidTypeException("Await variable must be of type int.");
        }
        return typeEnv;
    }
}
