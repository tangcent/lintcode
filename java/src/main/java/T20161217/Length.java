package T20161217;

/**
 * Length of Last Word
 * Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word in the string.
 * <p>
 * If the last word does not exist, return 0.
 * <p>
 * Notice
 * <p>
 * A word is defined as a character sequence consists of non-space characters only.
 * Example
 * Given s = "Hello World", return 5.
 */
public class Length {
    /**
     * @param s A string
     * @return the length of last word
     */
    public int lengthOfLastWord(String s) {
        // Write your code here
        if (s == null)
            return 0;
        s = s.trim();
        int l = s.lastIndexOf(' ');
        return l == -1 ? (s.length() > 0 ? s.length() : 0) : (s.length() - l - 1);
    }


    public static void main(String args[]) {
        System.out.print(new Length().lengthOfLastWord(" 3 "));
    }
}
