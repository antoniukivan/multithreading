import thread.MyRunnable;
import thread.MyThread;
import util.Counter;

public class Main {
    private static final int INITIAL_COUNTER = 0;

    public static void main(String[] args) {
        Counter counter = new Counter(INITIAL_COUNTER);
        Thread myThread = new MyThread(counter);
        Runnable myRunnable = new MyRunnable(counter);
        myThread.start();
        new Thread(myRunnable).start();
    }
}
