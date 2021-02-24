package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyCallable implements Callable<Integer> {
    private static final int THRESHOLD = 100000;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private final List<Integer> list;

    public MyCallable(List<Integer> list) {
        this.list = list;
    }

    @Override
    public Integer call() throws Exception {
        if (list.size() > THRESHOLD) {
            return executorService.invokeAll(createSubTasks()).stream()
                    .map(this::getValue)
                    .reduce(Integer::sum)
                    .orElse(0);
        }
        return getSum();
    }

    private List<Callable<Integer>> createSubTasks() {
        List<Callable<Integer>> tasks = new ArrayList<>();
        Callable<Integer> firstCallable = new MyCallable(list.subList(0, list.size() / 2));
        Callable<Integer> secondCallable
                = new MyCallable(list.subList(list.size() / 2, list.size()));
        tasks.add(firstCallable);
        tasks.add(secondCallable);
        return tasks;
    }

    private Integer getSum() {
        return list.stream().reduce(Integer::sum).orElse(0);
    }

    private Integer getValue(Future<Integer> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException("Computation was canceled", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Computation threw an exception", e);
        }
    }
}
