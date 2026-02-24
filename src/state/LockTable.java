package state;

import model.value.Value;

import java.util.Map;

public interface LockTable {
    int add(Integer value);
    Integer lookUp(int address);
    void remove(int address);
    void update(int address, Integer value);
    Map<Integer, Integer> getLockTable();
    boolean isDefined(int address);
    void setContent(Map<Integer, Integer> lockMap);
}
