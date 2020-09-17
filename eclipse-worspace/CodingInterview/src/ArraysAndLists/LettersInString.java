package ArraysAndLists;

import java.util.HashMap;

public class LettersInString {
	
	public boolean differentLetters(String word) {
		HashMap<Character, Integer> letters = 
				new HashMap<Character, Integer>();
		
		word = word.toLowerCase();
		char[] aWord = word.toCharArray();
		letters.put(aWord[0], 1);
		for (int k = 1; k < aWord.length; k++) {
			if (letters.containsKey(aWord[k])) {
				return false;
			} else {
				letters.put(aWord[k], 1);
			}
		}
		return true;
	}
	
	public boolean differentLetterTime(String word) {
		
		word = word.toLowerCase();
		char[] aWord = word.toCharArray();
		for (int k = 0; k < aWord.length; k++) {
			for (int j = k+1; j < aWord.length; j++) {
				if (aWord[k]==aWord[j]) {
					return false;
				} 
			
			}
		}
		return true;
	}
	
	public void testing() {
		System.out.println("With additional memory use");
		
		String word = "house";
		boolean result = differentLetters(word);
		System.out.println(result + " = true.");
		
		String word1 = "Tree";
		boolean result1 = differentLetters(word1);
		System.out.println(result1 + " = false.");
		
		String word2 = "knowledge";
		boolean result2 = differentLetters(word2);
		System.out.println(result2 + " = false.");
		
		String word3 = "print";
		boolean result3 = differentLetters(word3);
		System.out.println(result3 + " = true.");
		
		System.out.println("With additional runtime use");
		
		String word4 = "Comment";
		boolean result4 = differentLetterTime(word4);
		System.out.println(result4 + " = false.");
		
		String word5 = "Dynamic";
		boolean result5 = differentLetterTime(word5);
		System.out.println(result5 + " = true.");
		
		String word6 = "Lucio";
		boolean result6 = differentLetterTime(word6);
		System.out.println(result6 + " = true.");
		
		String word7 = "Yvonne";
		boolean result7 = differentLetterTime(word7);
		System.out.println(result7 + " = false.");

	}
	
	public static void main(String[] args) {
		LettersInString myLI = new LettersInString();
		myLI.testing();
	}
}
