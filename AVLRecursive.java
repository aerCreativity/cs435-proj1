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
                recalculate(root);
                return;
            }
            insertRec(root.left, val);
        } else if(root.val < val) {
            // Go right!
            if(root.right == null){
                root.right = new TreeNode(val, root);
                recalculate(root);
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
        if(root == null)
            return;
        
        // In the case that our root is not the value we want deleted.
        if(root.val != val) {
            if(root.val < val) {
                deleteRec(root.right, val);
            } else {
                deleteRec(root.left, val);
            }
            return;
        }

        // In the case that the root has 1 or 2 children
        // now, we have a tree where we want to delete the root.
        //  we want to swap the root with the prev/next value and then remove it
        if(root.left != null || root.right != null) {
            TreeNode toSwap = null;
            if(findPrevRec(root,val).val != val) {
                toSwap = searchValRec(root,findPrevRec(root,val));
            } else if(findNextRec(root,val).val != val) {
                toSwap = searchValRec(root,findNextRec(root,val));
            }
            swap(root,toSwap);
            deleteRec(toSwap, val);
            return;
        }

        // In the case that our root has no children and should be deleted.
        // clean up the parent of the node (if any)
        if(root.parent != null) {
            TreeNode curr = root.parent;
            if(curr.right != null && curr.right.val == val) {
                curr.right = null;
            } else {
                curr.left = null;
            }
            root.parent = null;
        }
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
        if(leaf.bf > 1){
            rotateLeft(leaf);
        } else if(leaf.bf < -1){
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
        if(right.bf < 0) {
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

        // update heights and bfs of the nodes changed
        // curr
        recalculate(curr);
        // right
        recalculate(right);

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
        if(left.bf > 0) {
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
        // curr
        recalculate(curr);
        // left
        recalculate(left);

        return left;
    }

    // Helper that recalculated balance for a TreeNode
    private static void recalculate(TreeNode root) {
        if(root.left != null && root.right != null) {
            // we have two children
            root.bf = root.right.height - root.left.height;
            if(root.bf > 0) {
                // right side is larger, use right height
                root.height = root.right.height + 1;
            } else {
                // left side is larger
                root.height = root.left.height + 1;
            }
        } else if(root.left != null) {
            // we only have a left child
            root.bf = 0 - root.left.height;
            root.height = root.left.height + 1;
        } else if(root.right != null) {
            // we only have a right child
            root.bf = root.right.height;
            root.height = root.right.height + 1;
        } else {
            // we have no child
            root.bf = 0;
            root.height = 1;
        }
    }

    // helper function to find a value in the tree
    // returns null if not found
    private static TreeNode searchValRec(TreeNode root, TreeNode search) {
        if(root == null)
            return null;

        // if we found it, return a reference
        if(root.val == search.val)
            return root;
        
        // otherwise, search the relevant subtree
        if(root.val > search.val) {
            return searchValRec(root.left, search);
        } else{
            return searchValRec(root.right, search);
        }
    }
    
    // Helper to swap two nodes
    private static void swap(TreeNode a, TreeNode b) {
        int temp = a.val;
        a.val = b.val;
        b.val = temp;
    }
}