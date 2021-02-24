package util;

import java.util.List;
import java.util.Random;

public class Util {
    public static void fillList(List<Integer> list, int size) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(1000));
        }
    }
}
