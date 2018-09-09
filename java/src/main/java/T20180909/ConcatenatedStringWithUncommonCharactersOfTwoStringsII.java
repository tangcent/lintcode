package T20180909;

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
public class ConcatenatedStringWithUncommonCharactersOfTwoStringsII {
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
    }

    class CharContainer {

        long letterFlag = 0;

        void add(char ch) {
            letterFlag = letterFlag | (1 << (ch - 90));
        }

        boolean contains(char ch) {
            return (letterFlag & (1 << (ch - 90))) != 0;
        }

        int size() {
            return Long.bitCount(letterFlag);
        }
    }

    public static void main(String[] args) {
        System.out.println(new ConcatenatedStringWithUncommonCharactersOfTwoStringsII().concatenetedString("abcs", "cxzca"));
    }
}
