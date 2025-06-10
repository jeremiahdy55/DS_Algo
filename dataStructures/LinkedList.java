package dataStructures;

/**
 * This class represents an unsorted linked list (the most basic form)
 */
public class LinkedList<T> {

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

    public void insertAtStart(T data) {
        Node newNode = new Node(data);
        newNode.next = this.head;
        this.head = newNode;
        this.length++;
    }

    public void insertAtEnd(T data) {
        Node newNode = new Node(data);
        if (this.head == null) {
            this.head = newNode;
        } else {
            Node currentNode = this.head;
            // Traverse to the end of the LinkedList
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
        }
        this.length++;

    }

    public void insertAtPosition(T data, int index) {
        // Assume 0-based indexing, like with arrays
        // if the index is not of bounds
        if (index >= 0 && index <= length) {
            if (index == 0) {
                // Insert at start
                insertAtStart(data);
                return; // return to prevent double increment of length
            } else {
                // Traverse to the index and insert
                Node newNode = new Node(data);
                Node currentNode = this.head;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.next;
                }
                newNode.next = currentNode.next;
                currentNode.next = newNode;
            }
            this.length++;
        } else {
            System.out.println("Unable to insert node");
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

    public static void main (String[] args) {
        LinkedList<String> strings = new LinkedList<>();

        strings.insertAtStart("this is the first created element, made from insertAtStart");
        strings.insertAtStart("This is the second created element, made from insertAtStart");
        strings.insertAtPosition("This is the third created element, made from insertAtPosition with index=1", 1);
        strings.insertAtEnd("This is the fourth created element, made from insertAtEnd");

        LinkedList<String>.Node current = strings.head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        System.out.print("\n\n");

        strings.deleteAtPosition(0);
        current = strings.head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        System.out.print("\n\n");
    }
}
