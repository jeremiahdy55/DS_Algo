package dsalgo.datastructures;

import java.util.Comparator;

public class BST<T> {

    BSTNode<T> root;
    Comparator<T> comparator;

    public class BSTNode<T> {
        public T val;
        public BSTNode<T> left;
        public BSTNode<T> right;
        
        public BSTNode() {}
        public BSTNode(T val) {
            this.val = val;
        }
    }

    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    // Traversal
    public void traverseAndPrintInOrder() { 
        traverseAndPrintInOrder(root);
    }

    private void traverseAndPrintInOrder(BSTNode<T> parent) {
        if (parent != null) {
            traverseAndPrintInOrder(parent.left);
            System.out.println(parent.val);
            traverseAndPrintInOrder(parent.right);
        }
    }

    // Insertion
    public void insert(T val) {
        BSTNode<T> newNode = new BSTNode<>(val);

        // Empty tree
        if (root == null) {
            this.root = newNode;
        } else {
            BSTNode<T> currentNode = root;
            BSTNode<T> parent = null;

            // Go to the proper place in the BST
            while (currentNode != null) {
                parent = currentNode;
                if (comparator.compare(val, currentNode.val) < 0) {
                    // val is less than currentNode's val
                    currentNode = currentNode.left;
                } else if (comparator.compare(val, currentNode.val) >= 0) {
                    currentNode = currentNode.right;
                }
            }

            // Insert newNode as a child of {parent}
            if (comparator.compare(val, parent.val) < 0) {
                // val is less than currentNode's val
                parent.left = newNode;
            } else if (comparator.compare(val, parent.val) >= 0) {
                parent.right = newNode;
            }
        }
    }

    // Search for {data} and return the BSTNode
    public BSTNode<T> search(T data) {
        return search(data, root);
    }

    private BSTNode<T> search(T data, BSTNode<T> current) {
        if (current == null) {
            System.out.println("Item NOT found");
            return null;
        }
        if (comparator.compare(data, current.val) == 0) {
            System.out.println("Item found!");
            return current;
        }
        if (comparator.compare(data, current.val) < 0) {
            return search(data, current.left);
        } else {
            return search(data, current.right);
        }
    }

    // Delete
    public void delete(T data) {
        root = delete(data, root); // overwrite the past tree with the new tree after the node is deleted
    }

    private BSTNode<T> delete(T data, BSTNode<T> current) {
        if (current == null) {
            System.out.println("cannot delete element, not found!");
            return null;
        }
        int compResult = comparator.compare(data, current.val);
        if (compResult < 0) {
            current.left = delete(data, current.left);
        } else if (compResult > 0) {
            current.right = delete(data, current.right);
        } else {
            // Node found!
            System.out.println("Deleting node with val: " + data + "!");

            // Case 1: no children
            if (current.left == null && current.right == null) {
                return null;
            }

            // Case 2: one child
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }

            // Case 3: two children
            // Find inorder successor by finding the smallest node in the right subtree
            BSTNode<T> successor = current.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            current.val = successor.val; // overwrite the data at current with successor's data
            current.right = delete(successor.val, current.right); // rebalance the subtree that contained successor
        }
        return current;
    }

    public static void main (String[] args) {
        BST<Integer> bst = new BST<Integer>(Comparator.naturalOrder());
        bst.insert(1);
        bst.insert(5);
        bst.insert(5);
        bst.insert(2);
        bst.insert(6);
        bst.traverseAndPrintInOrder();
        BST<Integer>.BSTNode<Integer> node = bst.search(4);
        System.out.println("searching for 4, is node null: "+ (node==null));
        node = bst.search(5);
        System.out.println("searching for 5, is node null: "+ (node==null));
        bst.delete(1);
        bst.traverseAndPrintInOrder();

    }
}
