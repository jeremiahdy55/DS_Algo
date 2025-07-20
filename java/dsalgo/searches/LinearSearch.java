package dsalgo.searches;

import dsalgo.utility.GenerateRandom;
import java.util.Arrays;
import java.util.Comparator;

public class LinearSearch {

    // Returns the index of the first element in the array if found, otherwise -1
    public static <T> int linearSearch(T[] arr, Comparator<T> comparator, T valueToFind) {
        for (int i = 0; i < arr.length; i++) {
            if (comparator.compare(arr[i], valueToFind) == 0) {
                return i;
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

        System.out.print("intArray: [ ");
        Arrays.stream(intArray).forEach((i) -> System.out.print(i + " "));
        System.out.print("]\n");

        System.out.print("Looking for [ " + intArray[4] +" ], this should return with index 4\n");
        System.out.println("linearSearch returns: " + linearSearch(intArray, intComparator, intArray[4]) + "\n");

        System.out.print("doubleArray: [ ");
        Arrays.stream(doubleArray).forEach((i) -> System.out.print(String.format("%.4f", i) + " "));
        System.out.print("]\n");

        System.out.print("Looking for [ " + 100.50 +" ], this should return with index -1\n");
        System.out.println("linearSearch returns: " + linearSearch(doubleArray, doubleComparator, 100.50) + "\n");

    }
}
