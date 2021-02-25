package thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private static ExecutorServiceCalculator executorServiceCalculator;

    @BeforeAll
    static void beforeAll() {
        firstList = new ArrayList<>();
        Util.fillList(firstList, SIZE);
        secondList = new ArrayList<>();
        Util.fillList(secondList, SIZE);
        thirdList = new ArrayList<>();
        Util.fillList(thirdList, SIZE);

        executorServiceCalculator = new ExecutorServiceCalculator();
    }

    @Test
    void sum_Ok() {
        Integer sum = executorServiceCalculator.calculate(firstList);
        Assertions.assertEquals(getSum(firstList), sum);
        sum = executorServiceCalculator.calculate(secondList);
        Assertions.assertEquals(getSum(secondList), sum);
        sum = executorServiceCalculator.calculate(thirdList);
        Assertions.assertEquals(getSum(thirdList), sum);
        sum = executorServiceCalculator.calculate(Collections.emptyList());
        Assertions.assertEquals(0, sum);
    }

    private int getSum(List<Integer> list) {
        return list.stream().reduce(Integer::sum).orElse(0);
    }

    @AfterAll
    static void afterAll() {
        executorServiceCalculator.shutdown();
    }
}
