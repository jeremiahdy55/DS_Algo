package dataStructures;

public class StackUsingArray<T> {

    public T[] stack;
    public int top = -1;
    public int size;

    public StackUsingArray(int size) {
        this.size = size;
        this.stack = (T[]) new Object[size];
    }
    
    public void push(T data) {
        if (top >= size - 1) {
            System.out.println("Stack overflow! Cannot insert new data");
            return;
        }
        stack[++top] = data; // increment first, then assign
    }

    public T pop() {
        if (top < 0) {
            System.out.println("Stack underflow! Cannot retrieve data from empty stack");
            return null;
        }
        T temp = stack[top];
        stack[top--] = null; // assign first, then decrement
        return temp;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == size - 1;
    }

    public T peek() {
        return isEmpty() ? null : stack[top];
    }

}
