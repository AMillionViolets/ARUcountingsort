package org.example;

import java.util.Random;

public class SortTimer {
    /**
     * Times the execution of a sorting function on a given array
     * @param sortFunction The sorting function to time (should modify the array in-place)
     * @param array The array to be sorted (will be copied to preserve original)
     * @return Execution time in milliseconds
     */
    public static long timeSort(Runnable sortFunction, int[] array) {
        // Create a copy of the array to preserve the original
        int[] arrayCopy = array.clone();

        long startTime = System.nanoTime();
        sortFunction.run();  // Execute the sort function
        long endTime = System.nanoTime();

        // Convert nanoseconds to milliseconds
        return (endTime - startTime) / 1_000_000;
    }

}