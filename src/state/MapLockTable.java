package state;

import model.value.Value;
import state.exceptions.AddressNotInLockTable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MapLockTable implements LockTable {
    private Map<Integer, Integer> lockMap = new HashMap<Integer, Integer>();
    private final AtomicInteger currentAddress = new AtomicInteger(1);
    @Override
    public synchronized int add(Integer value) {
        int address = currentAddress.getAndIncrement();
        lockMap.put(address, value);
        return address;
    }

    @Override
    public synchronized Integer lookUp(int address) {
        if  (lockMap.containsKey(address)) {
            return lockMap.get(address);
        }
        else{
            throw new AddressNotInLockTable("Address " + address + " not in lock map");
        }
    }

    @Override
    public synchronized void remove(int address) {
        if  (lockMap.containsKey(address)) {
            lockMap.remove(address);
        }
        else{
            throw new AddressNotInLockTable("Address " + address + " not in lock map");
        }
    }

    @Override
    public synchronized void update(int address, Integer value) {
        if  (lockMap.containsKey(address)) {
            lockMap.put(address, value);
        }
        else{
            throw new AddressNotInLockTable("Address " + address + " not in lock map");
        }
    }

    @Override
    public synchronized Map<Integer, Integer> getLockTable() {
        return lockMap;
    }

    @Override
    public synchronized boolean isDefined(int address) {
        if (lockMap.containsKey(address)) {
            return true;
        }
        else return false;
    }

    @Override
    public synchronized void setContent(Map<Integer, Integer> newLockMap) {
        lockMap.clear();
        lockMap.putAll(newLockMap);
    }
}
