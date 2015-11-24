import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * Trie class that is a TST based implementation
 * 
 * @author Nitin
 *
 */
public class Trie {

    private Node root;
    private HashMap<Character, Integer> alphabetRanks = new HashMap<Character, Integer>();
    private boolean def;

    /**
     * Node class that holds the character and its value
     * 
     * @author Nitin
     *
     */
    private static class Node {
        private char current;
        private Double value;
        private Double max;
        boolean exists;
        private Node left;
        private Node middle;
        private Node right;

        /**
         * Construct Node with the data
         * 
         * @param data is what the character in the node is 
         */
        public Node(char data) {
            this.current = data;
            this.exists = false;
            this.left = null;
            this.right = null;
            this.middle = null;

        }

        /**
         * Construct a node with the data and the value passed in
         * 
         * @param data is what the character in the node is 
         * @param maxVal is the value that the user gives
         */
        public Node(char data, double maxVal) {
            this.current = data;
            this.exists = false;
            this.left = null;
            this.right = null;
            this.middle = null;
            this.value = null;
            this.max = maxVal;

        }

        /**
         * Construct the node with the empty string
         *
         **/
        public Node() {
            this.current = '\0';
            this.exists = false;
            this.left = null;
            this.right = null;
            this.middle = null;
            this.value = null;
            this.max = null;

        }
    }

    /**
     * Default trie
     */
    public Trie() {
        root = new Node();
        def = true;
    }

    /**
     * If you are inserting with an arbitrary order of the alphabet Use this one
     *
     * @param arbitraryOrder is the order of the alphabet used to sort
     */
    public Trie(String arbitraryOrder) {
        root = new Node();
        def = false;
        getVals(arbitraryOrder);
    }

    /**
     * Finding a word
     * 
     * @param s is what you are checking for existence
     * @param isFullWord whether you are searching the full word or not
     * @return boolean whether word exists or not
     */
    public boolean find(String s, boolean isFullWord) {
        return find(root.middle, s, isFullWord, 0);
    }

    /**
     * Method returns character to rank (for AlphabetSort)
     * 
     * @return map of character to rank
     */
    public HashMap<Character, Integer> getMap() {
        return this.alphabetRanks;
    }

