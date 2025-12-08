package model.statement;

import state.ProgramState;

public class NoOperationStatement implements Statement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }
    @Override
    public String toString() {
        return "No Operation";
    }
}
