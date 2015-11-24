import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * AlphabetSort class that sorts alphabet
 * 
 * @author Nitin
 *
 */
public class AlphabetSort {


    /**
     * Method that calls the traverse method in trie
     * 
     * @param t is the trie you traverse through
     */
    public static void traverseTrie(Trie t) {
        t.wordsInOrder();
    }

    /**
     * Initializing the trie. Making sure that words that can be inserted are
     * inserted. After insertion is done, the tree is traversed
     * 
     * @param args is the file you are passing in
     */
    public static void main(String[] args) {
        Trie toInsertInto;
        ArrayList<String> myList = new ArrayList<String>();
        Scanner inFile = null;
        inFile = new Scanner(System.in);
        while (inFile.hasNextLine()) {
            myList.add(inFile.nextLine());
        }
        inFile.close();
        if (myList.isEmpty() || myList.size() == 1) {
            throw new IllegalArgumentException("No words or alphabet given");
        }
        String order = myList.remove(0);
        char[] checkDuplicates = new char[order.length()];
        for (int i = 0; i < order.length(); i++) {
            checkDuplicates[i] = order.charAt(i);
        }
        Arrays.sort(checkDuplicates);
        for (int i = 0; i < order.length() - 1; i++) {
            if (checkDuplicates[i] == checkDuplicates[i + 1]) {
                throw new IllegalArgumentException("Duplicate characters exist");
            }
        }
        toInsertInto = new Trie(order);
        for (String word : myList) {
            int i = 0;
            boolean canInsert = true;
            while (i < word.length()) {
                if (toInsertInto.getMap().containsKey(word.charAt(i))) {
                    i++;
                    continue;
                } else {
                    i = word.length();
                    canInsert = false;
                }
            }
            if (canInsert) {
                try {
                    toInsertInto.insert(word);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        traverseTrie(toInsertInto);
    }
}
