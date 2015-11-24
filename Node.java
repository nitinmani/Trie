package proj3;

import java.util.HashMap;
import java.util.Map;

public class Node {
	private static boolean hasWord = false;
	private static String savedWord = null;
	private Node toInsert = null;
	private boolean isWord;
	private boolean isLeaf;
	private char c;
	private Map<Character, Node> children;
	
	public Node() {
		children = new HashMap<Character, Node>();
		isLeaf = true;
		isWord = false;
	}
	
	public Node(String w) {
		this();
		this.insert(w);
	}
	
	public void insert(String word) {
		if(word == null || word.length() == 0)
			throw new IllegalArgumentException("blah");
		char first = word.charAt(0);
		if(children.get(first) == null) {
			this.isLeaf = false;
			if(word.length() == 1) {
				toInsert = new Node();
				children.put(first, toInsert);
				children.get(first).isWord = true;
			} else {
				toInsert = new Node();
				children.put(first, toInsert);
				children.get(first).insert(word.substring(1));
			}
			children.get(first).c = first;
		} else {
			children.get(first).insert(word.substring(1));
		}
	}
	
	public boolean find(String s, boolean isFullWord) {
		return Node.findHelper(this, s, isFullWord, 0);
	}
	
	private static boolean findHelper(Node node, String word, boolean isFullWord, int startVal) {
		if(startVal == 0) {
			savedWord = word.substring(0); //only should be done the first time
		}
		char letterAt = word.charAt(0);
		if(node.children.get(letterAt) == null) {
			hasWord = false;
		} else if(word.length() > 1) {
			if(!isFullWord) {
				if(savedWord.contains(word)) {
					hasWord = true;
				} else {
					Node.findHelper(node.children.get(letterAt), word.substring(1), isFullWord, startVal++);
				}
			}
			Node.findHelper(node.children.get(letterAt), word.substring(1), isFullWord, startVal++);
		} else {
			if(node.children.get(letterAt).isWord) {
				hasWord = true;
			} else {
				hasWord = false;
			}
		}
		return hasWord;
	}
}
