package main.dartanman.duels.utils;

/**
 * InsertionSort - Utility class to sort an integer array.
 * @author Austin Dart (Dartanman)
 */
public class InsertionSort {
	
	
	/**
	 * Returns the given integer array, sorted from least to greatest
	 * @param arr
	 *   The integer array to sort
	 * @return
	 *   The sorted integer array
	 */
    public static int[] sort(int[] arr)
    {
        int length = arr.length;
        for (int i = 1; i < length; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            
            arr[j + 1] = key;
        }
        return arr;
    }
}