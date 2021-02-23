package thread;

import org.apache.log4j.Logger;
import util.Counter;

public class MyThread extends Thread {
    private static final Logger logger = Logger.getLogger(Counter.class);
    private final Counter counter;
    public static final int END = 100;

    public MyThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        int value;
        while ((value = counter.increment()) < END) {
            logger.info(Thread.currentThread().getName() + " value = " + value);
        }
    }
}
