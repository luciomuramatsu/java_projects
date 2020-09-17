package textgen;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		
		String[] allWords = sourceText.split(" +");
		System.out.println(Arrays.toString(allWords));
		String prevWord = "";
		if (allWords.length > 1) {
			starter = allWords[0];
			prevWord = starter;
			ListNode firstNode = new ListNode(starter);
			wordList.add(firstNode);
		}
		boolean wasEqual = false;
		
		for (int i = 1; i < allWords.length; i++) {
			String w = allWords[i];
			//System.out.println("w) " + w);
			ListNode n = new ListNode(prevWord);
			//System.out.println("prevWord " + prevWord);
			for (int k = 0; k < wordList.size(); k++) {
				if(wordList.get(k).getWord().equals(prevWord)) {
					wordList.get(k).addNextWord(w);
					//System.out.println("wordList.get(k).getWord() " + wordList.get(k));
					wasEqual = true;
					break;
				}
				if(i == allWords.length-1) {
					if(wordList.get(k).getWord().equals(w)) {
						wordList.get(k).addNextWord(starter);
						//System.out.println("wordList.get(k).getWord() " + wordList.get(k));
						wasEqual = true;
						break;
					}
				}
			}
			if(i == allWords.length-1) {
				ListNode lastNode = new ListNode(w);
				//System.out.println("lastNode " + lastNode);
				wordList.add(lastNode);
				((LinkedList<ListNode>) wordList).getLast().addNextWord(starter);
			}
			
			if(wasEqual) {
				wasEqual = false;
				prevWord = w;
				continue;
				}
			wordList.add(n);
			//System.out.println("wordList.add(n) ");
			((LinkedList<ListNode>) wordList).getLast().addNextWord(w);
			//System.out.print("i " + i);
			//System.out.println("\t " + allWords.length);
	
			prevWord = w;
		}
		//System.out.println(wordList);
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if (wordList.size() == 0) {return "";}
		if (numWords == 0) {return "";}
		String currWord = starter;
		StringBuilder output = new StringBuilder();
		output.append(currWord);
		output.append(" ");
		int number = 1;
		int currIndex = -2;
		//System.out.println("wordList.get(i) " + wordList.get(0).getWord());
		//System.out.println("currWord " + currWord);
		//System.out.println("IF clause " + wordList.get(0).getWord().equals(currWord));
		while(number < numWords) {
			for (int i = 0; i < wordList.size(); i++) {
				if (wordList.get(i).getWord().equals(currWord)) {
					currIndex = i;
					//System.out.println("currIndex " + currIndex);
					//System.out.println("number " + number);
					//System.out.println("currWord " + currWord);
					break;
				}
			}
			
			String w = wordList.get(currIndex).getRandomNextWord(rnGenerator);
			//System.out.println("w " + w);
			output.append(w);
			output.append(" ");
			currWord = w;
			//System.out.println("w " + w);
			currIndex = -1;
			number ++;
		}
		
		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		clearAll();
		train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	private void clearAll() {
		wordList = new LinkedList<ListNode>();
		starter = "";
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//String textString = "";
		//System.out.println(textString + " test0");
		gen.train(textString);
		//System.out.println("test ");
		//System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		
        String input = "I love cats. I hate dogs. I I I I I I I I I I I I I I I I love cats. I I I I I I I I I I I I I I I I hate dogs. I I I I I I I I I like books. I love love. I am a text generator. I love cats. I love cats. I love cats. I love love love socks.";
        System.out.println(input);
		gen.retrain(input);
		System.out.println(gen);
		System.out.println(gen.generateText(500));
		String textString3 = "";
		gen.train(textString3);
		System.out.println(gen);
		System.out.println(gen.generateText(500));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
		int randIndex = generator.nextInt(nextWords.size());
		String w = nextWords.get(randIndex);
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	    return w;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


