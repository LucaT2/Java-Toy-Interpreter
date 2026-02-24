package view;

import java.util.List;

public class SemaphoreRow {
    private final int index;
    private final int value;
    private final String threads;

    public SemaphoreRow(int index, int value, List<Integer> threads) {
        this.index = index;
        this.value = value;
        this.threads = threads.toString();
    }

    public int getIndex() { return index; }
    public int getValue() { return value; }
    public String getThreads() { return threads; }
}