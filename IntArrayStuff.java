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
    public static int[] getRandomArray(int n) {
        int[] arr = new int[n];
        HashSet<Integer> seenNum = new HashSet<>();
        for(int i=0; i<n; i++) {
            int randInt = (int)(Math.random()*n*n);
            while(seenNum.contains(randInt)) {
                randInt = (int)(Math.random()*n*n);
            }
            arr[i] = randInt;
            seenNum.add(randInt);
        }
        return arr;
    }

    public static int[] getSortedArray(int n) {
        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
            arr[i] = n-i;
        }
        return arr;
    }
}