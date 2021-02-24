package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 100000;
    private final List<Integer> list;

    public MyRecursiveTask(List<Integer> list) {
        this.list = list;
    }

    @Override
    protected Integer compute() {
        if (list.size() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks()).stream()
                    .map(ForkJoinTask::join)
                    .reduce(Integer::sum)
                    .orElse(0);
        }
        return getSum();
    }

    private List<RecursiveTask<Integer>> createSubTasks() {
        List<RecursiveTask<Integer>> tasks = new ArrayList<>();
        RecursiveTask<Integer> firstTask = new MyRecursiveTask(list.subList(0, list.size() / 2));
        RecursiveTask<Integer> secondTask
                = new MyRecursiveTask(list.subList(list.size() / 2, list.size()));
        tasks.add(firstTask);
        tasks.add(secondTask);
        return tasks;
    }

    private Integer getSum() {
        return list.stream().reduce(Integer::sum).orElse(0);
    }
}
