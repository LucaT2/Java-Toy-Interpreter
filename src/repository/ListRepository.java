package repository;

import repository.exception.FileRepositoryException;
import repository.exception.ProgramStateNotFound;
import state.ProgramState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListRepository implements Repository {
    private final List<ProgramState> programStates = new ArrayList<>();
    private final PrintWriter logFile;
    public ListRepository(String logFilePath){
        //IO.println("Loading program states...");
        try {
            this.logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath)));
        }
        catch (Exception e) {
            throw new FileRepositoryException(e.getMessage());
        }
    }
    public ProgramState getCurrentProgramState(){
        return programStates.getFirst();
    }
    public void addProgramState(ProgramState programState){
        programStates.add(programState);
    }
    public void removeProgramState(ProgramState programState){
        if (!programStates.contains(programState)){
            throw new ProgramStateNotFound("Program state not found, try removing an existing program state");
        }
        programStates.remove(programState);
    }
    public void logProgramStateExecution(){
        ProgramState currentProgramState = getCurrentProgramState();
        this.logFile.write(currentProgramState.executionStack().toString() + "\n");
        this.logFile.write(currentProgramState.symbolTable().toString() + "\n");
        this.logFile.write(currentProgramState.out().toString() + "\n");
        this.logFile.write(currentProgramState.fileTable().toString() + "\n");
        this.logFile.flush();
    }
}
