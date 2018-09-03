package T20161222;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by TomNg on 2016/12/22.
 */
public class ValidPalindrome {
    /**
     * @param s A string
     * @return Whether the string is a valid palindrome
     */
    public boolean isPalindrome(String s) {
        // Write your code here
        if (s == null || s.equals(""))
            return true;
        s = s.toLowerCase();
        int start = 0;
        int end = s.length() - 1;
        char a;
        char b;
        while (start < end) {
            while (!isValid(a = s.charAt(start)) && start < end) {
                ++start;
            }
            while (!isValid(b = s.charAt(end)) && start < end) {
                --end;
            }
            if (start >= end)
                break;
            if (a != b)
                return false;
            ++start;
            --end;
        }
        return true;
    }

    private boolean isValid(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'z');
    }

    public static void main(String args[]) {
        System.out.print(new ValidPalindrome().isPalindrome("Ab"));
    }
}
