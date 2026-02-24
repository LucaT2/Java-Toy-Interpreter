package model.statement;

import model.exception.InvalidTypeException;
import model.types.Type;
import model.value.IntegerValue;
import state.ProgramState;
import state.exceptions.VariableNotInTableException;

import java.util.List;
import java.util.Map;

public record Acquire(String variable) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        if (state.symbolTable().isDefined(variable)) {
            if (state.symbolTable().LookUp(variable) instanceof IntegerValue){
                int foundIndex = ((IntegerValue) state.symbolTable().LookUp(variable)).value();
                synchronized (state.semaphoreTable()) {
                    if (state.semaphoreTable().isDefined(foundIndex)){
                        List<Integer> threadList = state.semaphoreTable().lookUp(foundIndex).getValue();
                        int lengthList = threadList.size();
                        int lengthSemaphore = state.semaphoreTable().lookUp(foundIndex).getKey();
                        if (lengthSemaphore > lengthList){
                            if(threadList.contains(state.id())){

                            }
                            else{
                                threadList.add(state.id());
                            }
                        }
                        else{
                            state.executionStack().push(this);
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
            throw new InvalidTypeException("Acquire: variable must be an integer");

        return typeEnv;
    }
}
