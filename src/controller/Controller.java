package controller;

import model.statement.Statement;
import repository.Repository;
import state.*;
import state.exceptions.EmptyStackException;

public class Controller {
    private final Repository repository;
    public Controller(Repository repository) {
        this.repository = repository;
    }
    public ProgramState oneStep(ProgramState state){
        ExecutionStack executionStack = state.executionStack();
        if (executionStack.isEmpty()){
            throw new EmptyStackException("Cannot execute empty program state stack");
        }
        Statement statement = executionStack.pop();
        state = statement.execute(state);
        return state;
        //IO.println(state.toString());
    }
    public void allStep(){
        ProgramState programState = repository.getCurrentProgramState();
        repository.logProgramStateExecution(programState);
        while (!programState.executionStack().isEmpty()){
            programState = oneStep(programState);
            repository.logProgramStateExecution(programState);
            GarbageCollector garbageCollector = new GarbageCollectorMap();
            programState.heap().setContent(garbageCollector.safeGarbageCollector(
                    garbageCollector.getAddressesFromSymTable(programState.symbolTable().getContents().values()
                    ), programState.heap().getHeapMap()));
            repository.logProgramStateExecution(programState);
        }
    }
    public void addProgramState(ProgramState programState){
        repository.addProgramState(programState);
    }

    public void removeProgramState(ProgramState programState){
        repository.removeProgramState(programState);
    }

    public void runAll(Statement statement){
        ExecutionStack executionStack = new ListExecutionStack();
        executionStack.push(statement);
        ProgramState programState = new ProgramState(
                executionStack,
                new MapSymbolTable(),
                new ListOut(),
                new MapFileTable(),
                new HeapMap());
        addProgramState(programState);
        allStep();
        removeProgramState(programState);
    }
}
