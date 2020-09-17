package LeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SumElements {
	public ArrayList<int[]> elementSum(int[] arr, int target){
        ArrayList<int[]> solution = new ArrayList<int[]>();
        int ref = 0;
        for (int i = 0; i < arr.length; i++){
            ref ++;
            for (int k = ref; k < arr.length; k++){
                if(arr[i] + arr[k] == target){
                    int[] temp = {arr[i], arr[k]};
                    solution.add(temp);
                }
            }
        }
        return solution;
    }
    public static void main(String[] args){
        int[] myArray = {2, 5, 7, 1, 9, 4, 0, 12, 6};
        int target = 7;
        SumElements my = new SumElements();
        ArrayList<int[]> results = my.elementSum(myArray, target);
        for (int[] a : results) {
        	System.out.println(Arrays.toString(a));
        }
        
    }
}
