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
        while (!programState.executionStack().isEmpty()){
            programState = oneStep(programState);
            IO.print(programState.executionStack());
            IO.print("\n");
            IO.print(programState.symbolTable());
            IO.print("\n");
            IO.print(programState.out());
            IO.println("\n");
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
        SymbolTable symbolTable = new MapSymbolTable();
        Out output = new ListOut();
        ProgramState programState = new ProgramState(executionStack, symbolTable, output);
        addProgramState(programState);
        allStep();
        removeProgramState(programState);
    }
}
