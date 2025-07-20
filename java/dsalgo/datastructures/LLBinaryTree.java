package dataStructures;

import java.util.Comparator;

// NOTE: Assuming all Nodes contain unique data!!!
// To actually build the binary tree you will have to manually assign left, right in whatever method call
public class LLBinaryTree<T> {
    
    public Node root;
    public Comparator<T> comparator;

    public class Node {
        public T data;
        public Node left;
        public Node right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    public LLBinaryTree() {
        this.root = null;
        this.comparator = null;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    
    public boolean isEqual(Node node, T val) {
        if (comparator == null) return false;
        return comparator.compare(node.data, val) == 0;
    }
    
    public Node findNode(Node node, T data) {
        if (node == null) return null;
        if (isEqual(node, data)) {
            return node;
        }
        Node checkInLeft  = (node.left != null) ? findNode(node.left, data) : null;
        Node checkInRight = (node.right != null) ? findNode(node.right, data) : null;
        if (checkInLeft != null)  return checkInLeft;
        if (checkInRight != null) return checkInRight;
        // otherwise no Node exists with {data}, return null
        return null;
    }

    // retrieve the left child of the Node with data == {data}
    public Node findLeftChild(T data) {
        Node parent = findNode(root, data);
        if (parent == null) return null;
        return parent.left;
    }

    // retrieve the right child of the Node with data == {data}
    public Node findRightChild(T data) {
        Node parent = findNode(root, data);
        if (parent == null) return null;
        return parent.right;
    }

    // wrapper method of findParent() to always searching at root
    public Node findParentOf(T data) {
        if (root == null || isEqual(root,data)) return null; // empty tree OR root has no parent
        return findParent(root,data);
    }

    // recursively retrieve the parent of the Node with data == {data}, starting at {current}
    public Node findParent(Node current, T data) {

        if (current == null) return null; // Node is null
        if (current.left == null && current.right == null) return null; // leaf node cannot be a parent
        
        // if the node whose parent we are searching for is found in either the left or right child of current,
        // then return current
        boolean foundInLeft = (current.left != null && isEqual(current.left, data));
        boolean foundInRight = (current.right != null && isEqual(current.right, data));
        if (foundInLeft || foundInRight) {
            return current;
        }

        // recursively check the left subtree
        Node checkLeft = findParent(current.left, data);
        if (checkLeft != null) return checkLeft;

        // if the left subtree doesn't work, use the right subtree as the base case
        // once the right subtree successfully finds the node or terminates at a leaf node, the result will propagate up
        return findParent(current.right, data);
    }

    public String traverse(String orderStyle) {
        StringBuilder sb = new StringBuilder();
        if (orderStyle.toLowerCase().equals("preorder")) {
            traversePreOrderHelper(root, sb);
        } else if (orderStyle.toLowerCase().equals("postorder")) {
            traversePostOrderHelper(root, sb);
        } else if (orderStyle.toLowerCase().equals("inorder")) {
            traverseInOrderHelper(root, sb);
        } else {
            return "Please specify from [\"preorder\", \"postorder\", \"inorder\"]";
        }
        return sb.toString().trim(); // removes all trailing space
    }

    /// Helper traversal functions ///
    private void traversePreOrderHelper(Node node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.data).append(" ");
        traversePreOrderHelper(node.left, sb);
        traversePreOrderHelper(node.right, sb);
    }

    private void traversePostOrderHelper(Node node, StringBuilder sb) {
        if (node == null) return;
        traversePostOrderHelper(node.left, sb);
        traversePostOrderHelper(node.right, sb);
        sb.append(node.data).append(" ");
    }

    private void traverseInOrderHelper(Node node, StringBuilder sb) {
        if (node == null) return;
        traverseInOrderHelper(node.left, sb);
        sb.append(node.data).append(" ");
        traverseInOrderHelper(node.right, sb);
    }
    
}
