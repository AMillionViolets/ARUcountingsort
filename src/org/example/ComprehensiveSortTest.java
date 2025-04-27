package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class ComprehensiveSortTest {
    // Test configurations
    private static final int[] ARRAY_SIZES = {10, 100, 1_000, 10_000, 100_000, 100_000_0,100_000_00};
    private static final int[] MAX_VALUES = {10,100, 1_000, 10_000, 100_000, 100_000_0,100_000_00};
    private static final int WARMUP_ROUNDS = 2;
    private static final int BENCHMARK_ROUNDS = 50;
    private static final boolean VERIFY_SORTING = true;
    private static final String CSV_FILE = "sort_performance_Countingsort" +
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv";

    public static void main(String[] args) {
        List<TestResult> results = new ArrayList<>();
        PrintWriter csvWriter = null;

        try {
            // Initialize CSV writer
            csvWriter = new PrintWriter(new FileWriter(CSV_FILE));
            writeCsvHeader(csvWriter);

            System.out.println("Running comprehensive sort tests...");
            System.out.println("Results will be saved to: " + CSV_FILE);

            // Test all permutations
            for (int size : ARRAY_SIZES) {
                for (int maxValue : MAX_VALUES) {
                    if (shouldSkipTest(size, maxValue)) continue;

                    TestResult result = runTestConfiguration(size, maxValue);
                    results.add(result);

                    // Output to console and CSV
                    printTestResult(result);
                    writeCsvResult(csvWriter, result);
                }
            }

            // Print summary
            System.out.println("\nTesting complete. Results saved to: " + CSV_FILE);

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
    }

    private static void writeCsvHeader(PrintWriter writer) {
        writer.println("Array Size,Max Value,Average Time (ms),Min Time (ms)," +
                "Max Time (ms),Standard Deviation,Sort Correct");
    }

    private static void writeCsvResult(PrintWriter writer, TestResult result) {
        writer.printf("%d,%d,%.2f,%d,%d,%.2f,%b%n",
                result.size,
                result.maxValue,
                result.averageTime,
                result.minTime,
                result.maxTime,
                result.stdDev,
                result.allCorrect);
    }



    private static boolean shouldSkipTest(int size, int maxValue) {
        // Skip tests where maxValue is smaller than array size to ensure variety
        return maxValue < size/10 && maxValue != Integer.MAX_VALUE;
    }

    private static double calculateStdDev(long[] times, double mean) {
        double variance = Arrays.stream(times)
                .mapToDouble(t -> Math.pow(t - mean, 2))
                .average().orElse(0);
        return Math.sqrt(variance);
    }

    private static TestResult runTestConfiguration(int size, int maxValue) {
        // Warmup phase
        for (int i = 0; i < WARMUP_ROUNDS; i++) {
            int[] warmupArray = generateRandomArray(size, maxValue);
            mySort(warmupArray);
        }

        // Benchmark phase
        long[] times = new long[BENCHMARK_ROUNDS];
        boolean allCorrect = true;

        for (int i = 0; i < BENCHMARK_ROUNDS; i++) {
            int[] array = generateRandomArray(size, maxValue);
            long startTime = System.nanoTime();
            mySort(array);
            long endTime = System.nanoTime();
            times[i] = (endTime - startTime) / 1_000_000;

            if (VERIFY_SORTING && !isSorted(array)) {
                allCorrect = false;
            }
        }

        return new TestResult(size, maxValue, times, allCorrect);
    }

    private static int[] generateRandomArray(int length, int maxValue) {
        Random random = new Random();
        int[] array = new int[length];

        // Fill with random values (0 to maxValue)
        for (int i = 0; i < length - 1; i++) {
            array[i] = maxValue == Integer.MAX_VALUE ?
                    random.nextInt() : random.nextInt(maxValue);
        }

        // Ensure at least one max value exists
        array[length - 1] = maxValue == Integer.MAX_VALUE ?
                Integer.MAX_VALUE : maxValue;

        // Shuffle the array
        for (int i = length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        return array;
    }

    private static void printTestResult(TestResult result) {
        System.out.printf("Size: %-7d | Max: %-11d | Avg: %-7.2f ms | Min: %-5d ms | Max: %-5d ms | %s%n",
                result.size,
                result.maxValue,
                result.averageTime,
                result.minTime,
                result.maxTime,
                result.allCorrect ? "✓" : "✗ (Sort Failed)");
    }




    private static void mySort(int[] array) {
        ARU_optimized.ARU_optimized(array);
    }

    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    static class TestResult {
        int size;
        int maxValue;
        double averageTime;
        long minTime;
        long maxTime;
        double stdDev;
        boolean allCorrect;

        TestResult(int size, int maxValue, long[] times, boolean allCorrect) {
            this.size = size;
            this.maxValue = maxValue;
            this.stdDev = calculateStdDev(times, averageTime);
            this.averageTime = Arrays.stream(times).average().orElse(0);
            this.minTime = Arrays.stream(times).min().orElse(0);
            this.maxTime = Arrays.stream(times).max().orElse(0);
            this.allCorrect = allCorrect;
        }
    }
}