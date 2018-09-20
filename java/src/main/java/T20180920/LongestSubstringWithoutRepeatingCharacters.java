package T20180920;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Description
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Example
 * For example, the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3.
 *
 * For "bbbbb" the longest substring is "b", with the length of 1.
 *
 * Challenge
 * O(n) time
 */
public class LongestSubstringWithoutRepeatingCharacters {
    /**
     * @param s: a string
     * @return: an integer
     */
    public int lengthOfLongestSubstring(String s) {
        // write your code here
        Queue<Character> queue = new LinkedList<>();
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            while (queue.contains(ch)) {
                queue.poll();
            }
            queue.add(ch);
            if (queue.size() > max) {
                max = queue.size();
            }
        }
        return max;
    }
}
