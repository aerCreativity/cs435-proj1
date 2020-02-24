/*
 * Author: Simon Lam
 * Part of CS435 Project 1.
 * Permission to post to Github granted by professor.
 * 
 * AVL Tree (Self-balancing)
 * Recursive Implementation
 */

// We will be using the same TreeNode class from before, but adding a "balance factor" value to the node
public class AVLRecursive {
    // Insert a value, recursively
    public static void insertRec(TreeNode root, int val) {
        // We need to see if we pick left or right
        // Increase the count for the side we pass through, and see if we can insert
        if(root.val > val) {
            // Go left!
            if(root.left == null) {
                root.left = new TreeNode(val,root);
                return;
            }
            insertRec(root.left, val);
        } else if(root.val < val) {
            // Go right!
            if(root.right == null){
                root.right = new TreeNode(val, root);
                return;
            }
            insertRec(root.right, val);
        } else {
            // The node already exists...
            return;
        }
    }

    // Delete a value, recursively
    public static void deleteRec(TreeNode root, int val) {
        //
    }

    // Find the next greatest value
    public static TreeNode findNextRec(TreeNode root, int val) {
        return root;
    }

    // Find the next smallest value
    public static TreeNode findPrevRec(TreeNode root, int val) {
        return root;
    }

    // Find the smallest value, which should be the leftmost value
    public static TreeNode findMinRec(TreeNode root) {
        if(root.left == null)
            return root;

        return findMinRec(root.left);
    }

    // Find the largest value, which should be the rightmost value
    public static TreeNode findMaxRec(TreeNode root) {
        if(root.right == null)
            return root; 

        return findMaxRec(root.right);
    }

    // Helper that transforms an array into a tree
    public static TreeNode fromArray(int[] arr) {
        if(arr.length < 1) return null;
        TreeNode root = new TreeNode(arr[0]);

        // loop through, inserting all items
        for(int i=1; i<arr.length; i++) {
            insertRec(root,arr[i]);
        }

        return root;
    }

    // Helper that rebalances the tree, if need be.
    //  It works by going up the tree from a leaf, and then rotating based on the Balance Factor
    public static void rebalanceUpRec(TreeNode leaf) {
        int diff = leaf.countRight-leaf.countLeft;
        if(diff > 1){
            rotateLeft(leaf);
        } else if(diff < -1){
            rotateRight(leaf);
        }
        
        // recursive call to the parent, until we hit root
        if(leaf.parent != null) {
            rebalanceUpRec(leaf.parent);
        }
    }

    // Helper that rotates left about the input node
    public static TreeNode rotateLeft(TreeNode root) {
        // The important nodes to keep track of are:
        //  (N)  current node
        //  (P)  current node's parent (if any)
        //  (R)  curr's right child
        //  (RL) curr's right child's left child (if any)

        // if there is no right child, there is no rotation
        if(root == null || root.right == null) {
            return root;
        }

        // Grab references for each node so we can start moving pointers
        TreeNode curr = root;
        TreeNode parent = curr.parent;
        TreeNode right = curr.right;
        TreeNode rightLeft = right.left;

        // firstly, update the parent (if any) to point at the right node
        if(parent != null) {
            // Find if current is the left or right child
            // Case: left child
            if(curr.val < parent.val) {
                parent.left = right;
            } else {
                parent.right = right;
            }
        }
        right.parent = parent;

        // check if we need to do a left-right rotation
        if(right.countLeft >= right.countRight) {
            rotateRight(right);
        }

        // switch it so that:
        //  RL is the right child of N
        //  N is the left child of R
        curr.right = rightLeft;
        right.left = curr;
        // and update their respective parents
        if(curr.right != null) {
            curr.right.parent = curr;
        }
        curr.parent = right;

        // update the values of the nodes
        curr.countRight = right.countLeft;
        right.countLeft = curr.countRight+curr.countLeft+1;

        return right;
    }

    // Helper that rotates right about the input node
    public static TreeNode rotateRight(TreeNode root) {
        // The important nodes to keep track of are:
        //  (N)  current node
        //  (P)  current node's parent (if any)
        //  (L)  current node's left child
        //  (LR) current node's left child's right child

        // if there is no left child, we cannot rotate
        if(root == null || root.left == null) {
            return root;
        }

        // Grab references for each node so we can start moving pointers
        TreeNode curr = root;
        TreeNode parent = curr.parent;
        TreeNode left = curr.left;
        TreeNode leftRight = left.right;

        // firstly, update the parent (if any) to point at the right node
        if(parent != null) {
            // Find if current is the left or right child
            // Case: left child
            if(curr.val < parent.val) {
                parent.left = left;
            } else {
                parent.right = left;
            }
        }
        left.parent = parent;

        // Check if this has to be a right-left rotation
        if(left.countRight >= left.countLeft) {
            rotateLeft(left);
        }

        // Switch it so that:
        //  LR is the left child of N
        //  N is the right child of L
        curr.left = leftRight;
        left.right = curr;
        // and update their respective parents
        if(curr.left != null) {
            curr.left.parent = curr;
        }
        curr.parent = left;

        // update the values of the nodes
        curr.countLeft = left.countLeft;
        left.countRight = curr.countLeft+curr.countRight+1;

        return left;
    }
}