package dsalgo.datastructures;

import java.util.Comparator;

/**
 * This class represents a sorted linked list
 * The list is continually mainted in sorted order at each update (creation,
 * insert)
 */
public class SortedLinkedList<T> {

    Node head = null;
    int length = 0;

    public class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Maintain sorted order with every update to the list
    public void insert(T data, Comparator<T> comparator) {
        Node newNode = new Node(data);
        // if list is empty or data should be at the head
        if (this.head == null || comparator.compare(data, this.head.data) < 0) {
            newNode.next = this.head;
            this.head = newNode;
        } else {
            Node currentNode = this.head;
            // Traverse through the list until you reach the proper place to put newNode
            while (currentNode.next != null && comparator.compare(data, currentNode.next.data) >= 0) {
                currentNode = currentNode.next;
            }
            // Once you stop, insert the newNode
            newNode.next = currentNode.next;
            currentNode.next = newNode;
        }
        this.length++;
    }

    // deletes the first node that has {data} if found
    public T delete(T data, Comparator<T> comparator) {
        if (this.head == null || this.length == 0) {
            System.out.println("Cannot delete from an empty list");
            return null;
        } else {
            // If match found at head,
            if (comparator.compare(data, this.head.data) == 0) {
                // delete the Node at head
                T returnVal = this.head.data;
                this.head = this.head.next;
                this.length--;
                return returnVal;
            } else { // otherwise, look through the list
                Node currentNode = this.head;
                // Traverse through the list to find the Node with {data}
                while (currentNode.next != null && comparator.compare(data, currentNode.next.data) > 0) {
                    currentNode = currentNode.next;
                }
                if (currentNode.next != null && comparator.compare(data, currentNode.next.data) == 0) {
                    // if the next node matches
                    T returnVal = currentNode.next.data;
                    currentNode.next = currentNode.next.next;
                    this.length--;
                    return returnVal;
                } else {
                    // if the next node doesn't match, then there will be no matches due to sorted
                    // behavior
                    System.out.println("Unable to find Node to delete");
                    return null;
                }
            }
        }
    }

    public T deleteAtPosition(int index) {
        // Assume 0-based indexing, like with arrays
        // if the index is not of bounds
        T data = null;
        if (index >= 0 && index < length) {
            if (index == 0) {
                // Remove the head from the linked list to be garbage collected
                data = this.head.data;
                this.head = this.head.next;
                return data;
            } else {
                // Traverse to the index and delete currentNode
                Node currentNode = this.head;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.next;
                }
                data = currentNode.next.data;
                currentNode.next = currentNode.next.next;
            }
            this.length--;
            return data;
        } else {
            System.out.println("Unable to delete node");
            return null;
        }
    }

    public static void main(String[] args) {
        SortedLinkedList<Integer> ints = new SortedLinkedList<>();
        Comparator<Integer> intComparator = (x, y) -> Integer.compare(x, y);

        ints.insert(0, intComparator);
        ints.insert(2, intComparator);
        ints.insert(0, intComparator);
        ints.insert(1, intComparator);
        ints.insert(5, intComparator);

        SortedLinkedList<Integer>.Node current = ints.head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        System.out.print("\n\n");

        ints.deleteAtPosition(3);
        current = ints.head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        System.out.print("\n\n");

        ints.delete(5, intComparator);
        current = ints.head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        System.out.print("\n\n");
    }
}
