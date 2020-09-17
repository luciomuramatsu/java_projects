package LeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class queue {
	  
	public static int[][] reconstructQueue(int[][] people) {
		Arrays.sort(people, new Comparator<int[]>() {
		      @Override
		      public int compare(int[] o1, int[] o2) {
		        // if the heights are equal, compare k-values
		    	return o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0];
		      }
		});
		
		List<int[]> output = new ArrayList<>();
		for(int[] p : people){
		    output.add(p[1], p);
		}
		return output.toArray(new int[people.length][2]);
	}
	public static void main(String[] args) {
		int[][] myList = {{6,0}, {7,1}, {5,4}, {4,2}, {7,0}, {6,2}};
		int[][] output = reconstructQueue(myList);
		System.out.println(Arrays.deepToString(output));
	}
	
}