    /**
     * Check if you can find the word by going down its characters
     * 
     * @param x is the node you are starting at
     * @param s is the word you want to find
     * @param isFullWord whether or not it is the full word
     * @param d tracks the characters of the word
     * @return boolean value of word's existence
     */
    private boolean find(Node x, String s, boolean isFullWord, int d) {
        if (x == null) {
            return false;
        }
        char c = s.charAt(d);
        if (!def) {
            if (alphabetRanks.get(x.current) > alphabetRanks.get(c)) {
                return find(x.left, s, isFullWord, d);
            } else if (alphabetRanks.get(x.current) < alphabetRanks.get(c)) {
                return find(x.right, s, isFullWord, d);
            } else if (d < s.length() - 1) {
                return find(x.middle, s, isFullWord, d + 1);
            } else {
                if (isFullWord) {
                    return x.exists;
                } else {
                    return true;
                }
            }
        } else {
            if (x.current > c) {
                return find(x.left, s, isFullWord, d);
            } else if (x.current < c) {
                return find(x.right, s, isFullWord, d);
            } else if (d < s.length() - 1) {
                return find(x.middle, s, isFullWord, d + 1);
            } else {
                if (isFullWord) {
                    return x.exists;
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * Insert if you aren't given weight
     * 
     * @param s is word to insert
     */
    public void insert(String s) {
        root.middle = insert(root.middle, s, 0);
    }

    /**
     * Insert if you are given weight
     * 
     * @param s is word to insert
     * @param val is value you are adding while inserting
     */
    public void insert(String s, double val) {
        root.middle = insert(root.middle, s, 0, val);
    }

    /**
     * Insert by checking if node exists, and adding to left or right as needed
     * 
     * @param x is your starting node
     * @param key is the word you are inserting
     * @param d tracks the current character
     * @return the node where you inserted
     */
    private Node insert(Node x, String key, int d) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException(
                    "Cannot add empty or null string");

        }
        char c = key.charAt(d);
        Node tempNode;
        if (x == null) {
            x = new Node(key.charAt(d));
        }
        if (!def) {
            if (alphabetRanks.get(x.current) > alphabetRanks.get(c)) {
                tempNode = insert(x.left, key, d);
                if (x.left == null) {
                    x.left = tempNode;
                }
            } else if (alphabetRanks.get(x.current) < alphabetRanks.get(c)) {
                tempNode = insert(x.right, key, d);
                if (x.right == null) {
                    x.right = tempNode;
                }
            } else {
                if (d < key.length() - 1) {
                    tempNode = insert(x.middle, key, d + 1);
                    if (x.middle == null) {
                        x.middle = tempNode;
                    }
                } else {
                    x.exists = true;
                }
            }
        } else {
            if (x.current > c) {
                tempNode = insert(x.left, key, d);
                if (x.left == null) {
                    x.left = tempNode;
                }
            } else if (x.current < c) {
                tempNode = insert(x.right, key, d);
                if (x.right == null) {
                    x.right = tempNode;
                }
            } else {
                if (d < key.length() - 1) {
                    tempNode = insert(x.middle, key, d + 1);
                    if (x.middle == null) {
                        x.middle = tempNode;
                    }
                } else {
                    x.exists = true;
                }
            }
        }
        return x;
    }

    /**
     * Same as the other insert method, but also insert the value passed in
     * 
     * @param x is the starting node
     * @param key is the word you are inserting
     * @param d is used to track the current character 
     * @param value is the value that the node takes
     * @return node where you end
     */
    private Node insert(Node x, String key, int d, double value) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException(
                    "Cannot add empty or null string");

        }
        char c = key.charAt(d);
        if (x == null) {
            x = new Node(key.charAt(d), value);
        }
        if (x.current > c) {
            if (x.max < value) {
                x.max = value;
            }
            x.left = insert(x.left, key, d, value);
        } else if (x.current < c) {
            if (x.max < value) {
                x.max = value;
            }
            x.right = insert(x.right, key, d, value);
        } else {
            if (d < key.length() - 1) {
                if (x.max < value) {
                    x.max = value;
                }
                x.middle = insert(x.middle, key, d + 1, value);
            } else {
                x.exists = true;
                x.max = Math.max(value, x.max);
                x.value = value;
            }
        }
        return x;
    }

    /**
     * Character to value mapping created here
     * 
     * @param alphabetOrder gives you the character to position mappings
     */
    private void getVals(String alphabetOrder) {
        for (int i = 0; i < alphabetOrder.length(); i++) {
            char c = alphabetOrder.charAt(i);
            alphabetRanks.put(c, i);
        }
    }

    /**
     * In order traversal of the words in the trie
     */
    public void wordsInOrder() {
        wordsInOrder(root.middle, "");
    }

    /**
     * Returns all words with the same prefix
     * 
     * @param x is the starting node
     * @param prefix is the prefix you are checking
     */
    private void wordsInOrder(Node x, String prefix) {
        if (x != null) {
            wordsInOrder(x.left, prefix);
            if (x.exists) {
                System.out.println(prefix + "" + x.current);
            }
            wordsInOrder(x.middle, prefix + "" + x.current);
            wordsInOrder(x.right, prefix);
        }
    }

    /**
     * Get the node that succeeds the terminating node of the prefix
     * 
     * @param prefix you are going to the node using this prefix
     * @return node which ends at the prefix
     */
    private Node getNodeAt(String prefix) {
        if (prefix.length() == 0) {
            return root;
        }
        Node temp = root.middle;
        int d = 0;
        while (temp != null && d < prefix.length()) {
            if (temp.current < prefix.charAt(d)) {
                temp = temp.right;
            } else if (temp.current > prefix.charAt(d)) {
                temp = temp.left;
            } else if (d < prefix.length() - 1) {
                temp = temp.middle;
                d++;
            } else {
                return temp;
            }
        }
        return temp;
    }

