package dsalgo.practice;

import dsalgo.datastructures.LinkedList;
import dsalgo.datastructures.StackUsingLL;

public class july24_assessment {

    public static void main (String[] args) {
        int[] tasks = new int[] {3,2,1};
        System.out.println("Seconds taken: " + problem1_queues(tasks.length, tasks));
        System.out.println();

        LinkedList<Integer> list = new LinkedList<>();
        int[] LR_bounds = new int[] {2,3};
        list.insertAtEnd(2);
        list.insertAtEnd(1);
        list.insertAtEnd(4);
        list.insertAtEnd(3);
        System.out.println("Sublists found within bounds: " + problem2_ll(4, LR_bounds, list.head));
        System.out.println();

        int[][] matrix = new int[][] {
            {0,1,0},
            {0,0,0},
            {0,1,0}
        };
        int[][] matrix2 = new int[][] {
            {0,0,0,1},
            {0,0,0,1},
            {0,0,0,1},
            {0,0,0,0}
        };
        int[][] matrix3 = new int[][] {
            {0,0,0},
            {0,0,0},
            {0,0,0}
        };

        System.out.println("Teacher is found at: " + problem3_stack(3, matrix));
        System.out.println("Teacher is found at: " + problem3_stack(3, matrix3));
        System.out.println("Teacher is found at: " + problem3_stack(4, matrix2));

    }

    public static int problem1_queues (int n, int[] tasks) {
        // each tasks[i] represents a person
        // n represents number of people
        // tasks.length = n
        // constraint: 1 <= tasks[i] <= 100
        int seconds = 0;
        for (int i = 0; i < tasks.length; i++) {
            // count how many seconds it takes to complete half tasks until it reaches one;
            while (tasks[i] > 1) {
                seconds++;
                tasks[i] = tasks[i] - (tasks[i]/2);
            }
            // count the last task;
            seconds++;
        }
        return seconds;
    }

    public static int problem2_ll (int n, int[] LR_bounds, LinkedList<Integer>.Node head) {
        int left_bound = LR_bounds[0];
        int right_bound = LR_bounds[1];
        int count_sublists = 0;

        LinkedList<Integer>.Node current = head;
        // iterate through the linked list
        while (current != null) {
            // note the maximum value of the sublist at all times
            int max_value = Integer.MIN_VALUE;
            if (current.data > max_value) max_value = current.data;

            // for all nodes in the sub list, keep track if the max_value is within the bounds
            while (max_value >= left_bound && max_value <= right_bound) {
                count_sublists++;
                current = current.next;
                if (current == null) {
                    max_value = Integer.MAX_VALUE; // set a condition to break the sublist tracking loop
                } else if (current.data > max_value) {
                    max_value = current.data; // keep looking for potential sublists
                }
            }

            // once done counting sublists, move to the next node
            if (current != null && current.next != null) current = current.next;
        }
        return count_sublists;
    }

    public static int problem3_stack(int n, int[][] matrix) {
        // constraints: 2 <= n <= 501
        // Matrix is square
        // NOTE: a person will never "know" themselves, matrix[i][i] always 0

        // create an array to store the counts of how many people know the person at index i
        int[] countPersonIsKnown = new int [n];

        StackUsingLL<Integer> stack = new StackUsingLL<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                // if matrix[i][j] == 1, then the i_th person knows the j_th person
                // we push j onto the stack to count it later
                if (matrix[i][j] == 1) stack.push(j);
            }
        }

        while (!stack.isEmpty()) {
            // count how many times a person is seen
            countPersonIsKnown[stack.pop()]++;
        }

        for (int i = 0; i < countPersonIsKnown.length; i++) {
            // for every i_th person in the matrix, check how many people know them
            // if its (n-1) people, we have found the teacher
            // since the teacher cannot "know" themself
            if (countPersonIsKnown[i] == (n-1)) {
                return i;
            }
        }
        return -1; // unable to find teacher
    }
}
