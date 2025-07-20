package dsalgo.sorts;

import dsalgo.utility.GenerateRandom;
import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {

    /**
     * Namesake sorting method implementation: MergeSort
     * 
     * @param <T>        A generic Object wrapper type (NOT PRIMITIVE!)
     * @param arr        An array of type:T
     * @param comparator A comparator of type:T which implements compare()
     */
    // Recursive sorting algorithm, divides the array and subsequent subarrays in half until one element each
    // then merges and sorts those subarrays together
    public static <T> T[] mergeSort(T[] arr, Comparator<T> comparator) {
        // Base case
        if (arr.length <= 1) return arr;
        
        // Divide arr into two subarrays
        int middleIdx = arr.length / 2;
        T[] left = Arrays.copyOfRange(arr, 0, middleIdx);
        T[] right = Arrays.copyOfRange(arr, middleIdx, arr.length);

        // Recursively sort the subarrays
        left = mergeSort(left, comparator);
        right = mergeSort(right, comparator);

        return merge(left, right, comparator);
    }

    // Merge helper function
    private static <T> T[] merge(T[] left, T[] right,  Comparator<T> comparator) {
        // Create an array of type:T by copying the left array and extending the length
        // We will overwrite the array later
        T[] sorted = Arrays.copyOf(left, left.length + right.length); 

        int leftIdx = 0;
        int rightIdx = 0;
        int sortedIdx = 0;

        // Compare the sorted arrays element-wise and merge them
        while (leftIdx < left.length && rightIdx < right.length) {
            if (comparator.compare(left[leftIdx], right[rightIdx]) <= 0) {
                sorted[sortedIdx] = left[leftIdx];
                leftIdx++;
                sortedIdx++;
            } else {
                sorted[sortedIdx] = right[rightIdx];
                rightIdx++;
                sortedIdx++;
            }
        }

        // Place the remaining elements at the end 
        // (this should only ever happen for EITHER right or left, not both)
        while (leftIdx < left.length){
            sorted[sortedIdx] = left[leftIdx];
            leftIdx++;
            sortedIdx++;
        }
        while (rightIdx < right.length) {
            sorted[sortedIdx] = right[rightIdx];
            rightIdx++;
            sortedIdx++;
        }

        return sorted;
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
        intArray = mergeSort(intArray, intComparator);
        Arrays.stream(intArray).forEach((i) -> System.out.print(i + " "));
        System.out.print("]\n\n");

        System.out.print("Unsorted doubleArray: [ ");
        Arrays.stream(doubleArray).forEach((i) -> System.out.print(String.format("%.4f", i) + " "));
        System.out.print("]\n\n");

        System.out.print("Sorted doubleArray: [ ");
        doubleArray = mergeSort(doubleArray, doubleComparator);
        Arrays.stream(doubleArray).forEach((i) -> System.out.print(String.format("%.4f", i) + " "));
        System.out.print("]\n\n");
    }

}
