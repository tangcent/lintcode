package T20180917;

/**
 * Description
 * Given a string S, find the longest palindromic substring in S. You may assume that the maximum length of S is 1000, and there exists one unique longest palindromic substring.
 * <p>
 * Example
 * Given the string = "abcdzdcab", return "cdzdc".
 * <p>
 * Challenge
 * O(n2) time is acceptable. Can you do it in O(n) time.
 */
public class LongestPalindromicSubstring {

    /**
     * @param s: input string
     * @return: the longest palindromic substring
     */
    public String longestPalindrome(String s) {
        // write your code here

        final int mid = s.length() >> 1;
        if (mid == 0) {
            return s;
        }

        int minIndex = 0;
        int maxStrStart = 0;
        int maxStrLength = 1;

        int a, b;
        if ((s.length() & 1) == 0) {
            a = mid - 1;
            b = mid;
            if (s.charAt(a) == s.charAt(b)) {
                maxStrLength = longestPalindrome(s, a - 1, b + 1);
                minIndex = (maxStrLength >> 1);
                maxStrStart = a - minIndex + 1;
            }
        } else {
            a = mid;
            b = mid;
            maxStrLength = longestPalindrome(s, a, a);
            minIndex = (maxStrLength >> 1);
            maxStrStart = a - minIndex;
        }

        while (a > minIndex) {

            int max = 0;


            if (s.charAt(a) == s.charAt(a - 1)) {
                max = longestPalindrome(s, a - 2, a + 1);
                if (max > maxStrLength) {
                    maxStrLength = max;
                    minIndex = (maxStrLength >> 1);
                    maxStrStart = a - minIndex;
                }
            }

            if (s.charAt(b) == s.charAt(b + 1)) {
                max = longestPalindrome(s, b - 1, b + 2);
                if (max > maxStrLength) {
                    maxStrLength = max;
                    minIndex = (maxStrLength >> 1);
                    maxStrStart = b - minIndex + 1;
                }
            }

            if (s.charAt(a - 1) == s.charAt(a + 1)) {
                max = longestPalindrome(s, a - 1, a + 1);
                if (max > maxStrLength) {
                    maxStrLength = max;
                    minIndex = (maxStrLength >> 1);
                    maxStrStart = a - minIndex;
                }
            }


            if (s.charAt(b - 1) == s.charAt(b + 1)) {
                max = longestPalindrome(s, b, b);
                if (max > maxStrLength) {
                    maxStrLength = max;
                    minIndex = (max >> 1);
                    maxStrStart = b - minIndex;
                }
            }

            --a;
            ++b;
        }

        return s.substring(maxStrStart, maxStrStart + maxStrLength);

    }

    public int longestPalindrome(String s, int indexA, int indexB) {
        try {
            while (s.charAt(indexA) == s.charAt(indexB)) {
                indexA--;
                indexB++;
            }
        } catch (Exception ignored) {

        }
        return indexB - indexA - 1;
    }

    public static void main(String[] args) {
        System.out.println(new LongestPalindromicSubstring().longestPalindrome("abb"));
        System.out.println(new LongestPalindromicSubstring().longestPalindrome("abiyw8wqbggbqw8whusbaabsdiqqbgygbqqbqywebe1927ehbncdzdzdcnbhecab"));
    }

}
