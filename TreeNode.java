// A helper class for the implementations of BST
public class TreeNode {
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