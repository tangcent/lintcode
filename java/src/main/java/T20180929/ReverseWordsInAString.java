package T20180929;

/**
 * Description
 * Given an input string, reverse the string word by word.
 * <p>
 * For example,
 * Given s = "the sky is blue",
 * return "blue is sky the".
 * <p>
 * Clarification
 * What constitutes a word?
 * A sequence of non-space characters constitutes a word.
 * Could the input string contain leading or trailing spaces?
 * Yes. However, your reversed string should not contain leading or trailing spaces.
 * How about multiple spaces between two words?
 * Reduce them to a single space in the reversed string.
 * Example
 * 给出s = "the sky is blue"，返回"blue is sky the"
 */
public class ReverseWordsInAString {

    /*
     * @param s: A string
     * @return: A string
     */
    public String reverseWords(String s) {
        // write your code here
        char[] chars = s.toCharArray();
        char[] result = new char[chars.length];
        int pre = chars.length;
        int index = 0;
        int i;
        for (i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == ' ') {
                if (pre > i + 1) {
                    for (int j = i + 1; j < pre; j++) {
                        result[index++] = chars[j];
                    }
                    result[index++] = ' ';
                }
                pre = i;
            }
        }
        for (int j = 0; j < pre; j++) {
            result[index++] = chars[j];
        }

        return new String(result, 0, index);
    }

    public static void main(String[] args) {
        System.out.println(new ReverseWordsInAString().reverseWords("the d"));
    }
}
