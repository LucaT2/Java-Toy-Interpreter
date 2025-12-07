package state;

import state.exceptions.EmptyStackException;

public record ProgramState(
        ExecutionStack executionStack,
        SymbolTable symbolTable,
        Out out,
        FileTable fileTable,
        Heap heap) {
    private final static int id = 0;
    public int id(){
        return id;
    }
    Boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }
    public ProgramState oneStep(){
        if (executionStack.isEmpty()){
            throw new EmptyStackException("Cannot execute empty program state stack");
        }
        return executionStack.pop().execute(this);
    }
}
