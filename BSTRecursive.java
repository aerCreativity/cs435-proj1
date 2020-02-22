/*
 * Author: Simon Lam
 * Part of CS435 Project 1.
 * Permission to post to Github granted by professor.
 * 
 * Binary Search Tree
 * Recursive Implementation
 */
import java.util.*;

/* Assumptions made:
 * -Anything within the base Java libraries is fair game to use.
 * -The binary search tree will only contain integer values.
 * -The binary search tree should have all of its functions implemented in a recursive format, if possible.
 * -Any helper functions should be made private.
 * -All edge cases should be addressed.
 * -Each function should be as optimized as possible.
 * -State the explicit time/space complexity outside of each function.
 * -One function should be included to handle converting an array into a tree.
 * -Any values used in the tree will be unique in the context of the tree.
 */

// Implementation of the Recursive binary search tree
public class BSTRecursive {
    // Main method used to create sample input and output
    public static void main(String[] args) {
        // begin with a balanced array of [1-15]
        Integer[] example = {8,4,12,2,6,10,14,1,3,5,7,9,11,13,15};
        TreeNode exRoot = fromArray(example);
        printArrRec(exRoot);
        System.out.print("\n");

        // Add values from 16-32
        for(int i=16; i<32; i++) {
            insertRec(exRoot,i);
        }
        printArrRec(exRoot);
        System.out.print("\n");

        // Remove all odd values
        for(int i=1; i<32; i+=2) {
            deleteRec(exRoot,i);
        }
        printArrRec(exRoot);
        System.out.print("\n");

        // Find and print the max and min
        int min = findMinRec(exRoot).val;
        int max = findMaxRec(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Print the value larger than 12
        System.out.println(findNextRec(exRoot,12));
        // Print the value less than 12
        System.out.println(findPrevRec(exRoot,12));
        // Print the value greater than max
        System.out.println(findNextRec(exRoot,max));
        // Print the value smaller than min
        System.out.println(findPrevRec(exRoot,min));
    }

    // Function to change an integer array into a tree
    //  The array will be inputted as an inorder traversal
    //   index 0 will be root
    //   index 1,2 will be children of root
    //   etc.
    // Assumption: any array inputted will be a valid tree.
    //             arr.length>0
    public static TreeNode fromArray(Integer[] arr) {
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
            if(leftIndex<arr.length && arr[leftIndex]!=null) {
                TreeNode leftChild = new TreeNode(arr[leftIndex],currNode);
                currNode.left = leftChild;
                needsChildren.add(leftChild);
                indeces.add(leftIndex);
            }
            if(rightIndex<arr.length && arr[rightIndex]!=null) {
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

    // Recursive function to insert a value into a tree.
    // O(s) where s is size of the tree
    public static void insertRec(TreeNode root, int val) {
        if(root == null)
            return;

        // Base Case: this root node has no value in the appropriate position beneath it.
        if(root.val > val && root.left == null) {
            TreeNode child = new TreeNode(val, root);
            root.left = child;
        } else if(root.val < val && root.right == null) {
            TreeNode child = new TreeNode(val, root);
            root.right = child;
        }

        // Otherwise, select a side where the next iteration should take place
        if(root.val > val) {
            insertRec(root.left, val);
        } else {
            insertRec(root.right, val);
        }
    }

    // Recursive function to delete a value from a tree.
    // O(s) where s is the size of the tree
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
            if(findPrevRec(root,val) != val) {
                toSwap = searchVal(root,findPrevRec(root,val));
            } else if(findNextRec(root,val) != val) {
                toSwap = searchVal(root,findNextRec(root,val));
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

    // Recursive function to find the next largest value within a tree.
    // no assumption on whether val is in the tree
    // if no greater value can be found, val is returned
    // O(s) where s is the size of the tree
    public static int findNextRec(TreeNode root, int val) {
        // base case
        if(root == null)
            return val;
        
        // check if root is larger or smaller than value
        if(root.val > val) {
            // we compare between root & left subtree's result
            int leftResult = findNextRec(root.left, val);
            // if theres a closer value in the left subtree, return it
            if(leftResult == val) {
                return root.val;
            } else {
                return leftResult;
            }
        // if we are at val, or if val is to the immediate right
        //  or if we see a value larger than val
        } else if(root.val == val || (root.right != null && root.right.val >= val)) {
            // we want to go through the right subtree
            return findNextRec(root.right, val);
        } else {
            // last case where val is larger than root, root.left, and root.right
            return val;
        }
    }

    // Recursive function to find the next smallest value within a tree.
    //  no assumption on whether val exists in tree
    //  if no lesser value can be found, val is returned
    // O(s) where s is the size of the tree
    public static int findPrevRec(TreeNode root, int val) {
        // base case
        if(root == null) 
            return val;

        // check if root is larger or smaller than value
        if(root.val < val) {
            // we compare between root & right subtree's result
            int rightResult = findPrevRec(root.right, val);
            // if there's a closer value in right subtree, return it
            if(rightResult == val) {
                return root.val;
            } else {
                return rightResult;
            }
        // if we are at val, or if val is to the immediate left
        //  or if we see a value larger than val
        } else if(root.val == val || (root.left != null && root.left.val <= val)) {
            // we want to go through the left subtree
            return findPrevRec(root.left, val);
        } else {
            // last case where val is smaller than root, root.left, and root.right
            return val;
        }
    }

    // Recursive function to find the smallest (leftmost) value within a tree.
    // O(s) where s is the size of the tree
    public static TreeNode findMinRec(TreeNode root) {
        // base case
        if(root.left == null)
            return root;
        
        // otherwise traverse through left subtree
        return findMinRec(root.left);
    }

    //Recursive function to find the largest (rightmost) value within a tree.
    // O(s) where s is the size of the tree
    public static TreeNode findMaxRec(TreeNode root) {
        // base case
        if(root.right == null)
            return root;

        // otherwise traverse through right subtree
        return findMaxRec(root.right);
    }

    // helper function to find a value in the tree
    // returns null if not found
    private static TreeNode searchVal(TreeNode root, int val) {
        if(root == null)
            return null;

        // if we found it, return a reference
        if(root.val == val)
            return root;
        
        // otherwise, search the relevant subtree
        if(root.val > val) {
            return(searchVal(root.left, val));
        } else{
            return(searchVal(root.right, val));
        }
    }

    // helper function to swap two nodes' values
    // it effectively swaps both nodes without using pointer shenanigans
    private static void swap(TreeNode a, TreeNode b) {
        int temp = b.val;
        b.val = a.val;
        a.val = temp;
    }

    // recursive function to print all values in the tree
    //  uses preorder traversal
    private static void printArrRec(TreeNode root) {
        // base case
        if(root == null)
            return;
        
        // printing
        System.out.print(root.val+"(");
        printArrRec(root.left);
        System.out.print(" ");
        printArrRec(root.right);
        System.out.print(")");
    }
}

// Helper class to encapsulate Tree Nodes
class TreeNode {
    public TreeNode parent, left, right;
    public int val;
    
    // Constructor 
    TreeNode(int v) {
        val = v;
    }
    
    // Override constructor to store parent node
    TreeNode(int v, TreeNode p) {
        val = v;
        parent = p;
    }
}