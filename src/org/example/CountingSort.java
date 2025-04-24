package org.example;
import java.util.Arrays;

public class CountingSort {

    /**
     * Sorts an array of non-negative integers using the Counting Sort algorithm.
     *
     * @param dataToSort The array of integers to be sorted.
     * @return A new array containing the sorted elements.
     */
    public static int[] performCountSort(int[] dataToSort) {

        // Handle empty or null input gracefully
        if (dataToSort == null || dataToSort.length == 0) {
            return new int[0];
        }

        int elementCount = dataToSort.length;
        int maxValue = 0;

        // Step 1: Find the maximum value in the input array to determine the range
        for (int idx = 0; idx < elementCount; idx++) {
            // Ensure non-negative values as counting sort typically assumes this
            if (dataToSort[idx] < 0) {
                throw new IllegalArgumentException("Counting sort implemented here does not support negative numbers.");
            }
            maxValue = Math.max(maxValue, dataToSort[idx]);
        }

        // Step 2: Create the frequency array (or count array)
        // Size needs to be maxValue + 1 to accommodate indices from 0 to maxValue
        int[] frequencyCounts = new int[maxValue + 1];

        // Step 3: Populate the frequency array with counts of each element
        for (int idx = 0; idx < elementCount; idx++) {
            frequencyCounts[dataToSort[idx]]++;
        }

        // Step 4: Modify the frequency array to store cumulative counts
        // Each index will now store the count of elements less than or equal to the
        // index value
        for (int idx = 1; idx <= maxValue; idx++) {
            frequencyCounts[idx] += frequencyCounts[idx - 1];
        }

        // Step 5: Create the output array (sorted array)
        int[] sortedData = new int[elementCount];

        // Step 6: Build the output array using the cumulative counts
        // Iterate backwards through the input array to maintain stability (important
        // for objects)
        for (int idx = elementCount - 1; idx >= 0; idx--) {
            int currentElement = dataToSort[idx];
            int position = frequencyCounts[currentElement] - 1; // Get the correct 0-based index
            sortedData[position] = currentElement;
            frequencyCounts[currentElement]--; // Decrement count for the next identical element
        }

        return sortedData;

    }

    public static void main(String[] args) {
        RandomArrayGenerator generator = new RandomArrayGenerator();

        int[] unsortedNumbers = generator.generateRandomArray(10,1,100);


        int[] sortedNumbers = performCountSort(unsortedNumbers);

        System.out.print("Sorted array:   ");
        for (int idx = 0; idx < sortedNumbers.length; idx++) {
            System.out.print(sortedNumbers[idx] + " ");
        }
        System.out.println(); // New line at the end

        // Example with empty array
        int[] emptyArray = {};
        System.out.println("Original array: " + Arrays.toString(emptyArray));
        int[] sortedEmpty = performCountSort(emptyArray);
        System.out.println("Sorted array:   " + Arrays.toString(sortedEmpty));

        // Example with single element
        int[] singleElementArray = { 7 };
        System.out.println("Original array: " + Arrays.toString(singleElementArray));
        int[] sortedSingle = performCountSort(singleElementArray);
        System.out.println("Sorted array:   " + Arrays.toString(sortedSingle));

    }

}