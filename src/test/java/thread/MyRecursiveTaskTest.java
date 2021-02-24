package thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.Util;

class MyRecursiveTaskTest {
    private static final int SIZE = 1000000;
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private static List<Integer> firstList;
    private static List<Integer> secondList;
    private static List<Integer> thirdList;

    @BeforeAll
    static void beforeAll() {
        firstList = new ArrayList<>();
        Util.fillList(firstList, SIZE);
        secondList = new ArrayList<>();
        Util.fillList(secondList, SIZE);
        thirdList = new ArrayList<>();
        Util.fillList(thirdList, SIZE);
    }

    @Test
    void sum_Ok() {
        RecursiveTask<Integer> myRecursiveTask = new MyRecursiveTask(firstList);
        Integer sum = forkJoinPool.invoke(myRecursiveTask);
        Assertions.assertEquals(getSum(firstList), sum);

        myRecursiveTask = new MyRecursiveTask(secondList);
        sum = forkJoinPool.invoke(myRecursiveTask);
        Assertions.assertEquals(getSum(secondList), sum);

        myRecursiveTask = new MyRecursiveTask(thirdList);
        sum = forkJoinPool.invoke(myRecursiveTask);
        Assertions.assertEquals(getSum(thirdList), sum);

        myRecursiveTask = new MyRecursiveTask(Collections.emptyList());
        sum = forkJoinPool.invoke(myRecursiveTask);
        Assertions.assertEquals(0, sum);
    }

    private int getSum(List<Integer> list) {
        return list.stream().reduce(Integer::sum).orElse(0);
    }

    @AfterAll
    static void afterAll() {
        forkJoinPool.shutdown();
    }
}
