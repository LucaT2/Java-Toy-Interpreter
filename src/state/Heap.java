package state;

import model.types.Type;

public interface Heap {
    void add(int address, int value);
    Type get(int address);
    void remove(int address);

}
