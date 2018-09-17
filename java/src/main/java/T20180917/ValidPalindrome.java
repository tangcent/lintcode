package T20180917;

/**
 * Description
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 * <p>
 * Have you consider that the string might be empty? This is a good question to ask during an interview.
 * <p>
 * For the purpose of this problem, we define empty string as valid palindrome.
 * <p>
 * Example
 * "A man, a plan, a canal: Panama" is a palindrome.
 * <p>
 * "race a car" is not a palindrome.
 * <p>
 * Challenge
 * O(n) time without extra memory.
 */
public class ValidPalindrome {


    /**
     * @param s: A string
     * @return: Whether the string is a valid palindrome
     */
    public boolean isPalindrome(String s) {
        // write your code here
        if (s == null) {
            return true;
        }

        try {
            int a = 0;
            int b = s.length() - 1;

            while (a < b) {

                int aC = getChar(s, a++);
                while (aC == -1) {
                    aC = getChar(s, a++);
                }

                int bC = getChar(s, b--);
                while (bC == -1) {
                    bC = getChar(s, b--);
                }

                if (aC != bC) {
                    return false;
                }

            }
        } catch (Exception e) {
            return true;
        }

        return true;
    }

    private int getChar(String s, int index) {
        char ch = s.charAt(index);
        if (96 < ch && ch < 123) {
            return ch;
        }
        if (47 < ch && ch < 58) {
            return ch;
        }
        if (64 < ch && ch < 91) {
            return ch + 32;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(new ValidPalindrome().isPalindrome("abaBA"));
    }
}
