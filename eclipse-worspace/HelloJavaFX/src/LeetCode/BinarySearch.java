package LeetCode;
import java.util.List;
import java.util.Arrays;

public class BinarySearch {


	public static int binarySearch(List<Integer> list, int target, 
			int left, int right){
	  	if (target < list.get(0) || target > list.get(list.size()-1)){
	      return -1;
	    }
	   
	    int midPoint = (right - left)/2 + left;
	    System.out.println("Mid " +left +" "+ right);
		while (left <= right) {
		    if (list.get(midPoint) == target) {
			  return midPoint;   
			} else if (target < list.get(midPoint)){
			  return binarySearch(list, target, left, midPoint-1);     
			} else  {
			  return binarySearch(list, target, midPoint+1, right);
			}
		}
		return midPoint;
	}
	  
	  public static boolean test(List<Integer> list, int target, int expected){
	    int res = binarySearch(list, target, 0, list.size()-1);
	    
	    if(res == expected){
	      System.out.println("Success!! \t Got: " + res + "\t Expected: " + expected);
	    } else	System.out.println("Failed \t Got: " + res + "\t Expected: " + expected);

	    return false;
	    
	  }
	  
	  public static void main(String[] args) {
	    Integer[] array = {1, 3, 6, 8, 13};
		List<Integer> list = Arrays.asList(array);  
		test(list, 3, 1);
		test(list, 4, 2);
	    test(list, 15, -1);
	    test(list, 1, 0);
	    test(list, 4, 2);
	    test(list, 2, 1);
	    test(list, 9, 4);
	    test(list, 12, 4);
	    
	  }
	}
