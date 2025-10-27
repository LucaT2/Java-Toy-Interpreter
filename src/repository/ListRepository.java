package repository;

import model.statement.Statement;
import repository.exception.ProgramStateNotFound;
import state.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class ListRepository implements Repository {
    private final List<ProgramState> programStates = new ArrayList<>();
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
}
