package state;

import javafx.util.Pair;
import model.value.Value;

import java.util.List;
import java.util.Map;

public interface SemaphoreTable {
    int add(Pair<Integer, List<Integer>> value);
    Pair<Integer, List<Integer>> lookUp(int address);
    void remove(int address);
    void update(int address, Pair<Integer, List<Integer>> value);
    Map<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable();

    void setContent(Map<Integer, Pair<Integer, List<Integer>>> newTable);
    boolean isDefined(int address);
}
