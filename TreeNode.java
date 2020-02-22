// A helper class for the implementations of BST
public class TreeNode {
    public TreeNode parent, left, right;
    public int val;
    // Additional values added so that these nodes may hold a balance factor
    public int countLeft,countRight;
    
    // Constructor 
    TreeNode(int v) {
        val = v;
    }
    
    // Override constructor to store parent node
    TreeNode(int v, TreeNode p) {
        val = v;
        parent = p;
    }

    // Get the balance factor of the node
    public int getBF() {
        return countRight-countLeft;
    }
}