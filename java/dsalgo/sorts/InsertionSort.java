package dsalgo.sorts;

import dsalgo.utility.GenerateRandom;
import java.util.Arrays;
import java.util.Comparator;

public class InsertionSort {

    /**
     * Namesake sorting method implementation: InsertionSort
     * 
     * @param <T>        A generic Object wrapper type (NOT PRIMITIVE!)
     * @param arr        An array of type:T
     * @param comparator A comparator of type:T which implements compare()
     */
    // NOTE: Sorted subarray grows from tail-to-top (i.e. the last element is put in
    // correct position first, then second to last, etc.)
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        // for each element in the array (except first element it would already be
        // CONSIDERED sorted)
        for (int i = 1; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1; // the index of the element(s) to check

            // check against all preceeding elements in sorted-subarray
            // stop if you reach the beginning of the array OR the temp value is larger than
            // any preceeding elements
            while (j >= 0 && (comparator.compare(arr[j], temp) > 0)) {
                // move element at arr[j] ONE position to the right
                arr[j + 1] = arr[j];
                j--;
            }

            // Now that all the elements greater than temp are to the right, place temp in
            // its correct spot.
            arr[j + 1] = temp;
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
