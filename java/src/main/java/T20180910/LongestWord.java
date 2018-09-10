package T20180910;

import java.util.LinkedList;
import java.util.List;

/**
 * Description
 * Given a dictionary, find all of the longest words in the dictionary.
 * <p>
 * Example
 * Given
 * <p>
 * {
 * "dog",
 * "google",
 * "facebook",
 * "internationalization",
 * "blabla"
 * }
 * the longest words are(is) ["internationalization"].
 * <p>
 * Given
 * <p>
 * {
 * "like",
 * "love",
 * "hate",
 * "yes"
 * }
 * the longest words are ["like", "love", "hate"].
 * <p>
 * Challenge
 * It's easy to solve it in two passes, can you do it in one pass?
 */
public class LongestWord {

    /*
     * @param dictionary: an array of strings
     * @return: an arraylist of strings
     */
    public List<String> longestWords(String[] dictionary) {
        // write your code here

        List<String> maxWord = new LinkedList<>();
        int count = 0;
        for (String word : dictionary) {
            if (word.length() < count) {
                continue;
            } else if (word.length() == count) {
                maxWord.add(word);
            } else {
                maxWord.clear();
                maxWord.add(word);
                count = word.length();
            }
        }
        return maxWord;
    }

}
