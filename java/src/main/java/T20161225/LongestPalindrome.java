package T20161225;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Longest Palindromic Substring
 * Given a string S, find the longest palindromic substring in S.
 * You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.
 * Example
 * Given the string = "abcdzdcab", return "cdzdc".
 */
public class LongestPalindrome {
    /**
     * @param s input string
     * @return the longest palindromic substring
     */
    public String longestPalindrome(String s) {
        // build indexer
        char[] chars = s.toCharArray();
        if (chars.length == 0) {
            return "";
        }
        Map<Character, List<Integer>> indexer = new HashMap<>();
        char tmp;
        int capacity = chars.length >> 6;
        for (int i = 0; i < s.length(); i++) {
            tmp = chars[i];
            List<Integer> list = indexer.get(tmp);
            if (list == null) {
                list = new ArrayList<>(capacity);
                indexer.put(tmp, list);
            }
            list.add(i);
        }

        int max = 0;
        int start = 0;
        for (Character c : indexer.keySet()) {
            List<Integer> list = indexer.get(c);
            if (list.size() < 1 || list.get(list.size() - 1) - list.get(0) < max)
                continue;
            int len = list.size();
            for (int i = 0; i < len - 1; i++) {
                for (int j = i + 1; j < len; j++) {
                    int st = list.get(i);
                    int en = list.get(j);
                    if (en - st < max) {
                        continue;
                    }
                    if (isPalindrome(chars, st, en)) {
                        max = en - st;
                        start = st;
                    }
                }
            }
        }
        if (max == 0) {
            return new String(new char[]{chars[0]});
        } else {
            return new String(chars, start, max + 1);
        }

    }

    private boolean isPalindrome(char[] chars, int start, int end) {
        while (++start < --end) {
            if (chars[start] != chars[end]) {
                return false;
            }
        }
        return true;
    }


    public static void main(String args[]) {
        LongestPalindrome longestPalindrome = new LongestPalindrome();
        System.out.print(longestPalindrome.longestPalindrome("agaxgbyicsygnxmutgfbegyfgndjhvgfyycghhgytfdrthhujjhgvghjhggftcgfdtrftgvjgvbgjfghhgfdftggtfdgvbhyujnnjuyhbvgtfghytfghytyyhgftyhgftfghytfghytyhgftyhgfty"));
    }
}
