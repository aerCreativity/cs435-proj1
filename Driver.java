/* This class is meant to run examples of each problem.
 */

 //import java.util.concurrent.TimeUnit;

 public class Driver {
    // Main method which runs the tests
    public static void main(String[] args) {
        // define size "n" of input arrays
        int n = 10000;
        long startTime, newTime;

        // Problem 5 & 6:
        // Creating the large input arrays
        int[] arrRand = IntArrayStuff.getRandomArray(n);
        int[] arrSort = IntArrayStuff.getSortedArray(n);

        //printArr(arrRand);

        // for random arr of input
        // recursive functions (commented out because of problem number 6)
        //TreeNode recBST = BSTRecursive.fromArray(arrRand);
        //TreeNode recAVL = AVLRecursive.fromArray(arrRand);

        // iterative functions
        System.out.println("For n = "+n);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Timing Iterative Implementation");
        System.out.println("on Random Array");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        startTime = System.nanoTime();
        TreeNode itBST = BSTIterative.fromArray(arrRand);
        newTime = System.nanoTime();
        System.out.println("[BST] Time Elapsed in nanoseconds: "+(newTime-startTime));
        startTime = System.nanoTime();
        TreeNode itAVL = AVLIterative.fromArray(arrRand);
        newTime = System.nanoTime();
        System.out.println("[AVL] Time Elapsed in nanoseconds: "+(newTime-startTime));
        
        // iterative functions
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Timing Iterative Implementation");
        System.out.println("on Sorted Array");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        startTime = System.nanoTime();
        itBST = BSTIterative.fromArray(arrSort);
        newTime = System.nanoTime();
        System.out.println("[BST] Time Elapsed in nanoseconds: "+(newTime-startTime));
        startTime = System.nanoTime();
        itAVL = AVLIterative.fromArray(arrSort);
        newTime = System.nanoTime();
        System.out.println("[AVL] Time Elapsed in nanoseconds: "+(newTime-startTime));
        
        
        /*
        // Create example array to test problems 1 and 4
        int[] example = IntArrayStuff.getRandomArray(n);
        System.out.println("Sample Array (Unsorted)");
        printArr(example);

        // Give a sorted, descending array.
        int[] sorted = IntArrayStuff.getSortedArray(n);
        // Print test array
        System.out.println("Sample Array (Sorted)");
        printArr(sorted);

        // Problem 4:
        // Run test on AVL Recursive
        System.out.println("+++++++++++++++++++++");
        System.out.println("Test on AVL Recursive");
        System.out.println("+++++++++++++++++++++");
        testAVLRecursive(example);
        // Run test on AVL Iterative
        System.out.println("+++++++++++++++++++++");
        System.out.println("Test on AVL Iterative");
        System.out.println("+++++++++++++++++++++");
        testAVLIterative(example);

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
        */
    }
    
    
    // Testing code for BST Recursive
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

    // Testing code for AVL Recursive
    public static void testAVLRecursive(int[] example) {
        // begin with a balanced array of [1-15]
        TreeNode exRoot = AVLRecursive.fromArray(example);
        int max, min;
        
        System.out.print("\n");
        
        // Find and print the max and min
        min = AVLRecursive.findMinRec(exRoot).val;
        max = AVLRecursive.findMaxRec(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Add values from 16-32
        for(int i=16; i<32; i++) {
            AVLRecursive.insertRec(exRoot,i);
        }
        System.out.print("\n");
        
        // Find and print the max and min
        min = AVLRecursive.findMinRec(exRoot).val;
        max = AVLRecursive.findMaxRec(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Remove all odd or even values depending on min/max
        for(int i=min; i<max; i+=2) {
            AVLRecursive.deleteRec(exRoot,i);
        }
        System.out.print("\n");

        // Find and print the max and min
        min = AVLRecursive.findMinRec(exRoot).val;
        max = AVLRecursive.findMaxRec(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Print the values greater and less than max and min
        System.out.println(AVLRecursive.findNextRec(exRoot,max));
        System.out.println(AVLRecursive.findPrevRec(exRoot,max));
        System.out.println(AVLRecursive.findNextRec(exRoot,min));
        System.out.println(AVLRecursive.findPrevRec(exRoot,min));
    }

    // Testing code for AVL Iterative
    public static void testAVLIterative(int[] example) {
        // begin with a balanced array of [1-15]
        TreeNode exRoot = AVLIterative.fromArray(example);
        int max, min;
        
        System.out.print("\n");
        
        // Find and print the max and min
        min = AVLIterative.findMinIter(exRoot).val;
        max = AVLIterative.findMaxIter(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Add values from 16-32
        for(int i=16; i<32; i++) {
            AVLIterative.insertIter(exRoot,i);
        }
        System.out.print("\n");
        
        // Find and print the max and min
        min = AVLIterative.findMinIter(exRoot).val;
        max = AVLIterative.findMaxIter(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Remove all odd/even values depending on min
        for(int i=min; i<max; i+=2) {
            AVLIterative.deleteIter(exRoot,i);
        }
        System.out.print("\n");

        // Find and print the max and min
        min = AVLIterative.findMinIter(exRoot).val;
        max = AVLIterative.findMaxIter(exRoot).val;
        System.out.println("Max: "+max+", Min: "+min);

        // Print the values greater and less than max and min
        System.out.println(AVLIterative.findNextIter(exRoot,max));
        System.out.println(AVLIterative.findPrevIter(exRoot,max));
        System.out.println(AVLIterative.findNextIter(exRoot,min));
        System.out.println(AVLIterative.findPrevIter(exRoot,min));
    }

    // Helper to print an array of integers
    private static void printArr(int[] arr) {
        for(int i : arr) {
            System.out.print(i+" ");
        }
        System.out.println();
    }
 }