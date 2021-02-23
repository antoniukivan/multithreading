package thread;

import util.Counter;

public class MyThread extends Thread {
    private final Counter counter;

    public MyThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.iterate();
    }
}
