package state;

import state.exceptions.VariableNotInTableException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MapLatchTable implements LatchTable{
    private final Map<Integer, Integer> latchMap = new HashMap<>();
    private final AtomicInteger currentAddress = new AtomicInteger(1);

    @Override
    public synchronized int add(Integer value) {
        int address = currentAddress.getAndIncrement();
        latchMap.put(address, value);
        return address;
    }

    @Override
    public synchronized Integer lookUp(int address) {
        if (latchMap.containsKey(address)){
            return latchMap.get(address);
        }
        else {
            throw new VariableNotInTableException("No such address " + address);
        }

    }

    @Override
    public synchronized void remove(int address) {
        if(latchMap.containsKey(address)){
            latchMap.remove(address);
        }
        else {
            throw new VariableNotInTableException("Address not found");
        }
    }

    @Override
    public synchronized void update(int address, Integer value) {
        if (latchMap.containsKey(address)){
            latchMap.put(address, value);
        }
        else {
            throw new RuntimeException("Address not found");
        }
    }

    @Override
    public synchronized Map<Integer, Integer> getLatchMap() {
        return latchMap;
    }

    @Override
    public synchronized boolean isDefined(int address) {
        if (latchMap.containsKey(address)){
            return true;
        }
        return false;
    }

    @Override
    public synchronized void setContent(Map<Integer, Integer> newMap) {
        latchMap.clear();
        latchMap.putAll(newMap);
    }

    // Add this to MapLatchTable
    @Override
    public synchronized void countDown(int address) {
        if (latchMap.containsKey(address)) {
            int current = latchMap.get(address);
            if (current > 0) {
                latchMap.put(address, current - 1);
            }
        }
    }
}
