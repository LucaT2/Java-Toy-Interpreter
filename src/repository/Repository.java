package repository;

import model.statement.Statement;
import state.ProgramState;

public interface Repository {
    ProgramState getCurrentProgramState();
    void addProgramState(ProgramState programState);
    void removeProgramState(ProgramState programState);

}
