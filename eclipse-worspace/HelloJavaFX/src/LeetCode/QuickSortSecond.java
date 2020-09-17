package LeetCode;

import java.util.ArrayList;
import java.util.Random;

public class QuickSortSecond {
    ArrayList<Integer> toSort;

    public QuickSortSecond(ArrayList<Integer> data){
    	toSort = data;
    }
    
    public ArrayList<Integer> sort() {
    	if(toSort == null || toSort.size() == 0) {
    		throw new IllegalArgumentException("Input is ether null or size 0");
    	}
    	int start = 0;
    	int end = toSort.size()-1;
    	quickSort(start, end);
    	return toSort;
    }
    
    private void quickSort(int start, int end){
    	if(start >= end) {
    		return;
    	}
    	
    	int pivot = partition(start, end);
    	
    	quickSort(start, pivot - 1);
    	quickSort(pivot + 1, end);
    		
    	
    }
    
    private int partition(int start, int end) {
    	int pivot = (end - start) / 2 + start;
    	int indexPivot = start;
    	swap(pivot, end);
    	for (int i = start; i < toSort.size()-1; i++) {
    		if(toSort.get(i) < toSort.get(end)) {
    			swap(i, indexPivot);
    			indexPivot++;
    		}
    	}
    	swap(indexPivot, end);
    	return indexPivot;
    }
    
    private void swap(int indexA, int indexB) {
    	
    	int temp = toSort.get(indexA);
    	toSort.set(indexA, toSort.get(indexB));
    	toSort.set(indexB, temp);
    }
    private void testing(){

        System.out.println(toSort);
        System.out.println(sort());
    }
    public static void main(String[] args){
        Random random = new Random();
        ArrayList<Integer> myList = new ArrayList<Integer>();
        for (int i =0; i < 10 ; i++) {
        	myList.add(random.nextInt(10));
        }
        QuickSortSecond qs = new QuickSortSecond(myList);
        qs.testing();
    }
}