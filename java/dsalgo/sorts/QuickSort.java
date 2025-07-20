package dsalgo.sorts;

import dsalgo.utility.GenerateRandom;
import java.util.Arrays;
import java.util.Comparator;

public class QuickSort {

    /**
     * Namesake sorting method implementation: MergeSort
     * Recursive sorting alogrithm, partitions the array based on a pivot, then recursively
     * calls itself on those partitions (minus the pivot element) to sort/partition those subarrays.
     * 
     * @param <T>        A generic Object wrapper type (NOT PRIMITIVE!)
     * @param arr        An array of type:T
     * @param comparator A comparator of type:T which implements compare()
     */
    public static <T> void quickSort(T[] arr, int firstIdx, int lastIdx, Comparator<T> comparator) {
        // if firstIdx == lastIdx OR firstIdx > lastIdx, the subarray is invalid
        if (firstIdx < lastIdx) {
            // Set the pivot to be the median of the first, middle, and last elements
            int pivotIndex = medianOfThree(arr, firstIdx, lastIdx, comparator);

            // Move the pivot to the end of the array (out of the way)
            swap(arr, lastIdx, pivotIndex);

            // Partition the array around the pivot, and get the pivot element's correctly placed index.
            int partitionIndex = partition(arr, firstIdx, lastIdx, comparator);

            // Recursively call quickSort method on the remaining unsorted left and right partitions
            quickSort(arr, firstIdx, partitionIndex - 1, comparator);
            quickSort(arr, partitionIndex + 1, lastIdx, comparator);
        }
    }

    // Partition helper function
    // Given the pivot element at lastIdx, partitions the array such that all elements
    // less than the pivot are to the left, and vice versa. Returns the index of the pivot element.
    private static <T> int partition(T[] arr, int firstIdx, int lastIdx, Comparator<T> comparator) {
        T pivot = arr[lastIdx];
        int i = firstIdx - 1; // tracker for all elements less than pivot

        // Cycle through all the elements other than the last element
        for (int j = firstIdx; j < lastIdx; j++) {
            // If the value is less than pivot
            if (comparator.compare(arr[j], pivot) <= 0) {
                // Swap the current value (arr[j]) so that its going to be to the left of pivot
                i++;
                swap(arr, i, j);
            }
        }
        // Swap the pivot to its proper place
        i++;
        swap(arr, lastIdx, i);
        return i;
    }

    // Swap helper function: this function swaps two array elements in-place
    private static <T> void swap (T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


    // Median-Of-Three helper function
    private static <T> int medianOfThree(T[] arr, int firstIdx, int lastIdx,  Comparator<T> comparator) {
        int middleIdx = (firstIdx + lastIdx) / 2;

        T first = arr[firstIdx];
        T middle = arr[middleIdx];
        T last = arr[lastIdx];

        boolean compareFM = comparator.compare(first, middle) <= 0;
        boolean compareML = comparator.compare(middle, last) <= 0;
        boolean compareFL = comparator.compare(first, middle) <= 0;

        // Compare first, middle, and last elements and determine the median
        if (compareFM) { // first <= middle
            if (compareML) {
                return middleIdx; // first <= middle <= last
            } else if (compareFL) {
                return lastIdx; // first <= last < middle
            } else {
                return firstIdx; // last < first <= middle
            }
        } else { // middle < first
            if (compareFL) {
                return firstIdx; // middle < first <= last
            } else if (compareML) {
                return lastIdx; // middle <= last < first
            } else {
                return middleIdx; // last < middle < first
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
        quickSort(intArray, 0, intArray.length - 1, intComparator);
        Arrays.stream(intArray).forEach((i) -> System.out.print(i + " "));
        System.out.print("]\n\n");

        System.out.print("Unsorted doubleArray: [ ");
        Arrays.stream(doubleArray).forEach((i) -> System.out.print(String.format("%.4f", i) + " "));
        System.out.print("]\n\n");

        System.out.print("Sorted doubleArray: [ ");
        quickSort(doubleArray, 0, doubleArray.length - 1, doubleComparator);
        Arrays.stream(doubleArray).forEach((i) -> System.out.print(String.format("%.4f", i) + " "));
        System.out.print("]\n\n");
    }

}
