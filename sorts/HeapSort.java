package sorts;

import java.util.Comparator;

public class HeapSort<T> {

    public static <T> T[] heapSort(T[] arr, Comparator<T> comparator) {
        // Build a min/max heap from the array first
        for (int i = arr.length/2; i >= 0; i--) {
            heapify(arr, arr.length, i, comparator);
        }
        // Extract elements from the heap one at a time, each sequential run does not consider the {i} last elements
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i); // swap the minimum/maximum value at position 0 to the end of the subarray
            heapify(arr, i, 0, comparator); // re-heapify the subarray from [0...i]
        }
        return arr;
    }


    public static <T> void heapify(T[] arr, int length, int i, Comparator<T> comparator) {
        // Using arr[i] as the parent...
        int leftChild = 2*i + 1;
        int rightChild = 2*i + 2;
        int target = i;

        if (leftChild < length && comparator.compare(arr[leftChild], arr[target]) > 0) {
            target = leftChild;
        }

        if (rightChild < length && comparator.compare(arr[rightChild], arr[target]) > 0) {
            target = rightChild;
        }

        if (target != i) {
            swap(arr, i, target);
            heapify(arr, length, target, comparator);
        }
    }


    // helper method to swap two elements in an array
    private static <T> void swap (T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Comparator<Integer> maxHeapComp =(x, y) -> Integer.compare(x, y);
        Comparator<Integer> minHeapComp =(x, y) -> Integer.compare(y, x);
        Integer[] ascendingArr  = new Integer[] {1, 3, 5, 7, 9, 2, 4, 6, 8, 10, 12, 11};
        Integer[] descendingArr = new Integer[] {1, 3, 5, 7, 9, 2, 4, 6, 8, 10, 12, 11};
        heapSort(ascendingArr, maxHeapComp);
        heapSort(descendingArr, minHeapComp);

        System.out.printf("ascendingArr after heapSort: [ ");
        for (Integer i : ascendingArr) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println("]");

        System.out.printf("descendingArr after heapSort: [ ");
        for (Integer i : descendingArr) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println("]");

    }
    
}
