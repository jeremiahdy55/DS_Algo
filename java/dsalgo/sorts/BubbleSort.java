package dsalgo.sorts;

import dsalgo.utility.GenerateRandom;
import java.util.Arrays;
import java.util.Comparator;

public class BubbleSort {

    /**
     * Namesake sorting method implementation: BubbleSort
     * 
     * @param <T>        A generic Object wrapper type (NOT PRIMITIVE!)
     * @param arr        An array of type:T
     * @param comparator A comparator of type:T which implements compare()
     */
    // NOTE: Sorted subarray grows from tail-to-top (i.e. the last element is put in
    // correct position first, then second to last, etc.)
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        // for each element in the array (except last element it would already be
        // sorted)
        for (int i = 0; i < arr.length - 1; i++) {
            // check against all other elements in sorted-sub array
            for (int j = 0; j < arr.length - i - 1; j++) {
                // swap if the value is greater
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    // Swap elements
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
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
