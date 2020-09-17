package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		if (word.isEmpty()) return false;
		if (isWord(word)) return false;
		word = word.toLowerCase();
		char[] inWord = word.toCharArray();
		TrieNode firstNode = root.insert(inWord[0]);
		TrieNode nextNode = null;
		if(firstNode == null) {
			nextNode = root.getChild(inWord[0]);
		} else {
			nextNode = firstNode;
		}

		for (int i = 1; i < inWord.length; i++) {
			if (nextNode.getChild(inWord[i]) != null) {
				nextNode = nextNode.getChild(inWord[i]);
				continue;
			} else {
				nextNode = nextNode.insert(inWord[i]);
			}
		}
		nextNode.setEndsWord(true);
		size ++;
			
		
	    return nextNode.endsWord();
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		if (s.isEmpty()) return false;
		s = s.toLowerCase();
		char [] inS = s.toCharArray();
		TrieNode currNode = root.getChild(inS[0]);
		for (int i = 1; i < inS.length; i++) {
			if (currNode == null) break;
			currNode = currNode.getChild(inS[i]);
		}
		if (currNode == null) return false;
		
		return currNode.endsWord();
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 List<String> predictions = new ArrayList<String>(numCompletions);
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 
    	 prefix = prefix.toLowerCase();
 		 char [] inPrefix = prefix.toCharArray();
 		 
 		 List<Character> sortedMap = new ArrayList<Character>(this.root.getValidNextCharacters());
		 Collections.sort(sortedMap);
		 int indexOfChar = 0;
			
 		 TrieNode currNode = null;
 		 if (inPrefix.length == 0) {
 			
 			while (currNode == null) {				
 				currNode = this.root.getChild(sortedMap.get(indexOfChar));
 				indexOfChar ++;
 			}
 			 
 		 } else {
 			currNode = root.getChild(inPrefix[0]);
 		 }
 		 
 		 if (currNode == null) return predictions;
 		 
 		 for (int i = 1; i < inPrefix.length; i++) {
 			 currNode = currNode.getChild(inPrefix[i]);
 		 }
 		 //if (currNode == null) return predictions;
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
 		 Queue<TrieNode> lineWords = new LinkedList<TrieNode>();
 		 lineWords.add(currNode);
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
 		 while (!lineWords.isEmpty()) {
    	 //       remove the first Node from the queue
 			TrieNode firstLine = lineWords.remove();
 			
    	 //       If it is a word, add it to the completions list
 			if(firstLine == null) break;
 			if (firstLine.endsWord() && predictions.size() < numCompletions) {
 				if (firstLine.getText().length() >= inPrefix.length) {
 					predictions.add(firstLine.getText());
 				}
 			}
 			
    	 // Add all of its child nodes to the back of the queue
 			for(Character tn : firstLine.getValidNextCharacters()) {
 				lineWords.add(firstLine.getChild(tn));
 			}
 			
 			if (lineWords.size() == 0 && predictions.size() < numCompletions) {
 				
 				if (inPrefix.length == 0) {
 					firstLine = root;
 				} else {
 					indexOfChar = 0;
 				}
 				while (lineWords.size() == 0 && indexOfChar < sortedMap.size()) {				
 					lineWords.add(firstLine.getChild(sortedMap.get(indexOfChar)));
 	 				indexOfChar ++;
 	 			}
 			}
 		 }
 		 // Return the list of completions   	 
         return predictions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	public static void main(String[] args) {
 		AutoCompleteDictionaryTrie largeDict = new AutoCompleteDictionaryTrie();
 		AutoCompleteDictionaryTrie smallDict = new AutoCompleteDictionaryTrie();
// 		String dictFile = "data/words.small.txt";
// 		DictionaryLoader.loadDictionary(largeDict, dictFile);
// 		List<String> completions = largeDict.predictCompletions("", 4);
// 		System.out.println(completions);
 		
 		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		List<String> completions = smallDict.predictCompletions("",  4);
		System.out.println(completions);
		smallDict.printTree();
 	}

	
}