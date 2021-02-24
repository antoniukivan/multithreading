package thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.Util;

public class MyCallableTest {
    private static final int SIZE = 1000000;
    private static List<Integer> firstList;
    private static List<Integer> secondList;
    private static List<Integer> thirdList;
    private static ExecutorService executorService;

    @BeforeAll
    static void beforeAll() {
        firstList = new ArrayList<>();
        Util.fillList(firstList, SIZE);
        secondList = new ArrayList<>();
        Util.fillList(secondList, SIZE);
        thirdList = new ArrayList<>();
        Util.fillList(thirdList, SIZE);

        executorService = Executors.newSingleThreadExecutor();
    }

    @Test
    void sum_Ok() {
        Callable<Integer> myCallable = new MyCallable(firstList);
        Future<Integer> firstFuture = executorService.submit(myCallable);
        myCallable = new MyCallable(secondList);
        Future<Integer> secondFuture = executorService.submit(myCallable);
        myCallable = new MyCallable(thirdList);
        Future<Integer> thirdFuture = executorService.submit(myCallable);
        myCallable = new MyCallable(Collections.emptyList());
        Future<Integer> fourthFuture = executorService.submit(myCallable);
        try {
            Assertions.assertEquals(getSum(firstList), firstFuture.get());
            Assertions.assertEquals(getSum(secondList), secondFuture.get());
            Assertions.assertEquals(getSum(thirdList), thirdFuture.get());
            Assertions.assertEquals(0, fourthFuture.get());
        } catch (InterruptedException e) {
            throw new RuntimeException("Computation was canceled", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Computation threw an exception", e);
        }
    }

    private int getSum(List<Integer> list) {
        return list.stream().reduce(Integer::sum).orElse(0);
    }

    @AfterAll
    static void afterAll() {
        executorService.shutdown();
    }
}
