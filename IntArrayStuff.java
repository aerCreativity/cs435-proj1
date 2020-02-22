/*
 * Author: Simon Lam
 * Part of CS435 Project 1.
 * Permission to post to Github granted by professor.
 * 
 * Problem number 3 of project 1
 */
import java.lang.Math;
import java.util.HashSet;

public class IntArrayStuff {
    // Generates an array of length 'n' with unique positive integers
    public static int[] getRandomArray(int n) {
        int[] arr = new int[n];
        HashSet<Integer> seenNum = new HashSet<>();
        for(int i=0; i<n; i++) {
            // Generate random number, and check if that number has been seen
            int randInt = (int)(Math.random()*n*n);
            while(seenNum.contains(randInt)) {
                randInt = (int)(Math.random()*n*n);
            }
            arr[i] = randInt;
            seenNum.add(randInt);
        }
        return arr;
    }

    // Generates a descending array of size 'n' from 'n' to '1'
    public static int[] getSortedArray(int n) {
        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
            arr[i] = n-i;
        }
        return arr;
    }
}