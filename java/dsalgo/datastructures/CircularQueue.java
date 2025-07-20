package dsalgo.datastructures;

public class CircularQueue<T> {

    public Node front;
    public Node rear;
    
    public class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Insert the data into the rear of the queue
    public void insert(T data) {
        Node newNode = new Node(data);
        if (front == null && rear == null){
            // if queue is empty
            this.front = newNode;
            this.rear = newNode;
            this.rear.next = this.front; //maintain circle
        } else {
            // if queue is not empty
            this.rear.next = newNode; // make the current last element point to the newNode
            this.rear = newNode; // make newNode the last element
            this.rear.next = this.front; // maintain circle
        } 
    }

    // Execute the first element in the queue, and then remove it
    public T delete() {
        if (front == null || rear == null) {
            System.out.println("Cannot dequeue from an empty queue, UNDERFLOW");
            return null;
        }

        T data;

        if (front == rear) {
            // if the list is not empty and has only one element
            data = this.front.data;
            this.front = null;
            this.rear = null;
        } else {
            // if the list is not empty and has multiple elements
            data = this.front.data;
            this.front = this.front.next;
            this.rear.next = this.front; // maintain circle
        }
        return data;
    }

    // view the front element of the queue (this.front.data), if not null
    public T peek() {
        if (this.front != null) {
            return this.front.data;
        } else {
            System.out.println("Cannot peek from an empty queue, UNDERFLOW");
            return null;
        }
    }
}