    /**
     * Method calls another method that returns a PQ of the words with the
     * prefixes. First go down to the subtree (ending node of prefix) and add it
     * if it's already part of a word. Invoke other method which you can then
     * iterate over to return an array list of words
     * 
     * @param prefix that you need to get the top k elements
     * @param k number of elements you need
     * @return iterable of the number of elements
     */
    public ArrayList<String> getTopK(String prefix, int k) {
        Node x = getNodeAt(prefix);
        if (x == null) {
            return new ArrayList<String>();
        }
        TreeSet<Node> tree = new TreeSet<Node>(new ValueComparison());
        HashMap<Node, String> mapNodePrefix = new HashMap<Node, String>();
        mapNodePrefix.put(x, prefix);
        PriorityQueue<Node> maxPQ = new PriorityQueue<Node>(k, new MaxComparison());
        TreeSet<Node> output;
        if (x.exists) {
            tree.add(x);
        }
        if (prefix.length() == 0) {
            output = getTopK(root.middle, maxPQ, tree, k, prefix, mapNodePrefix);
        } else {
            output = getTopK(x.middle, maxPQ, tree, k, prefix, mapNodePrefix);
        }
        ArrayList<String> matching = new ArrayList<String>();
        Iterator<Node> searchOutputs = output.iterator();
        int i = 0;
        while (i < k && searchOutputs.hasNext()) {
            Node next = searchOutputs.next();
            String nextToAdd = mapNodePrefix.get(next);
            matching.add(nextToAdd);
            i++;
        }
        return matching;
    }

    /**
     * Returns a tree set of the top k words with the same prefix
     * 
     * @param node starting point
     * @param queue keeps track of all the nodes that you need to check
     * @param topK is a tree set that has all of the nodes to return
     * @param mapNodePrefix maps node to prefix
     * @param k is the minimum number of nodes to return
     * @param prefix is the prefix you are checking
     * @return topK
     */
    private TreeSet<Node> getTopK(Node node, PriorityQueue<Node> queue,
            TreeSet<Node> topK, int k, String prefix,
            HashMap<Node, String> mapNodePrefix) {
        if (node == null) {
            return topK;
        }

        if (node.exists) {
            topK.add(node);
            mapNodePrefix.put(node, (prefix + node.current));
        }

        if (node.middle != null) {
            queue.add(node.middle);
            mapNodePrefix.put(node.middle, (prefix + node.current));
        }
        
        if (node.left != null) {
            queue.add(node.left);
            mapNodePrefix.put(node.left, prefix);
        }

        if (node.right != null) {
            queue.add(node.right);
            mapNodePrefix.put(node.right, prefix);
        }

        if (queue.isEmpty()) {
            return topK;
        }

        if (topK.size() >= k) {
            Iterator<Node> iter = topK.iterator();
            Node lastNode = queue.peek();
            for (int i = 0; i < k; i++) {
                lastNode = iter.next();
            }
            if (lastNode.value < queue.peek().max) {
                Node next = queue.remove();
                topK = getTopK(next, queue, topK, k,
                        mapNodePrefix.get(next), mapNodePrefix);              
            } else {
                return topK;
            }
        } else {
            Node next = queue.remove();
            topK = getTopK(next, queue, topK, k,
                    mapNodePrefix.get(next), mapNodePrefix);
        }
        return topK;
    }

    /**
     * Class that allows you to compare the two values of two nodes
     * 
     * @author Nitin
     *
     */
    private class ValueComparison implements Comparator<Node> {
        /**
         * Default constructor for comparator
         **/
        public ValueComparison() {

        }

        /**
         * Compare method allows you to add in tree set
         *
         * @param f is the first node
         * @param s is the second node
         * @return an integer that tells us how the two values compare
         */
        public int compare(Node f, Node s) {
            if (f.value < s.value) {
                return 1;
            } else if (f.value > s.value) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    /**
     * Class that allows you to compare maximum values of the two nodes
     * 
     * @author Nitin
     *
     */
    private class MaxComparison implements Comparator<Node> {
        /**
         * Default constructor for comparator
         **/
        public MaxComparison() {

        }

        /**
         * Compare method allows you to add in PQ
         *
         * @param f is the first node
         * @param s is the second node
         * @return an integer that tells us how the two values compare
         */
        public int compare(Node f, Node s) {
            if (f.max < s.max) {
                return 1;
            } else if (f.max > s.max) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
