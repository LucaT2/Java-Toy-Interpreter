package view;

import java.util.List;

public class BarrierTableRow {
    private final int index;
    private final int value;
    private final List<Integer> list;

    public BarrierTableRow(int index, int value, List<Integer> list) {
        this.index = index;
        this.value = value;
        this.list = list;
    }

    public int getIndex() { return index; }
    public int getValue() { return value; }
    public List<Integer> getList() { return list; }
}
