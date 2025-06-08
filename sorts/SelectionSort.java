package sorts;

import java.util.Arrays;
import java.util.Comparator;

import utility.GenerateRandom;

public class SelectionSort {

    /**
     * Namesake sorting method implementation: SelectionSort
     * 
     * @param <T>        A generic Object wrapper type (NOT PRIMITIVE!)
     * @param arr        An array of type:T
     * @param comparator A comparator of type:T which implements compare()
     */
    // NOTE: Sorted subarray grows from top-to-tail (i.e. the first element is put
    // in its correct place first, then second, etc.)
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        // for each element in the array (except last element it would already be
        // sorted)
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIdx = i;
            // check against all other elements in sorted-sub array
            for (int j = i + 1; j < arr.length; j++) {
                // check if the value at arr[j] is less than the current mininum value
                if (comparator.compare(arr[j], arr[minElementIdx]) < 0) {
                    // if so, set the minElementIdx to the current element's index
                    minElementIdx = j;
                }
            }
            // if the minElementIdx != i, then swap
            if (minElementIdx != i) {
                T temp = arr[minElementIdx];
                arr[minElementIdx] = arr[i];
                arr[i] = temp;
            }
        }
    }

    public static void main(String[] args) {
        // initialize comparators and random number arrays used for example
        Comparator<Integer> intComparator = (x, y) -> Integer.compare(x, y);
        Comparator<Double> doubleComparator = (x, y) -> Double.compare(x, y);
        Integer[] intArray = GenerateRandom.intArray(0, 10, 10);
        Double[] doubleArray = GenerateRandom.doubleArray(0.0, 10.50, 10);

        System.out.print("Unsorted intArray: [ ");
        Arrays.stream(intArray).forEach((i) -> System.out.print(i + " "));
        System.out.print("]\n\n");

        System.out.print("Sorted intArray: [ ");
        sort(intArray, intComparator);
        Arrays.stream(intArray).forEach((i) -> System.out.print(i + " "));
        System.out.print("]\n\n");

        System.out.print("Unsorted doubleArray: [ ");
        Arrays.stream(doubleArray).forEach((i) -> System.out.print(String.format("%.4f", i) + " "));
        System.out.print("]\n\n");

        System.out.print("Sorted doubleArray: [ ");
        sort(doubleArray, doubleComparator);
        Arrays.stream(doubleArray).forEach((i) -> System.out.print(String.format("%.4f", i) + " "));
        System.out.print("]\n\n");
    }

}
