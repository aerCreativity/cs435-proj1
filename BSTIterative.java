/*
 * Author: Simon Lam
 * Part of CS435 Project 1.
 * Permission to post to Github granted by professor.
 * 
 * Binary Search Tree
 * Iterative Implementation
 */
import java.util.*;

/* Assumptions made:
 * -Anything within the base Java libraries is fair game to use.
 * -The binary search tree will only contain integer values.
 * -The binary search tree should have all of its functions implemented in a iterative format, if possible.
 * -Any helper functions should be made private.
 * -All edge cases should be addressed.
 * -Each function should be as optimized as possible.
 * -State the explicit time/space complexity outside of each function.
 * -One function should be included to handle converting an array into a tree.
 * -Any values used in the tree will be unique in the context of the tree.
 */

// Implementation of the Iterative binary search tree
public class BSTIterative {
    // Refer to "Driver.java" for examples
    private static int counter = 0; // counter for traversals, used for Q6

    // Function to change an unsorted integer array into a tree
    // Input array should have no duplicates
    public static TreeNode fromArray(int[] arr) {
        // Create the root with the first value
        TreeNode root = new TreeNode(arr[0]);

        // Insert every value from arr into this tree
        for(int i=1; i<arr.length; i++) {
            insertIter(root,arr[i]);
        }

        System.out.println("Creation done with "+counter+" traversals.");
        return root;
    }

    // Function to change a sorted integer array into a tree
    //  The array will be inputted as an inorder traversal
    //   index 0 will be root
    //   index 1,2 will be children of root
    //   etc.
    // Assumption: any array inputted will be a valid tree.
    //             arr.length>0
    public static TreeNode fromSortedArray(int[] arr) {
        // Create the root node from the base array
        TreeNode root = new TreeNode(arr[0]);
        // Create a queue of nodes which have not yet been checked for children
        //  and a queue of indeces which refer to these nodes.
        Queue<TreeNode> needsChildren = new LinkedList<>();
        Queue<Integer> indeces = new LinkedList<>();
        
        // Add the root to queue to start us off
        needsChildren.add(root);
        indeces.add(0);
        
        // While we have not travelled through the entirety of the array
        while(needsChildren.peek() != null) {
            // pop current node and current index
            TreeNode currNode = needsChildren.remove();
            int currIndex = indeces.remove();
            int leftIndex = currIndex*2+1;
            int rightIndex= currIndex*2+2;
            
            // check if each child is valid
            // if they're valid, create that child and add it to the queue
            if(leftIndex<arr.length) {
                TreeNode leftChild = new TreeNode(arr[leftIndex],currNode);
                currNode.left = leftChild;
                needsChildren.add(leftChild);
                indeces.add(leftIndex);
            }
            if(rightIndex<arr.length) {
                TreeNode rightChild = new TreeNode(arr[rightIndex],currNode);
                currNode.right = rightChild;
                needsChildren.add(rightChild);
                indeces.add(rightIndex);
            }
            
            // short circuit if the indeces are out of range of the array
            if(rightIndex >= arr.length)
                break;
        }
        
        // return the root of the created tree
        return root;
    }

    // From an unsorted array into a tree
    //  print this using inorder traversal to make a list of sorted elements
    public static TreeNode fromUnsorted(Integer[] arr) {
        TreeNode root = new TreeNode(arr[0]);
        for(int i=1; i<arr.length; i++) {
            insertIter(root,arr[i]);
        }

        return root;
    }

    // Iterative method of inserting an item into a BST
    public static void insertIter(TreeNode root, int val) {
        if(root == null) return;
        
        TreeNode curr = root;
        // Traverse until we find an appropriate empty spot
        while(curr.left != null || curr.right != null) {
            counter++;
            if(curr.val < val && curr.right != null) {
                curr = curr.right;
            } else if(curr.val > val && curr.left != null) {
                curr = curr.left;
            } else {
                break;
            }
        }

        // Keep it unique!
        if(curr.val == val)
            return;

        // Insert new node in appropriate location
        TreeNode newNode = new TreeNode(val, curr);
        if(curr.val < val) {
            curr.right = newNode;
        } else {
            curr.left = newNode;
        }
    }

    // Iterative method of removing an item from a BST
    //  we swap the node we want to delete with the closest node (in value) and continue swapping until it has no children.
    public static void deleteIter(TreeNode root, int val) {
        if(root == null) return;

        // find the node we want to delete
        TreeNode curr = searchValIter(root,val);
        if(curr == null) return;
        
        // swap with nearest node until we hit the bottom of the tree
        while(curr.left != null || curr.right != null) {
            // find closest child
            TreeNode smaller = findPrevIter(curr,val);
            TreeNode larger = findNextIter(curr,val);
            // swap if possible
            if(smaller != null) {
                swap(curr,smaller);
                curr = smaller;
            } else if(larger != null){
                swap(curr,larger);
                curr = larger;
            }
        }
        
        // at the bottom of the tree, remove the node
        // if the parent exists
        if(curr.parent != null) {
            // remove the parent's attachment to the child
            // java will handle the trash collection
            TreeNode parent = curr.parent;
            if(parent.right != null && parent.right.val == val) {
                parent.right = null;
            } else {
                parent.left = null;
            }
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

    // helper function to find a value in the tree
    // returns null if not found
    private static TreeNode searchValIter(TreeNode root, int val) {
        if(root == null)
            return null;
        
        TreeNode curr = root;
        while(curr.val != val) {
            if(curr.val < val && curr.right != null) {
                curr = curr.right;
            } else if(curr.val > val && curr.left != null) {
                curr = curr.left;
            } else {
                return null;
            }
        }
        return curr;
    }

    // helper function to swap two nodes' values
    // it effectively swaps both nodes without using pointer shenanigans
    private static void swap(TreeNode a, TreeNode b) {
        int temp = b.val;
        b.val = a.val;
        a.val = temp;
    }

    // helper function to print the values in the tree
    public static void printArrIter(TreeNode root) {
        Queue<TreeNode> toPrint = new LinkedList<>();
        toPrint.add(root);

        while(toPrint.peek() != null) {
            TreeNode temp = toPrint.poll();
            if(temp.left != null)
                toPrint.add(temp.left);
            if(temp.right != null)
               toPrint.add(temp.right);
            System.out.print(temp.val+" ");
        }
    }
}