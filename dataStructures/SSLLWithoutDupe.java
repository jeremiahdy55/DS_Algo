package dataStructures;

import java.util.Arrays;
import java.util.Comparator;
import utility.GenerateRandom;

// This is an implementation of creating a single sorted linked list
// without duplicates given a random unsorted array.
// Do this without sorting the array first.
public class SSLLWithoutDupe<T> extends SortedLinkedList<T> {

    // Override the Sorted Singly-linked list insert method
    // to implement duplicate element check
    @Override
    public void insert (T data, Comparator<T> comparator) {
        // Maintain sorted order with every update to the list
        Node newNode = new Node(data);

        // if list is empty
        if (this.head == null) {
            this.head = newNode;
            this.length++;
            return;
        }

        // Node to insert is equal to head.data, then do nothing and return early
        if (this.head != null && comparator.compare(data, this.head.data) == 0) {
            return;
        }

        // Node to insert is less than head.data
        if (this.head != null && comparator.compare(data, this.head.data) < 0) {
            newNode.next = this.head;
            this.head = newNode;
            this.length++;
            return;
        }

        // Node to insert is after head
        // Traverse to the correct location
        Node currentNode = this.head;
        while (currentNode.next != null && comparator.compare(data, currentNode.next.data) > 0) {
            currentNode = currentNode.next;
        }
        // If duplicate found, do nothing and return early
        if (currentNode.next != null && comparator.compare(data, currentNode.next.data) == 0) {
            return;
        } else {
            newNode.next = currentNode.next;
            currentNode.next = newNode;
            this.length++;
        }

    }

    // Converts the array of T elements into a SSLWithoutDupe list
    public static <T> SSLLWithoutDupe<T> convertList (T[] arr, Comparator<T> comparator) {
        SSLLWithoutDupe<T> list = new SSLLWithoutDupe<>();
        for (T data : arr) {
            list.insert(data, comparator);
        }
        return list;
    }

    // print all elements
    public void traverseAndPrint() {
        Node currentNode = this.head;
        int index = 0;
        while (currentNode != null) {
            System.out.println("index " + index + ": " + currentNode.data);
            index++;
            currentNode = currentNode.next;
        }
    }

    public static void main(String[] args) {
        Integer[] intArray1 = GenerateRandom.intArray(0, 10, 10);
        Integer[] intArray2 = GenerateRandom.intArray(0, 30, 20);
        Comparator<Integer> intComparator = (x, y) -> Integer.compare(x, y);
        
        SSLLWithoutDupe<Integer> ints1;
        SSLLWithoutDupe<Integer> ints2;

        System.out.println("----------------Printing intArray1----------------");
        System.out.print("[");
        Arrays.stream(intArray1).forEach(x -> System.out.print(String.format(" %d ", x)));
        System.out.println("]");

        System.out.println("----------------Printing intArray2----------------");
        System.out.print("[");
        Arrays.stream(intArray2).forEach(x -> System.out.print(String.format(" %d ", x)));
        System.out.println("]");

        ints1 = SSLLWithoutDupe.convertList(intArray1, intComparator);
        ints2 = SSLLWithoutDupe.convertList(intArray2, intComparator);

        System.out.println("----------------Printing ints1----------------");
        ints1.traverseAndPrint();
        System.out.println("----------------Printing ints2----------------");
        ints2.traverseAndPrint();
    }
}
