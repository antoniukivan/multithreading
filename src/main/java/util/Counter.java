package util;

public class Counter {
    private int value;

    public Counter(int value) {
        this.value = value;
    }

    public int increment() {
        return ++value;
    }
}
