package dsalgo.sorts;

import dsalgo.utility.GenerateRandom;
import java.util.Arrays;
import java.util.Comparator;

public class ShellSort {

    /**
     * Namesake sorting method implementation: ShellSort
     * 
     * @param <T>        A generic Object wrapper type (NOT PRIMITIVE!)
     * @param arr        An array of type:T
     * @param comparator A comparator of type:T which implements compare()
     */
    // NOTE: Sorted subarray grows from tail-to-top (i.e. the last element is put in
    // correct position first, then second to last, etc.)
    public static <T> void sort(T[] arr, Comparator<T> comparator) {
        // Initialize the gap sequence used to determine the gapped elements on which to perform insertion sort
        // used simple gap/2 for subsequent gap to keep things simple
        for (int gap = arr.length/2; gap > 0; gap = gap/2) {

            // For each gap-subarray, perform a gapped insertion sort
            // NOTE: {i} is the index of ELEMENT TO INSERT IN THIS PASS OF THE GAPPED INSERTION SORT
            for (int i = gap; i < arr.length; i++) {
                T temp = arr[i];
                int j = i;

                // check against all preceeding elements in gapped-subarray
                // stop if you reach the beginning of the subarray OR the temp value is larger than
                // any preceeding elements
                while (j >= gap && (comparator.compare(arr[j - gap], temp) > 0)) {
                    // move element at arr[j - gap] ONE gap-hop to the right
                    arr[j] = arr[j - gap];
                    j -= gap; // proceed to the next proceeding gapped-subarray index
                }

                // now that the all the larger elements in the subarray are to the right,
                // put temp in its correct location
                arr[j] = temp;
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
