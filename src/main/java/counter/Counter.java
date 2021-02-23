package counter;

import org.apache.log4j.Logger;

public class Counter {
    public static final int START = 0;
    public static final int END = 100;
    private static final Logger logger = Logger.getLogger(Counter.class);
    private int value;

    public Counter(int value) {
        this.value = value;
    }

    public void iterate() {
        for (value = START; value < END;) {
            logger.info(Thread.currentThread().getName() + " value = " + value++);
        }
    }
}
