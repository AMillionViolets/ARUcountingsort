//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.Arrays;
import java.util.Collections;

public class ARU {
    public static int[] ARU(int[] inputArray) {
        int arrayLength = inputArray.length;
        if (arrayLength == 0) {
            return inputArray;
        }

        int maxValue = Collections.max(Arrays.stream(inputArray).boxed().toList());
        int sqrtMax = (int)Math.ceil(Math.sqrt((double) maxValue));
        int[] quotientCounts = new int[(int) sqrtMax + 2];  // Count frequencies of quotients
        int[] remainderCounts = new int[(int) sqrtMax + 2]; // Count frequencies of remainders
        int[] tempArray = new int[arrayLength];

        System.out.println("Calculating quotient and remainder counts");

        // Count frequencies of quotients and remainders
        for (int i = 0; i < arrayLength; i++) {
            int quotient = (int) (inputArray[i] / sqrtMax);
            int remainder = (int) (inputArray[i] % sqrtMax);
            quotientCounts[quotient]++;
            remainderCounts[remainder]++;
        }

        // Compute prefix sums (cumulative counts)
        for (int i = 1; i <= sqrtMax; i++) {
            quotientCounts[i] += quotientCounts[i - 1];
            remainderCounts[i] += remainderCounts[i - 1];
        }

        // Distribute elements into tempArray based on remainders
        for (int i = arrayLength - 1; i >= 0; i--) {
            int remainder = (int) (inputArray[i] % sqrtMax);
            tempArray[--remainderCounts[remainder]] = inputArray[i];

        }

        // Distribute elements back into inputArray based on quotients
        for (int i = arrayLength - 1; i >= 0; i--) {
            int quotient = (int) (tempArray[i] / sqrtMax);
            inputArray[--quotientCounts[quotient]] = tempArray[i];
        }

        return inputArray;
    }
}
