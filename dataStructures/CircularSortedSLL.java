package dataStructures;

import java.util.Comparator;

public class CircularSortedSLL<T> {

    Node head = null;
    Node tail = null;
    // int length = 0;
    Comparator<T> comparator;
    boolean reversed = false; // flag to indicate if list is in reversed sorted order

    public class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public CircularSortedSLL(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private int compare(T data1, T data2) {
        int reverseList = 1;
        if (reversed) reverseList = -1;
        return comparator.compare(data1, data2) * reverseList;
    }

    public void insert(T data) {
        Node newNode = new Node(data);
        if (this.head == null) {
            // Case 1: if list is empty
            this.head = newNode;
            this.tail = newNode;
            newNode.next = newNode; //point to itself
        } else if (compare(data, this.head.data) < 0) {
            // Case 2: insert before the head Node
            newNode.next = this.head;
            this.tail.next = newNode;
            this.head = newNode;
        } else {
            // Case 3: insert somewhere else in list other than head
            Node current = this.head;
            // Traverse to the appropriate location
            while (current.next != this.head && compare(data, current.next.data) >= 0) {
                current = current.next;
            }
            if (current == this.tail) {
                this.tail = newNode;
            }
            newNode.next = current.next;
            current.next = newNode;
            this.tail.next = this.head; // Continually maintain circle

        }

    }

    // public static void main(String[] args) {
    //     SortedLinkedList<Integer> ints = new SortedLinkedList<>();
    //     Comparator<Integer> intComparator = (x, y) -> Integer.compare(x, y);

    //     ints.insert(0, intComparator);
    //     ints.insert(2, intComparator);
    //     ints.insert(0, intComparator);
    //     ints.insert(1, intComparator);
    //     ints.insert(5, intComparator);

    //     SortedLinkedList<Integer>.Node current = ints.head;
    //     while (current != null) {
    //         System.out.println(current.data);
    //         current = current.next;
    //     }
    //     System.out.print("\n\n");

    //     ints.deleteAtPosition(3);
    //     current = ints.head;
    //     while (current != null) {
    //         System.out.println(current.data);
    //         current = current.next;
    //     }
    //     System.out.print("\n\n");

    //     ints.delete(5, intComparator);
    //     current = ints.head;
    //     while (current != null) {
    //         System.out.println(current.data);
    //         current = current.next;
    //     }
    //     System.out.print("\n\n");
    // }
    
}
