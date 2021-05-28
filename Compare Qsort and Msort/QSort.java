package VTULabExperiments;

/*
    @author Swarit Pandey
    IntelliJ IDEA Ultimate
    Lab Exp 07:
    Task:   Sort a given set of n integer elements using Quick Sort method and compute its time
            complexity. Run the program for varied values of n > 5000 and record the time taken to sort.
            Plot a graph of the time taken versus n on graph sheet. The elements can be read from a file
            or can be generated using the random number generator. Demonstrate using Java how the
            divide-and-conquer method works along with its time complexity analysis: worst case,
            average case and best case.

    Note: Stress Tested
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


class QuickSort {
    Duration initSort (int[] array, int l, int h) {
        Instant start = Instant.now();
        if(l < h) {
            int j = partition(array, l, h);
            initSort(array, l, j);
            initSort(array, j + 1, h);
        }
        Instant end = Instant.now();
        return Duration.between(start, end);
    }

    private int partition(int[] array, int l, int h) {
        int pivot = array[l];
        int i = l;
        int j = h;

        while(i < j) {
            do {
                i++;
            } while(pivot >= array[i]);

            do {
                j--;
            } while(pivot < array[j]);

            if(i < j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[l];
        array[l] = array[j];
        array[j] = temp;

        return j;
    }
}

public class DAALabExp07 {
    public static void main(String[] args) throws Exception {
        Scanner scanData = new Scanner(System.in);
        System.out.println("To run automated stress test press 'Y'\nTo run manual test case press 'N' \nRun control test press 'C'");
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
    // "sortingTestCases/" + fileName + "/" + fileName + "_" + x + ".txt"
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
                int refFileName = fileName + 1;
                int[] array = new int[refFileName];
                while (scanData.hasNextInt()) {
                    array[index] = Integer.parseInt(scanData.next());
                    index++;
                }
                array[refFileName - 1] = Integer.MAX_VALUE;
                QuickSort sort = new QuickSort();
                Duration timeTaken = sort.initSort(array, 0, array.length - 1);
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
        System.out.println("All controlled tests passed, overall time taken by QuickSort is: " + finalTime);
    }

    private static void initManualTest() throws Exception {
        int[] array;
        Scanner scanData = new Scanner(System.in);
        System.out.println("Enter the size of the array: ");
        int size = scanData.nextInt();
        size = size + 1;
        array = new int[size];
        generate(array);
        array[size - 1] = Integer.MAX_VALUE;
        QuickSort sort = new QuickSort();
        Duration timeTaken = sort.initSort(array, 0, array.length - 1);
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
            sizes = sizes + 1;
            array = new int[sizes];
            generate(array);
            array[sizes - 1] = Integer.MAX_VALUE;
            QuickSort sort = new QuickSort();
            Duration time_taken = sort.initSort(array, 0, array.length - 1);
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
        for(int i = 0; i < array.length - 2; ++i) {
            if(array[i] > array[i + 1]) {
                System.out.println(array[i] + " " + i);
                return false;
            }
        }
        return true;
    }
}

