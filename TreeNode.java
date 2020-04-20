// A helper class for the implementations of BST
public class TreeNode {
    public TreeNode parent, left, right;
    public int val;
    // Additional values added so that these nodes may hold a balance factor
    public int bf, height; // balance factor, height
    
    // Constructor 
    TreeNode(int v) {
        val = v;
        parent = null;
        height = 0;
    }
    
    // Override constructor to store parent node
    TreeNode(int v, TreeNode p) {
        val = v;
        parent = p;
        height = 1;
    }
    
    // helper function to swap two nodes
    public static void swap(TreeNode a, TreeNode b) {
        int temp = a.val;
        a.val = b.val;
        b.val = temp;
    }
}