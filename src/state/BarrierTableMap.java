package state;

import javafx.util.Pair;
import state.exceptions.AddressNotInBarrierTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class BarrierTableMap  implements BarrierTable {
    private HashMap<Integer, Pair<Integer, List<Integer>>> barrierTableMap = new HashMap<>();
    private final AtomicInteger currentAddress = new AtomicInteger(1);

    @Override
    public synchronized int add(int capacity) {
        int addr = currentAddress.getAndIncrement();
        barrierTableMap.put(addr, new Pair<>(capacity, new ArrayList<Integer>()));
        return addr;
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> lookUp(int address) {
        if (barrierTableMap.containsKey(address)) {
            return barrierTableMap.get(address);
        }
        else throw new AddressNotInBarrierTable("Address not in Barrier Table");
    }

    @Override
    public synchronized void remove(int address) {
        if (barrierTableMap.containsKey(address)) {
            barrierTableMap.remove(address);
        }
        else throw new AddressNotInBarrierTable("Address not in Barrier Table");
    }

    @Override
    public synchronized void update(int address, Pair<Integer, List<Integer>> pair) {
        if (barrierTableMap.containsKey(address)) {
            barrierTableMap.put(address, pair);
        }
        else throw new AddressNotInBarrierTable("Address not in Barrier Table");
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        return barrierTableMap;
    }

    @Override
    public boolean isDefined(int address) {
        if (barrierTableMap.containsKey(address)) {
            return true;
        }
        return false;
    }

    @Override
    public void setContent(Map<Integer, Pair<Integer, List<Integer>>> heapMap) {
        barrierTableMap.clear();
        barrierTableMap.putAll(heapMap);
    }
}
