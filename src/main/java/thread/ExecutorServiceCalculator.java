package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceCalculator {
    private static final int THRESHOLD = 100000;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public Integer calculate(List<Integer> list) {
        if (list.size() > THRESHOLD) {
            try {
                return executorService.invokeAll(createSubTasks(list)).stream()
                        .map(this::getValue)
                        .reduce(Integer::sum)
                        .orElse(0);
            } catch (InterruptedException e) {
                throw new RuntimeException("Computation was canceled", e);
            }
        }
        return getValue(executorService.submit(new MyCallable(list)));
    }

    private List<Callable<Integer>> createSubTasks(List<Integer> list) {
        List<Callable<Integer>> tasks = new ArrayList<>();
        Callable<Integer> firstTask = new MyCallable(list.subList(0, list.size() / 2));
        Callable<Integer> secondTask
                = new MyCallable(list.subList(list.size() / 2, list.size()));
        tasks.add(firstTask);
        tasks.add(secondTask);
        return tasks;
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

    public void shutdown() {
        executorService.shutdown();
    }
}
