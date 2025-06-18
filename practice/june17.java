package practice;

import dataStructures.LinkedList;
import java.util.Comparator;

public class june17 {
    public static void main (String[] args) {
    // Modify linked list in such a way that last node become the first node of the list.
    // eg:
    // list:{1,2,3,4}
    // output:{4,1,2,3}
    System.out.println("First problem: move last to first");
    LinkedList<Integer> ints = new LinkedList<>();
    ints.insertAtStart(1);
    ints.insertAtEnd(2);
    ints.insertAtEnd(3);
    ints.insertAtEnd(4);

    traverseAndPrint(ints);

    /// Problem 1 Implementation
    // Traverse to the end
    LinkedList<Integer>.Node currentNode = ints.head;
    // traverse to the second to last node
    while(currentNode.next.next != null) {
        currentNode = currentNode.next;
    }
    // make the last Node point to the head
    currentNode.next.next = ints.head;
    // reassign head
    ints.head = currentNode.next;
    // make the now last Node point to null
    currentNode.next = null;
    traverseAndPrint(ints);


    // check if a linked list is palindrome or not!
    // eg1:
    // list={1,2,3,3,2,1}
    // output:palindrome
    // eg2:
    // list={1,2,3,2,1}
    // output: not palindrome
    System.out.println("Second problem: palindrome");
    Comparator<Integer> intComparator = (x, y) -> Integer.compare(x, y);
    LinkedList<Integer> ints2 = new LinkedList<>();
    ints2.insertAtStart(1);
    ints2.insertAtEnd(2);
    ints2.insertAtEnd(3);
    ints2.insertAtEnd(3);
    ints2.insertAtEnd(2);
    ints2.insertAtEnd(1);

    LinkedList<Integer> ints3 = new LinkedList<>();
    ints3.insertAtStart(1);
    ints3.insertAtEnd(2);
    ints3.insertAtEnd(3);
    ints3.insertAtEnd(2);
    ints3.insertAtEnd(1);

    System.out.print("ints2 array: ");
    traverseAndPrint(ints2);
    System.out.print("ints3 array: ");
    traverseAndPrint(ints3);
    System.out.println(String.format("LinkedList ints2 palindrome check: ") + checkIfPalindrome(ints2, intComparator));
    System.out.println(String.format("LinkedList ints3 palindrome check: ") + checkIfPalindrome(ints3, intComparator));


    // delete node from a singly list at the given difference.
    // input:[1,2,3,4,5,6,7,8]
    // difference=2
    // output:[1,4,7]
    // Start at head, save the current node, delete next {difference} number of Nodes, repeat...
    LinkedList<Integer> ints4 = new LinkedList<>();
    ints4.insertAtStart(1);
    ints4.insertAtEnd(2);
    ints4.insertAtEnd(3);
    ints4.insertAtEnd(4);
    ints4.insertAtEnd(5);
    ints4.insertAtEnd(6);
    ints4.insertAtEnd(7);
    ints4.insertAtEnd(8);
    ints4.insertAtEnd(9);
    ints4.insertAtEnd(10);
    System.out.println("Third problem: delete at difference");
    System.out.print("ints4 array: ");
    traverseAndPrint(ints4);
    System.out.println("deleting nodes at difference 2");
    deleteAtDifference(ints4, 2);
    System.out.print("ints4 array after delete at difference 2: ");
    traverseAndPrint(ints4);

    }

    public static <T> void traverseAndPrint(LinkedList<T> list) {
        LinkedList<T>.Node current = list.head;
        System.out.print("[ ");
        while(current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        } 
        System.out.println("]");
    }

    /// Problem 2 Implementation
    public static <T> boolean checkIfPalindrome(LinkedList<T> list, Comparator<T> comparator) {
        LinkedList<T> list2 = new LinkedList<>();
        LinkedList<T>.Node current = list.head;
        int length = 0;
        while(current != null) {
            list2.insertAtStart(current.data);
            current = current.next;
            length++;
        }
        // if length is odd, it can't be a palindrome
        if (length % 2 != 0) return false;
        
        // Else, check each node
        LinkedList<T>.Node nodeFrom1 = list.head;
        LinkedList<T>.Node nodeFrom2 = list2.head;
        while(nodeFrom1 != null && nodeFrom2 != null) {
            if (comparator.compare(nodeFrom1.data, nodeFrom2.data) != 0) {
                return false;
            }
            nodeFrom1 = nodeFrom1.next;
            nodeFrom2 = nodeFrom2.next;
        } 
        return true;
    }

    public static <T> void deleteAtDifference(LinkedList<T> list, int difference) {
        LinkedList<T>.Node current = list.head;
        while (current != null && current.next != null) {
            // Get the next {difference} nodes
            for (int i = 0; i < difference; i++) {
                // if the node isn't null, delete it
                if (current.next != null) {
                    current.next = current.next.next; // make the current node point to the next-next
                }
            }
            current = current.next;
        }

    }
}
