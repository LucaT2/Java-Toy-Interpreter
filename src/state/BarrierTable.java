package state;

import javafx.util.Pair;
import model.value.Value;

import java.util.List;
import java.util.Map;

public interface BarrierTable {

    int add(int capacity);
    Pair<Integer, List<Integer>> lookUp(int address);
    void remove(int address);
    void  update(int address, Pair<Integer, List<Integer>> pair);
    Map<Integer, Pair<Integer, List<Integer>>> getBarrierTable();
    boolean isDefined(int address);
    void setContent(Map<Integer, Pair<Integer, List<Integer>>> heapMap);
}
