package org.example;
import java.util.Random;

public class RandomArrayGenerator {
    public static int[] generateRandomArray(int length, int min, int max) {
        if (length <= 0) {
            throw new IllegalArgumentException("Array length must be positive");
        }
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }

        Random random = new Random();
        int[] array = new int[length];

        for (int i = 0; i < length; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }
        for (int i = 0; i < length; i++) {
            if (array[i] == max) {
                break;
            }
            if (i==length) {
                array[i- random.nextInt(10)] = max;
            }

        }

        return array;
    }
}