package dsalgo.datastructures;

import java.util.Comparator;

public class ArrayBinaryTree<T> {
 
    public T[] tree;
    public Comparator<T> comparator;

    public ArrayBinaryTree() {
        this.tree = null;
        this.comparator = null;
    }

    public void setTree(T[] tree) {
        this.tree = tree;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public boolean isValid(int index) {
        return (tree != null && index >= 0 && index <= tree.length - 1);
    }

    public T findLeftChild(int index) {
        if (isValid(index)) {
            int leftChildIndex = (2*index) + 1;
            if (isValid(leftChildIndex)) {
                return tree[leftChildIndex];
            }
        }
        return null;
    }

    public T findRightChild(int index) {
        if (isValid(index)) {
            int rightChildIndex = (2*index) + 2;
            if (isValid(rightChildIndex)) {
                return tree[rightChildIndex];
            }
        }
        return null;
    }

    public T findParent(int index) {
        // index == 0 -> root node has no parent
        if (isValid(index) && index != 0) {
            int parentIndex = (index - 1) / 2;
            if (isValid(parentIndex)) {
                return tree[parentIndex];
            }
        }
        return null;
    }

    public String traverse(String orderStyle) {
        StringBuilder sb = new StringBuilder();
        if (orderStyle.toLowerCase().equals("preorder")) {
            traversePreOrderHelper(0, sb);
        } else if (orderStyle.toLowerCase().equals("postorder")) {
            traversePostOrderHelper(0, sb);
        } else if (orderStyle.toLowerCase().equals("inorder")) {
            traverseInOrderHelper(0, sb);
        } else {
            return "Please specify from [\"preorder\", \"postorder\", \"inorder\"]";
        }
        return sb.toString().trim(); // removes all trailing space
    }

    /// Helper traversal functions ///
    private void traversePreOrderHelper(int index, StringBuilder sb) {
        if (!isValid(index)) return;
        sb.append(tree[index]).append(" ");
        traversePreOrderHelper(2 * index + 1, sb);
        traversePreOrderHelper(2 * index + 2, sb);
    }

    private void traversePostOrderHelper(int index, StringBuilder sb) {
        if (!isValid(index)) return;
        traversePostOrderHelper(2 * index + 1, sb);
        traversePostOrderHelper(2 * index + 2, sb);
        sb.append(tree[index]).append(" ");
    }

    private void traverseInOrderHelper(int index, StringBuilder sb) {
        if (!isValid(index)) return;
        traverseInOrderHelper(2 * index + 1, sb);
        sb.append(tree[index]).append(" ");
        traverseInOrderHelper(2 * index + 2, sb);
    }
  
}


