package state;

import javafx.util.Pair;
import state.exceptions.VariableNotInTableException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MapSemaphoreTable implements SemaphoreTable {
    private final Map<Integer, Pair<Integer, List<Integer>>> mapSem = new HashMap<>();
    private final AtomicInteger currentAddress = new AtomicInteger(1);

    @Override
    public synchronized int add(Pair<Integer, List<Integer>> value) {
        int address = currentAddress.getAndIncrement();
        mapSem.put(address, value);
        return address;
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> lookUp(int address) {
        if  (mapSem.containsKey(address)) {
            return mapSem.get(address);
        }
        else{
            throw new VariableNotInTableException("Variable not in semaphore table");
        }
    }

    @Override
    public synchronized void remove(int address) {
        if  (mapSem.containsKey(address)) {
            mapSem.remove(address);
        }
        else{
            throw new VariableNotInTableException("Variable not in semaphore table");
        }
    }

    @Override
    public synchronized void update(int address, Pair<Integer, List<Integer>> value) {
        if  (mapSem.containsKey(address)) {
            mapSem.put(address, value);
        }
        else{
            throw new VariableNotInTableException("Variable not in semaphore table");
        }
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        return mapSem;
    }

    @Override
    public synchronized void setContent(Map<Integer, Pair<Integer, List<Integer>>> newTable) {
        mapSem.clear();
        mapSem.putAll(newTable);
    }

    @Override
    public synchronized boolean isDefined(int address) {
        if (mapSem.containsKey(address)) {
            return true;
        }
        return false;
    }
}
