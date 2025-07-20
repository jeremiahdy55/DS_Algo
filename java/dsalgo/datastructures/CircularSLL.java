package dsalgo.datastructures;

public class CircularSLL<T> {

    Node head = null;
    Node tail = null;
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

        if (this.head == null) {
            // if list is empty
            this.head = newNode;
            this.tail = newNode;
            newNode.next = newNode; // point to itself
        } else {
            newNode.next = this.head;
            this.head = newNode;
            this.tail.next = this.head; // point to the next Node
        }

        this.length++;
    }

    public void insertAtEnd(T data) {
        Node newNode = new Node(data);

        if (this.head == null) {
            // if list is empty
            this.head = newNode;
            this.tail = newNode;
            newNode.next = newNode; // point to itself
        } else {
            this.tail.next = newNode;
            newNode.next = this.head; // point to the next Node
            this.tail = newNode; // overwrite tail
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
            } else if (index == length) {
                // Insert at end
                insertAtEnd(data);
            } else {
                Node newNode = new Node(data);
                Node currentNode = this.head;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.next;
                }
                newNode.next = currentNode.next;
                currentNode.next = newNode;
                this.length++;
            }
        } else {
            System.out.println("Unable to insert node");
        }
    }

    public T deleteAtPosition(int index) {
        // Assume 0-based indexing, like with arrays
        // if the index is not of bounds
        T data;
        if (index >= 0 && index < length) {
            if (index == 0) {
                // Remove the head from the linked list to be garbage collected
                data = this.head.data;

                if (this.head == this.tail) {
                    // Only one node
                    this.head = null;
                    this.tail = null;
                } else {
                    this.head = this.head.next;
                    this.tail.next = this.head; // Maintain circle
                }
            } else {
                // Traverse to the index and delete currentNode
                Node currentNode = this.head;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.next;
                }
                data = currentNode.next.data;
                // if currentNode.next is the tail, that means it's the last element of the list
                // & we need to overwrite tail
                if (currentNode.next == this.tail) {
                    this.tail = currentNode;
                }
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
        CircularSLL<String> strings = new CircularSLL<>();

        strings.insertAtStart("this is the first created element, made from insertAtStart");
        strings.insertAtStart("This is the second created element, made from insertAtStart");
        strings.insertAtPosition("This is the third created element, made from insertAtPosition with index=1", 1);
        strings.insertAtEnd("This is the fourth created element, made from insertAtEnd");

        CircularSLL<String>.Node current = strings.head;
        int i = 10;
        while (current != null && i > 0) {
            System.out.println(current.data);
            current = current.next;
            i--;
        }
        System.out.print("\n\n");

        strings.deleteAtPosition(0);
        current = strings.head;
        i = 10;
        while (current != null && i > 0) {
            System.out.println(current.data);
            current = current.next;
            i--;
        }
        System.out.print("\n\n");
    }
    
}
