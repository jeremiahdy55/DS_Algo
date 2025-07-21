package dsalgo.datastructures;

import java.util.Comparator;

public class AVLTree<T> {

    public AVLNode root;
    public Comparator<T> comparator;

    public AVLTree(Comparator<T> comparator) {
        this.root = null;
        this.comparator = comparator;
    }
    
    public class AVLNode {
        public T val;
        private int height;
        public AVLNode left;
        public AVLNode right;
        
        public AVLNode() {}
        public AVLNode(T val) {
            this.val = val;
        }
        public int getHeight() {
            return this.height;
        }
        public void setHeight(int height) {
            this.height = height;
        }
    }
    
    
    public void insert(T data) {
        this.root = insertAndBalance(this.root, data);
    }

    // recursive helper insert function
    private AVLNode insertAndBalance(AVLNode current, T data) {
        if (current == null) {
            return new AVLNode(data);
        }

        // traverse the tree until we find where we can put the node
        if (compareValues(data, current.val) < 0) {
            current.left = insertAndBalance(current.left, data);
        } else if (compareValues(data, current.val) >= 0) {
            current.right = insertAndBalance(current.right, data);
        }

        // update height
        updateHeight(current);

        // ================= rebalance if necessary ===================
        current = rebalance(current);
        return current;
    }

    public void delete(T target) {
        this.root = deleteAndBalance(this.root, target);
    }

    // recursive helper delete function
    private AVLNode deleteAndBalance(AVLNode current, T target) {
        if (current == null) return null;
        int compareResult = compareValues(target, current.val);

        // Delete using BST algorithm
        if (compareResult < 0) {
            current.left = deleteAndBalance(current.left, target);
        } else if (compareResult > 0) {
            current.right = deleteAndBalance(current.right, target);
        } else {
            // Match was found!
            if (current.left == null || current.right == null) {
                // Case 1: zero children
                // Case 2: one child
                // Simply delete the node and assign that child to the current node's place
                current = (current.left != null) ? current.left : current.right;
            } else {
                // Case 3: two children
                // Find the successor node, which is the left-most child in the right subtree of current
                AVLNode successor = current.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                // reassign current's data, then rebuild the tree with another recursive call
                current.val = successor.val;
                // NOTE: just like in BST, this recursive call is only on the right subtree, so it will skip current
                //       and only delete the successor node we just found
                current.right = deleteAndBalance(current.right, successor.val);
            }
        }

        // Second null-reference check just in case the match found had 0 children
        // we can't run updateHeight() and rebalance() on {null}
        if (current == null) return null;

        // Update the height
        updateHeight(current);

        // ================= rebalance if necessary ===================
        current = rebalance(current);
        return current;
    }

    public AVLNode search(T target) {
        AVLNode node = search(this.root, target);
        if (node == null) {
            System.out.println("No match found");
        } else {
            System.out.println("Match found!");
        }
        return node;
    }

    private AVLNode search(AVLNode current, T target) {
        if (current == null) {
            return null;
        }
        int compareResult = compareValues(target, current.val);
        if (compareResult == 0) {
            return current;
        } else if (compareResult < 0) {
            return search(current.left, target);
        } else {
            return search(current.right, target);
        }
    }

    public void printInOrder() {
        System.out.print("AVLTree: [ ");
        printInOrder(this.root);
        System.out.println("]");
    }

    // ==== REBALANCING SUBTREE ====
    private AVLNode rebalance(AVLNode current) {
        // get balance factor for the current node (the node at the top of the subtree we're currently inspecting)
        int balance = getBalanceFactor(current);

        // when current is left-heavy
        if (balance > 1) {
            // get the balance factor of the left-child
            int leftChildBalance = getBalanceFactor(current.left);

            if (leftChildBalance >= 0) {
                // the left child is either left-heavy or balanced
                // perform a SINGLE right-rotation
                return rotateRight(current);
            } else {
                // the left child is right-heavy
                // perform a DOUBLE pivot
                // left-child of current is overwritten by the resulting subtree of a left-rotation at current.left
                // then we can perform the right-rotation to balance current
                current.left = rotateLeft(current.left);
                return rotateRight(current);
            }
        }

        // when current is right-heavy
        if (balance < -1) {
            // get the balance factor of the right-child
            int rightChildBalance = getBalanceFactor(current.right);

            if (rightChildBalance >= 0) {
                // the right child is either right-heavy or balanced
                // perform a SINGLE left-rotation
                return rotateLeft(current);
            } else {
                // the right child is left-heavy
                // perform a DOUBLE pivot
                // right-child of current is overwritten by the resulting subtree of a right-rotation at current.right
                // then we can perform the left-rotation to balance current
                current.right = rotateRight(current.right);
                return rotateLeft(current);
            }
        }

        // return current if no balancing is required
        return current;
    }

    // ==== ROTATE HELPER METHODS ====
    // think of balancing scales (move weight/nodes from the heavier end to the other)
    // Visualize the subtree that is being rotated as:
    //         x
    //      /     \
    //    y         z
    // where y and z may or may not have children, if not then nodesBetween  will be null

    // when the subtree is right-heavy (balance factor < -1) on x
    // where x is the root of the subtree,
    // then the right child (z) becomes the parent of the subtree
    // x becomes z's new left child
    // z's previous left subtree is now x's right subtree/children
    // so the children still fit the BST model
    private AVLNode rotateLeft(AVLNode x) {
        // let z be the right child of x
        AVLNode z = x.right;
        // let nodesbetween be the LEFT-children of z, such that x < (all AVLNode values in nodesBetween) <= z
        AVLNode nodesBetween = z.left; // can also be null
    
        // Perform rotation
        z.left = x;
        x.right = nodesBetween;

        // Update heights
        updateHeight(x);
        updateHeight(z);
    
        return z; // since z is the new root of this subtree, return z
    }

    // when the subtree is left-heavy (balance factor > +1) on x
    // where x is the root of the subtree,
    // then the left child (y) becomes the parent of the subtree
    // x becomes y's new right child
    // y's previous right subtree is now x's left subtree/children
    // so the children still fit the BST model
    private AVLNode rotateRight(AVLNode x) {
        // let y be the left child of x
        AVLNode y = x.left;
        // let nodesBetween be the RIGHT-children of y, such that y < (all AVLNode values in nodesBetween) <= x
        AVLNode nodesBetween = y.right;
    
        // Perform rotation
        y.right = x;
        x.left = nodesBetween;
    
        // Update heights
        updateHeight(x);
        updateHeight(y);

        return y; // since y is the new root of this subtree, return y
    }

    // ==== Other helper methods ====
    private int getBalanceFactor(AVLNode node) {
        int leftHeight = node.left != null ? node.left.getHeight() : 0;
        int rightHeight = node.right != null ? node.right.getHeight() : 0;
        return leftHeight - rightHeight;
    }

    private int getMaxChildSubtreeHeight(AVLNode node) {
        int leftHeight = node.left != null ? node.left.getHeight() : 0;
        int rightHeight = node.right != null ? node.right.getHeight() : 0;
        return Math.max(leftHeight, rightHeight);
    }

    private void updateHeight(AVLNode node) {
        node.setHeight(1 + getMaxChildSubtreeHeight(node));
    }

    private int compareValues (T x, T y) {
        return this.comparator.compare(x,y);
    }
    
    private void printInOrder(AVLNode node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.print(node.val + " ");
        printInOrder(node.right);
    }

}
