import thread.MyRunnable;
import thread.MyThread;
import util.Counter;

public class Main {
    private static final int VALUE = 0;

    public static void main(String[] args) {
        Counter counter = new Counter(VALUE);
        MyThread myThread = new MyThread(counter);
        MyRunnable myRunnable = new MyRunnable(counter);
        myThread.start();
        new Thread(myRunnable).start();
    }
}
