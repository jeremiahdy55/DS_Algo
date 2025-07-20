package dsalgo.datastructures;

public class StackUsingLL<T> {
    public LinkedList<T> list;
    public LinkedList<T>.Node top = null;

    public StackUsingLL() {
        this.list = new LinkedList<>();
    }
    
    public void push(T data) {
        list.insertAtStart(data);
        top = list.head;
    }

    public T pop() {
        if (isEmpty()) {
            System.out.println("Stack underflow! Cannot retrieve data from empty stack");
            return null;
        }
        T temp = list.deleteAtPosition(0);
        top = list.head;
        return temp;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public T peek() {
        return isEmpty() ? null : list.head.data;
    }

}

