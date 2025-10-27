package repository;

import model.statement.Statement;
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
        programStates.remove(programState);
    }
}
