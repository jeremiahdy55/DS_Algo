package dataStructures;

import java.util.Comparator;

public class DoublySortedLL<T> {

    Node head = null;
    Node tail = null;
    int length = 0;
    Comparator<T> comparator;
    boolean reversed = false;

    public class Node {
        T data;
        Node next;
        Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    public DoublySortedLL(Comparator<T> comparator) {
        this.comparator = comparator;
    }


    private int compare(T data1, T data2) {
        int reverseList = 1;
        if (reversed) reverseList = -1;
        return comparator.compare(data1, data2) * reverseList;
    }

    // Maintain sorted order with every update to the list
    public void insert(T data) {
        Node newNode = new Node(data);
        if (this.head == null) {
            // if list is empty
            this.head = newNode;
            this.tail = newNode;
        } else if (compare(data, this.head.data) < 0) {
            // if list is not empty and node should be at the start
            this.head.prev = newNode;
            newNode.next = this.head;
            this.head = newNode;
        } else {
            // if list is not empty and node should be inserted anywhere else, traverse forward
            Node currentNode = this.head;
            while (currentNode.next != null && compare(data, currentNode.next.data) >= 0) {
                currentNode = currentNode.next;
            }

            if (currentNode.next == null) {
                // inserting at the end
                currentNode.next = newNode;
                newNode.prev = currentNode;
                this.tail = newNode;
            } else {
                // inserting somewhere in the middle
                newNode.next = currentNode.next;
                currentNode.next.prev = newNode;
                currentNode.next = newNode;
                newNode.prev = currentNode;
            }
        }
        this.length++;
    }

    public void reverse() {
        Node currentNode = this.head;
        Node tempNode = null;
        // for any node that exists
        while (currentNode != null) {
            // switch the pointers to next and prev
            tempNode = currentNode.prev;
            currentNode.prev = currentNode.next;
            currentNode.next = tempNode;
            // move to the next node (which now sits at prev)
            currentNode = currentNode.prev;
        }
        // Swap the head and tail if the list is more than two nodes
        if (tempNode != null) {
            this.tail = this.head;
            this.head = tempNode.prev;
        }
        this.reversed = !this.reversed;
    }

    public void traverseAndPrint() {
        Node currentNode = this.head;
        int index = 0;
        System.out.println("Traversing DoublySLL");
        while (currentNode != null) {
            System.out.println("index " + index + ": " + currentNode.data);
            index++;
            currentNode = currentNode.next;
        }
    }

    // deletes the first node that has {data} if found
    public T delete(T data) {
        if (this.head == null || this.length == 0) {
            System.out.println("Cannot delete from an empty list");
            return null;
        } else {
            // If match found at head,
            if (compare(data, this.head.data) == 0) {
                // delete the Node at head
                T returnVal = this.head.data;
                this.head = this.head.next;

                // if list originally had more than one Node
                if (this.head != null) {
                    this.head.prev = null; // set the prev pointer
                } else {
                    // otherwise the list is now empty
                    this.tail = null;
                }
                this.length--;
                return returnVal;
            } else { // otherwise, look through the list
                Node currentNode = this.head;

                // Traverse through the list to find the Node with {data} at currentNode.next
                while (currentNode.next != null && compare(data, currentNode.next.data) > 0) {
                    currentNode = currentNode.next;
                }

                // if the currentNode.next matches, remember its data and reroute its pointers
                if (currentNode.next != null && compare(data, currentNode.next.data) == 0) {
                    T returnVal = currentNode.next.data;
                    currentNode.next = currentNode.next.next;

                    if (currentNode.next != null) {
                        // if the deleted Node was in the middle of the list
                        currentNode.next.prev = currentNode;
                    } else {
                        // else, the deleted Node was the tail, so update tail
                        this.tail = currentNode;
                    }
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
        T data = null;
        // if the index is not of bounds
        if (index >= 0 && index < length) {
            if (index == 0) {
                // Remove the head from the linked list to be garbage collected
                data = this.head.data;
                this.head = this.head.next;

                // if list originally had more than one Node
                if (this.head != null) {
                    this.head.prev = null; // set the prev pointer
                } else {
                    // otherwise the list is now empty
                    this.tail = null;
                }
            } else {
                // Traverse to the index and delete currentNode
                Node currentNode = this.head;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.next;
                }
                data = currentNode.next.data;
                currentNode.next = currentNode.next.next;
                if (currentNode.next != null) {
                    // if the deleted Node was in the middle of the list
                    currentNode.next.prev = currentNode;
                } else {
                    // else, the deleted Node was the tail, so update tail
                    this.tail = currentNode;
                }
            }
            this.length--;
            return data;
        } else {
            System.out.println("Unable to delete node");
            return null;
        }
    }

    public static void main(String[] args) {
        Comparator<Integer> intComparator = (x, y) -> Integer.compare(x, y);
        DoublySortedLL<Integer> ints = new DoublySortedLL<>(intComparator);

        ints.insert(0);
        ints.insert(2);
        ints.insert(0);
        ints.insert(1);
        ints.insert(5);

        ints.traverseAndPrint();
        System.out.println("reversing list!");
        ints.reverse();
        ints.traverseAndPrint();
        ints.deleteAtPosition(3);
        ints.traverseAndPrint();
        ints.delete(5);
        ints.traverseAndPrint();
    }
    
}
