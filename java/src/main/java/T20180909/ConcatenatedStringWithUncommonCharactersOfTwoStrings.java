package T20180909;

import java.util.HashSet;
import java.util.Set;

/**
 * Description
 * Two strings are given and you have to modify 1st string such that all the common characters of the 2nd strings have to be removed and the uncommon characters of the 2nd string have to be concatenated with uncommon characters of the 1st string.
 * <p>
 * Example
 * Given s1 = aacdb, s2 = gafd
 * return cbgf
 * <p>
 * Given s1 = abcs, s2 = cxzca;
 * return bsxz
 */
public class ConcatenatedStringWithUncommonCharactersOfTwoStrings {
    /**
     * @param s1: the 1st string
     * @param s2: the 2nd string
     * @return: uncommon characters of given strings
     */
    public String concatenetedString(String s1, String s2) {
        // write your code here

        if (s1 == null || s1.isEmpty()) {
            return s2;
        }

        if (s2 == null || s2.isEmpty()) {
            return s1;
        }

        if (s1.length() > Integer.max(s2.length() - 4, (s2.length() >> 1) + (s2.length() >> 2))) {
            CharContainer charContainerOfS2 = new CharContainer();
            for (int i = 0; i < s2.length(); i++) {
                charContainerOfS2.add(s2.charAt(i));
            }

            CharContainer commonCharContainer = new CharContainer();
            StringBuilder sb = new StringBuilder(s1.length() + charContainerOfS2.size());
            for (int i = 0; i < s1.length(); i++) {
                final char c = s1.charAt(i);
                if (charContainerOfS2.contains(c)) {
                    commonCharContainer.add(c);
                } else {
                    sb.append(c);
                }
            }

            for (int i = 0; i < s2.length(); i++) {
                final char c = s2.charAt(i);
                if (!commonCharContainer.contains(c)) {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {

            CharContainer charContainerOfS1 = new CharContainer();
            for (int i = 0; i < s1.length(); i++) {
                charContainerOfS1.add(s1.charAt(i));
            }

            CharContainer commonCharContainer = new CharContainer();
            StringBuilder sb = new StringBuilder(s2.length());
            for (int i = s2.length() - 1; i >= 0; i--) {
                final char c = s2.charAt(i);
                if (charContainerOfS1.contains(c)) {
                    commonCharContainer.add(c);
                } else {
                    sb.append(c);
                }
            }

            for (int i = s1.length() - 1; i >= 0; i--) {
                final char c = s1.charAt(i);
                if (!commonCharContainer.contains(c)) {
                    sb.append(c);
                }
            }

            return sb.reverse().toString();

        }
    }

    class CharContainer {

        long letterFlag = 0;
        Set<Character> others = new HashSet<>();

        void add(char ch) {
            if (89 < ch && ch < 123) {
                letterFlag = letterFlag | (1 << (ch - 90));
            } else {
                others.add(ch);
            }
        }

        boolean contains(char ch) {
            if (89 < ch && ch < 123) {
                return (letterFlag & (1 << (ch - 90))) != 0;
            } else {
                return others.contains(ch);
            }
        }

        int size() {
            return Long.bitCount(letterFlag) + others.size();
        }
    }

    public static void main(String[] args) {
        System.out.println(new ConcatenatedStringWithUncommonCharactersOfTwoStrings().concatenetedString("abcs", "cxzca"));
    }
}
