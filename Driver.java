/* This class is meant to run examples of each problem.
 */

 public class Driver {
    // Main method which runs the tests
    public static void main(String[] args) {
        // define size "n"
        int n = 10;

        // Create example array
        int[] example = IntArrayStuff.getRandomArray(n);
        System.out.println("Sample Array (Unsorted)");
        printArr(example);

        // Sort it.
        int[] sorted = IntArrayStuff.getSortedArray(n);
        // Print test array
        System.out.println("Sample Array (Sorted)");
        printArr(sorted);

        // Problem 1:
        // Run test on BST Recursive
        System.out.println("+++++++++++++++++++++");
        System.out.println("Test on BST Recursive");
        System.out.println("+++++++++++++++++++++");
        testBSTRecursive(example);
        // Run test on BST Iterative
        System.out.println("+++++++++++++++++++++");
        System.out.println("Test on BST Iterative");
        System.out.println("+++++++++++++++++++++");
        testBSTIterative(example);
        
    }
    
    
    // Testing code for BST Iterative
    public static void testBSTRecursive(int[] example) {
        // begin with a balanced array of [1-15]
        TreeNode exRoot = BSTRecursive.fromArray(example);
        int max, min;
        
        BSTIterative.printArrIter(exRoot);
        System.out.print("\n");
        
        // Find and print the max and min
        min = BSTRecursive.findMinRec(exRoot).val;
        max = BSTRecursive.findMaxRec(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Add values from 16-32
        for(int i=16; i<32; i++) {
            BSTRecursive.insertRec(exRoot,i);
        }
        BSTRecursive.printArrRec(exRoot);
        System.out.print("\n");
        
        // Find and print the max and min
        min = BSTRecursive.findMinRec(exRoot).val;
        max = BSTRecursive.findMaxRec(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Remove all odd or even values depending on min/max
        for(int i=min; i<max; i+=2) {
            BSTRecursive.deleteRec(exRoot,i);
        }
        BSTRecursive.printArrRec(exRoot);
        System.out.print("\n");

        // Find and print the max and min
        min = BSTRecursive.findMinRec(exRoot).val;
        max = BSTRecursive.findMaxRec(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Print the values greater and less than max and min
        System.out.println(BSTRecursive.findNextRec(exRoot,max));
        System.out.println(BSTRecursive.findPrevRec(exRoot,max));
        System.out.println(BSTRecursive.findNextRec(exRoot,min));
        System.out.println(BSTRecursive.findPrevRec(exRoot,min));
    }

    // Testing code for BST Iterative
    public static void testBSTIterative(int[] example) {
        // begin with a balanced array of [1-15]
        TreeNode exRoot = BSTIterative.fromArray(example);
        int max, min;
        
        BSTIterative.printArrIter(exRoot);
        System.out.print("\n");
        
        // Find and print the max and min
        min = BSTIterative.findMinIter(exRoot).val;
        max = BSTIterative.findMaxIter(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Add values from 16-32
        for(int i=16; i<32; i++) {
            BSTIterative.insertIter(exRoot,i);
        }
        BSTIterative.printArrIter(exRoot);
        System.out.print("\n");
        
        // Find and print the max and min
        min = BSTIterative.findMinIter(exRoot).val;
        max = BSTIterative.findMaxIter(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Remove all odd/even values depending on min
        for(int i=min; i<max; i+=2) {
            BSTIterative.deleteIter(exRoot,i);
        }
        BSTIterative.printArrIter(exRoot);
        System.out.print("\n");

        // Find and print the max and min
        min = BSTIterative.findMinIter(exRoot).val;
        max = BSTIterative.findMaxIter(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Print the values greater and less than max and min
        System.out.println(BSTIterative.findNextIter(exRoot,max));
        System.out.println(BSTIterative.findPrevIter(exRoot,max));
        System.out.println(BSTIterative.findNextIter(exRoot,min));
        System.out.println(BSTIterative.findPrevIter(exRoot,min));
    }

    // Helper to print an array of integers
    private static void printArr(int[] arr) {
        for(int i : arr) {
            System.out.print(i+" ");
        }
        System.out.println();
    }
 }