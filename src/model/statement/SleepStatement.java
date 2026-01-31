package model.statement;

import model.types.Type;
import state.ProgramState;

import java.util.Map;

public record SleepStatement(int number) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        if (number!=0){
            state.executionStack().push(new SleepStatement(number-1));
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws Exception {
        return typeEnv;
    }
}
