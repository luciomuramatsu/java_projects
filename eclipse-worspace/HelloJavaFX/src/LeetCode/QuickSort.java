package LeetCode;

import java.util.Arrays;

public class QuickSort {
	
	private int[] tempArray;
	private int pivotIndex;
	private boolean order;
	
	public int[] sort(int[] nums) {
		
		if(nums == null || nums.length == 0) {
			throw new IllegalArgumentException("input null or size 0");
		}
		order = true;
		tempArray = nums;
		quickSort(0, tempArray.length-1);
		return tempArray;
	}
	
	public int[] reverseSort(int[] nums) {
		
		if(nums == null || nums.length == 0) {
			throw new IllegalArgumentException("input null or size 0");
		}
		order = false;
		tempArray = nums;
		quickSort(0, tempArray.length-1);
		return tempArray;
	}
	
	private void quickSort(int begin, int end) {
		if (begin >= end) return;
		pivotIndex = (end - begin) / 2 + begin;
		swap(pivotIndex, end);
		if(order) {
			partition(begin, end);
		} else {
			reversePartition(begin, end);
		}
		quickSort(begin, pivotIndex -1);
		quickSort(pivotIndex + 1, end);
	}
		
	private void partition(int begin, int end) {
		
		int elementPivot = tempArray[end];
		pivotIndex = begin;
		
		for (int currIndex = begin; currIndex < end; currIndex++) {
			if (tempArray[currIndex] < elementPivot) {
				swap(pivotIndex, currIndex);
				pivotIndex++;
			}
		}
		swap(pivotIndex, end);		
	}
	
	private void reversePartition(int begin, int end) {
		
		int elementPivot = tempArray[end];
		pivotIndex = begin;
		
		for (int currIndex = begin; currIndex < end; currIndex++) {
			if (tempArray[currIndex] > elementPivot) {
				swap(pivotIndex, currIndex);
				pivotIndex++;
			}
		}
		swap(pivotIndex, end);		
	}
	
	private void swap(int indexA, int indexB) {
		int temp = tempArray[indexA];
		tempArray[indexA] = tempArray[indexB];
		tempArray[indexB] = temp;
	}
	
	public static void main(String[] args) {
		int[] myArray = {2,8,6,0,1,-3,20,-10,100,23,3,6};
		QuickSort qs = new QuickSort();
		System.out.println(Arrays.toString(myArray));
		int[] sorted = qs.sort(myArray);
		System.out.println(Arrays.toString(sorted));
		int[] reverseSorted = qs.reverseSort(myArray);
		System.out.println(Arrays.toString(reverseSorted));
		
	}
	
	
}
