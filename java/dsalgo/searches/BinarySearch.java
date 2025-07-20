package dsalgo.searches;

import dsalgo.sorts.QuickSort;
import dsalgo.utility.GenerateRandom;
import java.util.Arrays;
import java.util.Comparator;

public class BinarySearch {
    // Returns the index of the element in the array if found (if duplicates, returns the first found), otherwise -1
    public static <T> int binarySearch(T[] arr, Comparator<T> comparator, T valueToFind, int firstIdx, int lastIdx) {
        if (lastIdx >= firstIdx) {
            // Get the middle index of the array
            int middleIdx = firstIdx + (lastIdx - firstIdx) / 2;

            // Compare the element at middleIdx to valueToFind
            int compareResult = comparator.compare(arr[middleIdx], valueToFind);

            if (compareResult == 0) {
                // If found, return the index
                return middleIdx;
            } else if (compareResult > 0) {
                // If the middleIdx is greater than the valueToFind -> search the lesser half of the array
                return binarySearch(arr, comparator, valueToFind, firstIdx, middleIdx - 1);
            } else {
                // the middleIdx is less than the valueToFind -> search the greater half of the array
                return binarySearch(arr, comparator, valueToFind, middleIdx + 1, lastIdx);

            }
        }
        return -1; // if valueToFind is not in the array, return -1
    }
    

    public static void main(String[] args) {
        // initialize comparators and random number arrays used for example
        Comparator<Integer> intComparator = (x, y) -> Integer.compare(x, y);
        Comparator<Double> doubleComparator = (x, y) -> Double.compare(x, y);
        Integer[] intArray = GenerateRandom.intArray(0, 10, 10);
        Double[] doubleArray = GenerateRandom.doubleArray(0.0, 10.50, 10);

        //Sort the arrays
        QuickSort.quickSort(intArray, 0, intArray.length - 1, intComparator);
        QuickSort.quickSort(doubleArray, 0, doubleArray.length - 1, doubleComparator);

        System.out.print("intArray: [ ");
        Arrays.stream(intArray).forEach((i) -> System.out.print(i + " "));
        System.out.print("]\n");

        System.out.print("Looking for [ " + intArray[4] +" ], this should return with index 4\n");
        System.out.println("binarySearch returns: " + binarySearch(intArray, intComparator, intArray[4], 0, intArray.length -1) + "\n");

        System.out.print("doubleArray: [ ");
        Arrays.stream(doubleArray).forEach((i) -> System.out.print(String.format("%f", i) + " "));
        System.out.print("]\n");

        System.out.print("Looking for [ " + 100.5 +" ], this should return with index -1\n");
        System.out.println("binarySearch returns: " + binarySearch(doubleArray, doubleComparator, 100.5, 0, doubleArray.length -1) + "\n");

    }
}
