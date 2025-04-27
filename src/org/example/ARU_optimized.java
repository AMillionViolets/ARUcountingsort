package org.example;
import java.util.Arrays;
import java.util.Collections;
public class ARU_optimized {
    public static int[] ARU_optimized(int[] inputArray) {
        int arrayLength = inputArray.length;
        if (arrayLength == 0) {
            return inputArray;
        }

        int maxValue = Collections.max(Arrays.stream(inputArray).boxed().toList());
        int sqrtMax = (int) Math.ceil(Math.sqrt((double) maxValue));
        int[] countArray = new int[sqrtMax + 2];  // Shared counting array
        int[] tempArray = new int[arrayLength];

        // First pass: sort by remainders
        Arrays.fill(countArray, 0);
        for (int num : inputArray) {
            countArray[(int) (num % sqrtMax)]++;
        }
        for (int i = 1; i <= sqrtMax; i++) {
            countArray[i] += countArray[i - 1];
        }
        for (int i = arrayLength - 1; i >= 0; i--) {
            int remainder = (int) (inputArray[i] % sqrtMax);
            tempArray[--countArray[remainder]] = inputArray[i];
        }

        // Second pass: sort by quotients
        Arrays.fill(countArray, 0);
        for (int num : tempArray) {
            countArray[(int) (num / sqrtMax)]++;
        }
        for (int i = 1; i <= sqrtMax; i++) {
            countArray[i] += countArray[i - 1];
        }
        for (int i = arrayLength - 1; i >= 0; i--) {
            int quotient = (int) (tempArray[i] / sqrtMax);
            inputArray[--countArray[quotient]] = tempArray[i];
        }

        return inputArray;
    }
}