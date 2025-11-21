package state;

import model.types.Type;
import model.value.Value;
import state.exceptions.AddressAlreadyUsed;
import state.exceptions.AddressNotInHeap;

import java.util.HashMap;
import java.util.Map;

public class HeapMap implements Heap{
    private final Map<Integer, Value>  heapMap = new HashMap<>();
    private static int currentAddress = 1;

    private synchronized int getNewAddress(){
        return currentAddress++;
    }
    @Override
    public int add(Value value) {
        int addr = getNewAddress();
        heapMap.put(addr, value);
        return addr;
    }

    @Override
    public Value lookUp(int address) {
        if (heapMap.containsKey(address)) {
            return heapMap.get(address).getValue();
        }
        throw new AddressNotInHeap("Address not in heap");

    }

    @Override
    public void remove(int address) {
        if (heapMap.containsKey(address)) {
            heapMap.remove(address);
            return;
        }
        else {
            throw new AddressNotInHeap("Address not in heap");
        }
    }

    @Override
    public void update(int address, Value value) {
        if (heapMap.containsKey(address)) {
            heapMap.put(address, value);
        }
        else{
            throw new AddressNotInHeap("Address not in heap");
        }
    }
    @Override
    public Map<Integer, Value> getHeapMap() {
        return heapMap;
    }

    @Override
    public void setContent(Map<Integer, Value> heapMap) {
        this.heapMap.clear();
        this.heapMap.putAll(heapMap);
    }
}
