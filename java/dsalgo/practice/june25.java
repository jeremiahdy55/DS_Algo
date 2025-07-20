package practice;

import dataStructures.LinkedList;
import java.util.Comparator;

public class june25 {

    public static void main(String[] args) {

        Comparator<Integer> intComparator = (x, y) -> Integer.compare(x, y);

        LinkedList<Integer> oddList = new LinkedList<>();
        oddList.insertAtEnd(1);
        oddList.insertAtEnd(3);
        oddList.insertAtEnd(5);
        oddList.insertAtEnd(7);
        System.out.print("oddList:  ");
        traverseAndPrint(oddList);

        LinkedList<Integer> evenList = new LinkedList<>();
        evenList.insertAtEnd(2);
        evenList.insertAtEnd(4);
        evenList.insertAtEnd(6);
        evenList.insertAtEnd(8);
        evenList.insertAtEnd(10);
        System.out.print("evenList: ");
        traverseAndPrint(evenList);
        System.out.println();

        System.out.println("-------------- Pairwise swap --------------");
        System.out.print("oddList swapped pairwise:  ");
        traverseAndPrint(swapPairwise(oddList));
        System.out.print("evenList swapped pairwise: ");
        traverseAndPrint(swapPairwise(evenList));
        System.out.println();
        System.out.println();


        System.out.println("-------------- Combine two lists into decreasing order --------------");
        System.out.print("Combining oddList and evenList into decreasing order: ");
        traverseAndPrint(returnDecreasingSortedList(oddList, evenList, intComparator));
        System.out.println();
        System.out.println();


        System.out.println("-------------- Delete nth node from the end --------------");
        System.out.print("oddList  | n=3 : ");
        oddList = removeNthNodeFromList(oddList, 3);
        traverseAndPrint(oddList);
        System.out.print("evenList | n=0 : ");
        evenList = removeNthNodeFromList(evenList, 0);
        traverseAndPrint(evenList);
        System.out.print("evenList | n=5 : ");
        evenList = removeNthNodeFromList(evenList, 5);
        traverseAndPrint(evenList);
        System.out.println();
        System.out.println();





    }

    //Given a singly linked list, swap pairwise (if odd # of elements, no swap at the end)
    public static <T> LinkedList<T> swapPairwise(LinkedList<T> list) {
        LinkedList<T>.Node current = list.head;
        LinkedList<T> newList = new LinkedList<>();
        while (current != null && current.next != null) {
            // since we're iterating through {list} sequentially, use insertAtEnd
            // to keep pairs relative positioning correct with other pairs
            newList.insertAtEnd(current.next.data);
            newList.insertAtEnd(current.data);
            current = current.next.next; // skip over current.next
        }
        if (current != null) {
            // in the case of odd length lists, insert the final element without swapping
            newList.insertAtEnd(current.data);
        }
        return newList;
    }

    // Given two linked lists sorted in increasing order.
    //Merge them such a way that the result list is in decreasing order (reverse order).
    // NOTE: This could be accomplished by inserting all elements into the custom implementation
    //       of dataStructures.DoublySortedLL and calling reverse()
    //       For the sake of this exercise, I will be using LinkedList that is manually created in order
    public static <T> LinkedList<T> returnDecreasingSortedList (LinkedList<T> list1, LinkedList<T> list2, Comparator<T> comparator) {
        LinkedList<T>.Node temp_1 = list1.head;
        LinkedList<T>.Node temp_2 = list2.head;
        LinkedList<T> newList = new LinkedList<>();

        while (temp_1 != null && temp_2 != null) {
            // compare the two elements
            if (comparator.compare(temp_1.data, temp_2.data) == 0) {
                // temp_1 == temp_2, then use temp_1
                newList.insertAtStart(temp_1.data);
                temp_1 = temp_1.next;
            } else if (comparator.compare(temp_1.data, temp_2.data) > 0) {
                // temp_1 > temp_2
                newList.insertAtStart(temp_2.data);
                temp_2 = temp_2.next;
            } else if (comparator.compare(temp_1.data, temp_2.data) < 0) {
                // temp_1 < temp_2
                newList.insertAtStart(temp_1.data);
                temp_1 = temp_1.next;
            }
        }

        // if there are any leftover elements, append them to the front of the newList
        // NOTE: there are no additional checks needed here since this will only occur
        //       for EITHER list1 or list2, not both (due to conditional in while-loop above)
        while (temp_1 != null) {
            newList.insertAtStart(temp_1.data);
            temp_1 = temp_1.next;
        }

        while (temp_2 != null) {
            newList.insertAtStart(temp_2.data);
            temp_2 = temp_2.next;
        }

        return newList;
    }

    
    // Given the head of a linked list, remove the nth node from the end of the list and return the new list.
    // Follow up: Could you do this in one pass?
    public static <T> LinkedList<T> removeNthNodeFromList(LinkedList<T> list, int nthFromEnd) {
        // Implementation 1
        // return list.deleteAtPosition(nthFromEnd);

        // Implementation 2
        LinkedList<T>.Node dummy = list.new Node(null);
        dummy.next = list.head;

        LinkedList<T>.Node current = dummy;
        LinkedList<T>.Node vanguard = dummy;
        for (int i = 0; i < nthFromEnd; i++) {
            if (vanguard.next != null) {
                 // move the vanguard pointer up {nthFromEnd} number of spaces
                vanguard = vanguard.next;
            } else {
                System.out.println("UNDERFLOW");
                return null;
            }
        }

        // traverse through the list, move each pointer by one space at a time
        // until vanguard pointer reaches the end of the list
        while (vanguard.next != null) {
            current = current.next;
            vanguard = vanguard.next;
        }

        // at this point, current.next should be the nth node from the end to delete
        if (current.next != null) {
            current.next = current.next.next;
        }

        // update the list head in case head was removed, otherwise this does nothing
        list.head = dummy.next; 
        return list;
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
}
