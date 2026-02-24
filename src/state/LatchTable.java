package state;

import model.value.Value;

import java.util.Map;

public interface LatchTable {
    int add(Integer value);
    Integer lookUp(int address);
    void remove(int address);
    void update(int address, Integer value);
    Map<Integer, Integer> getLatchMap();
    boolean isDefined(int address);
    void setContent(Map<Integer, Integer> heapMap);
    public void countDown(int address);
}
