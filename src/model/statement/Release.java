package model.statement;

import model.exception.InvalidTypeException;
import model.types.Type;
import model.value.IntegerValue;
import state.ProgramState;
import state.exceptions.VariableNotInTableException;

import java.util.List;
import java.util.Map;

public record Release(String variable) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        if (state.symbolTable().isDefined(variable)) {
            if (state.symbolTable().LookUp(variable) instanceof IntegerValue){
                int foundIndex = ((IntegerValue) state.symbolTable().LookUp(variable)).value();
                synchronized (state.semaphoreTable()) {
                    if (state.semaphoreTable().isDefined(foundIndex)){
                        List<Integer> threadList = state.semaphoreTable().lookUp(foundIndex).getValue();
                        if (threadList.contains(state.id())){
                            threadList.remove(Integer.valueOf(state.id()));
                        }
                    }

                    else{
                        throw new VariableNotInTableException("Address " + foundIndex + " is not in semaphore table");
                    }
                }
            }
            else{
                throw new InvalidTypeException("Variable " + variable + " is not integer");
            }
        }
        else{
            throw new VariableNotInTableException(variable);
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        if (!typeEnv.containsKey(variable))
            throw new VariableNotInTableException("Variable " + variable + " not declared");

        if (!typeEnv.get(variable).equals(Type.INTEGER))
            throw new InvalidTypeException("Release: variable must be an integer");

        return typeEnv;
    }
}
