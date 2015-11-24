import java.util.ArrayList;
import java.util.HashMap;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.In;

/**
 * Implements autocomplete on prefixes for a given dictionary of terms and
 * weights.
 *
 * @author Nitin
 */
public class Autocomplete {
    
    Trie toInsertInto = new Trie();
    HashMap<String, Double> dups = new HashMap<String, Double>();

    /**
     * Initializes required data structures from parallel arrays.
     * 
     * @param terms
     *            Array of terms.
     * @param weights
     *            Array of weights.
     */
    public Autocomplete(String[] terms, double[] weights) {
        if (terms.length != weights.length) {
            throw new IllegalArgumentException(
                    "The two arrays are not equal in size");
        }
        for (int i = 0; i < terms.length; i++) {
            if (weights[i] < 0) {
                throw new IllegalArgumentException(
                        "Cannot have negative weights");
            } else {
                if (!dups.containsKey(terms[i])) {
                    dups.put(terms[i], weights[i]);
                    toInsertInto.insert(terms[i], weights[i]);
                } else {
                    throw new IllegalArgumentException("There are duplicates");
                }
            }
        }
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return
     * 0.0
     * 
     * @param term is the term you want to find the weight of
     * @return the weight of the term
     */
    public double weightOf(String term) {
        if (dups.containsKey(term)) {
            return dups.get(term);
        } else {
            return 0.0;
        }
    }

    /**
     * Return the top match for given prefix, or null if there is no matching
     * term.
     * 
     * @param prefix
     *            Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        ArrayList<String> topMatches = topMatches(prefix, 1);
        if (topMatches == null) {
            return "";
        }
        return topMatches.remove(0);
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an
     * iterable. If there are less than k matches, return all the matching
     * terms.
     * 
     * @param prefix is the prefix you want to find the matches for
     * @param k is the number of matches you want
     * @return an arraylist with the top matches
     */
    public ArrayList<String> topMatches(String prefix, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException(
                    "Cannot find top matches for a non positive k");
        }
        ArrayList<String> al = topMatches(prefix, k, toInsertInto);
        if (al == null) {
            return null;
        } else {
            return al;
        }
    }

    /**
     * Calls the method in Trie.java that gets the top k elements
     * 
     * @param prefix is the prefix you want to find the matches for
     * @param k is the number of matches you want
     * @param t is the trie that you use to find the matches
     * @return an arraylist with the top matches
     */
    private ArrayList<String> topMatches(String prefix, int k, Trie t) {    
        ArrayList<String> matches = t.getTopK(prefix, k);
        return matches;
    }

    /**
     * Returns the highest weighted matches within k edit distance of the word.
     * If the word is in the dictionary, then return an empty list.
     * 
     * @param word
     *            The word to spell-check
     * @param dist
     *            Maximum edit distance to search
     * @param k
     *            Number of results to return
     * @return Iterable in descending weight order of the matches
     */
    public Iterable<String> spellCheck(String word, int dist, int k) {
        return null;
    }

    /**
     * Test client. Reads the data from the file, then repeatedly reads
     * autocomplete queries from standard input and prints out the top k
     * matching terms.
     * 
     * @param args
     *            takes the name of an input file and an integer k as
     *            command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble(); // read the next weight
            in.readChar(); // scan past the tab
            terms[i] = in.readLine(); // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term),
                        term);
            }
        }
    }
}
