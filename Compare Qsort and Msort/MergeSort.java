package VTULabExperiments;

/*
*   @author Swarit Pandey
*   IntelliJ IDEA Ultimate
*   Lab Exp 08:
*   Task:   Sort a given set of n integer elements using Merge Sort method and compute its time
            complexity. Run the program for varied values of n> 5000, and record the time taken to
            sort. Plot a graph of the time taken versus n on graph sheet. The elements can be read from a
            file or can be generated using the random number generator. Demonstrate using Java how
            the divide-and-conquer method works along with its time complexity analysis: worst case,
            average case and best case.

    Note: Stress Tested
* */

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class MergeSort {
    Duration initSort(int[] A) {
        Instant start = Instant.now();
        int step = 1;
        int left, right;
        while(step < A.length) {
            left = 0;
            right = step;
            while(right + step <= A.length) {
                merge(A, left, left + step, right, right + step);
                left = right + step;
                right = left + step;
            }
            if(right < A.length) {
                merge(A, left, left + step, right, A.length);
            }
            step *= 2;
        }
        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    private void merge(int[] A, int startL, int stopL, int startR, int stopR) {
        int[] right = new int[stopR - startR + 1];
        int[] left = new int[stopL - startL + 1];

        for(int i = 0, k = startR; i < (right.length - 1); ++i, ++k) {
            right[i] = A[k];
        }

        for(int i = 0, k = startL; i < (left.length - 1); ++i, ++k) {
            left[i] = A[k];
        }

        right[right.length - 1] = Integer.MAX_VALUE;
        left[left.length - 1] = Integer.MAX_VALUE;

        for(int k = startL, m = 0, n = 0; k < stopR; ++k) {
            if(left[m] <= right[n]) {
                A[k] = left[m];
                m++;
            } else {
                A[k] = right[n];
                n++;
            }
        }
    }
}

public class DAALabExp08 {
    public static void main(String[] args) throws Exception {
        Scanner scanData = new Scanner(System.in);
        System.out.println("To run automated stress test press 'Y'\nTo run manual test case press 'N'\nRun controlled tests");
        String response = scanData.next();

        switch (response) {
            case "Y" -> initStressTest();
            case "N" -> initManualTest();
            case "C" -> initControlTest();
            default -> {
                System.out.println("Invalid, try again!");
                System.exit(0);
            }
        }
    }

    private static void initControlTest() throws FileNotFoundException {
        int [] files = new int[] {5000, 10999, 56099, 89898, 99989, 999999, 10000999};
        Instant start = Instant.now();
        for(int fileName : files) {
            int count = 0;
            int x = 0;
            while(x < 5) {
                File file = new File("sortingTestCases/" + fileName + "/" + fileName + "_" + x + ".txt");
                Scanner scanData = new Scanner(file);
                int index = 0;
                int[] array = new int[fileName];
                while (scanData.hasNextInt()) {
                    array[index] = Integer.parseInt(scanData.next());
                    index++;
                }
                MergeSort sort = new MergeSort();
                Duration timeTaken = sort.initSort(array);
                if(check(array)) {
                    System.out.println("Test case #" + count + "\nVerdict: Pass \nTime Taken: " + timeTaken +"\nSize: " + fileName);
                } else {
                    System.out.println("Test case #" + count + "\nVerdict: Fail");
                }
                count++;
                x++;
            }
            System.out.println();
        }
        Instant finish = Instant.now();
        Duration finalTime = Duration.between(start, finish);
        System.out.println("All controlled tests passed, overall time taken by Merge Sort is: " + finalTime);
    }

    private static void initManualTest() throws Exception {
        int[] array;
        Scanner scanData = new Scanner(System.in);
        System.out.println("Enter the size of the array: ");
        int size = scanData.nextInt();
        array = new int[size];
        generate(array);
        MergeSort sort = new MergeSort();
        Duration timeTaken = sort.initSort(array);
        System.out.println("Time taken to get the ordered set: " + timeTaken);
        if(check(array)) System.out.println("Sorted!");
        else throw new Exception("Sorting failed.");

        System.out.println("Do you want to see the array: (Y/N)");
        String response = scanData.next();
        if(response.equals("Y")) {
            System.out.println(Arrays.toString(array));
        } else {
            System.exit(0);
        }

    }

    private static void initStressTest() {
        int[] test_size;
        int[] array;
        int count = 1;
        test_size = new int[] {5000, 10999, 99989, 100020, 99987, 56099, 1000099, 10000999, 89898, 999999, 100000000, 99009900, 99889987};
        Arrays.sort(test_size);

        Instant start = Instant.now();
        for(int sizes : test_size) {
            array = new int[sizes];
            generate(array);
            MergeSort sort = new MergeSort();
            Duration time_taken = sort.initSort(array);
            if(check(array)) {
                System.out.println("Test case #" + count + "\nVerdict: Pass \nTime Taken: " + time_taken +"\nSize: " + sizes);
            } else {
                System.out.println("Test case #" + count + "\nVerdict: Fail");
            }
            count++;
            System.out.println("\n");
        }

        Instant end = Instant.now();
        Duration timeTaken = Duration.between(start, end);
        System.out.println("Time take to finish all the test cases: " + timeTaken);
    }

    private static void generate(int[] array) {
        Random rand = new Random();
        for(int i = 0; i < array.length; ++i) {
            array[i] = rand.nextInt();
        }
    }

    private static boolean check(int[] array) {
        for(int i = 0; i < array.length - 1; ++i) {
            if(array[i] > array[i + 1]) {
                System.out.println(array[i] + " " + i);
                return false;
            }
        }
        return true;
    }
}
