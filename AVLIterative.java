/*
 * Author: Simon Lam
 * Part of CS435 Project 1.
 * Permission to post to Github granted by professor.
 * 
 * AVL Tree (Self-balancing)
 * Iterative Implementation
 */

// We will be using the same TreeNode class from before, but adding a "balance factor" value to the node
public class AVLIterative {
    private static int counter = 0;

    // Create a tree from an array
    public static TreeNode fromArray(int[] arr) {
        System.out.println("Starting with "+arr[0]);
        TreeNode root = new TreeNode(arr[0]);

        for(int i=1; i<arr.length; i++) {
            insertIter(root,arr[i]);
        }

        System.out.println("Creation done with "+counter+" traversals.");
        return root;
    }

    // Insert an item, iteratively
    public static void insertIter(TreeNode root, int val) {
        // Find the insertion point
        TreeNode curr = root;
        TreeNode lastCurr = null;
        while(curr != null) {
            lastCurr = curr;
            counter++;
            // traversal
            if(val < curr.val) {
                curr = curr.left;
            } else if(val > curr.val){
                curr = curr.right;
            } else {
                // we found it, cannot insert duplicates
                return;
            }
        }
        // backtrack slightly
        curr = lastCurr;

        // we insert the new node as a leaf
        // insert to the correct side of curr
        if(curr.val > val)
            curr.left = new TreeNode(val, curr);
        else
            curr.right = new TreeNode(val, curr);

        // we update all the values of heights and balance factors up the tree
        while(curr != null) {
            recalculate(curr);
            //System.out.println("curr bf "+curr.bf);
            // check if we need to pivot
            if(curr.bf > 1) {
                curr = rotateLeft(curr);
            } else if(curr.bf < -1) {
                curr = rotateRight(curr);
            }
            curr = curr.parent;
        }
    }

    // Delete an item, iteratively
    public static void deleteIter(TreeNode root, int val) {
        if(root == null) return;

        // find the node we want to delete
        TreeNode curr = root;
        while(curr != null) {
            counter++;
            // traversal
            if(val < curr.val) {
                curr = curr.left;
            } else if(val > curr.val){
                curr = curr.right;
            } else {
                // we found it, cannot insert duplicates
                break;
            }
        }
        
        // swap with nearest node until we hit the bottom of the tree
        while(curr.left != null || curr.right != null) {
            // find closest child
            TreeNode smaller = findPrevIter(curr,val);
            TreeNode larger = findNextIter(curr,val);
            // swap if possible
            if(smaller != null) {
                TreeNode.swap(curr,smaller);
                curr = smaller;
            } else if(larger != null){
                TreeNode.swap(curr,larger);
                curr = larger;
            }
        }
        
        // at the bottom of the tree, remove the node
        // if the parent exists
        TreeNode parent = null;
        if(curr.parent != null) {
            // remove the parent's attachment to the child
            parent = curr.parent;
            // java will handle the trash collection
            if(parent.right != null && parent.right.val == val) {
                parent.right = null;
            } else {
                parent.left = null;
            }
        }

        // we update all the values of heights and balance factors up the tree
        while(parent != null) {
            recalculate(parent);
            // check if we need to pivot
            if(parent.bf > 1) {
                parent = rotateLeft(curr);
            } else if(parent.bf < -1) {
                parent = rotateRight(curr);
            }
            parent = parent.parent;
        }
    }

    // Iterative way of finding the next largest value from 'val'
    public static TreeNode findNextIter(TreeNode root, int val) {
        if(root == null) return null;
        TreeNode curr = root;
        // Let's look to the right for a number larger than val
        while(curr.val <= val) {
            counter++;
            // If there's no larger value in our tree
            if(curr.right == null)
                return null;
            curr = curr.right;
        }

        // Let's look for the smallest number that still fits
        //  NOTE: This is not a recursive call!
        return findMinIter(curr);
    }

    // Iterative way of finding the next smallest value from 'val'
    public static TreeNode findPrevIter(TreeNode root, int val) {
        if(root == null) return null;
        TreeNode curr = root;
        // Let's look to the left for a number smaller than val
        while(curr.val >= val) {
            counter++;
            // If there's no smaller value that fits
            if(curr.left == null)
                return null;
            curr = curr.left;
        }

        // Let's return the largest number of that subtree
        //  NOTE: This is not a recursive call!
        return findMaxIter(curr);
    }

    // Iterative way of finding the minimum in a tree
    //  Finds the leftmost value
    public static TreeNode findMinIter(TreeNode root) {
        if(root == null) return root;
        TreeNode curr = root;
        while(curr.left != null) {
            counter++;
            curr = curr.left;
        }

        return curr;
    }

    // Iterative way of finding the maximum in a tree
    //  Finds the rightmost value
    public static TreeNode findMaxIter(TreeNode root) {
        if(root == null) return root;
        TreeNode curr = root;
        while(curr.right != null) {
            counter++;
            curr = curr.right;
        }

        return curr;
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
        TreeNode parent = root.parent;
        TreeNode right = root.right;
        TreeNode rightLeft = right.left;

        // check if we need to do a left-right rotation
        if(right.bf < 0) {
            right = rotateRight(right);
            rightLeft = right.left;
        }

        // firstly, update the parent (if any) to point at the right node
        if(parent == null) {
            right.parent = null;
        } else {
            // Find if current is the left or right child
            // Case: left child
            if(root == parent.left) {
                parent.left = right;
            } else {
                parent.right = right;
            }
            right.parent = parent;
        }

        // switch it so that:
        //  RL is the right child of N
        //  N is the left child of R
        root.right = rightLeft;
        right.left = root;
        // and update their respective parents
        if(root.right != null) {
            root.right.parent = root;
        }
        root.parent = right;

        // update heights and bfs of the nodes changed
        // curr
        recalculate(root);
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
        TreeNode parent = root.parent;
        TreeNode left = root.left;
        TreeNode leftRight = left.right;

        // Check if this has to be a right-left rotation
        if(left.bf > 0) {
            left = rotateLeft(left);
            leftRight = left.right;
        }

        // firstly, update the parent (if any) to point at the right node
        if(parent == null) {
            left.parent = null;
        } else {
            // Find if current is the left or right child
            // Case: left child
            if(root.val < parent.val) {
                parent.left = left;
            } else {
                parent.right = left;
            }
            left.parent = parent;
        }

        // Switch it so that:
        //  LR is the left child of N
        //  N is the right child of L
        root.left = leftRight;
        left.right = root;
        // and update their respective parents
        if(root.left != null) {
            root.left.parent = root;
        }
        root.parent = left;

        // update the values of the nodes
        // curr
        recalculate(root);
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
}