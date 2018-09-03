package T20161217;

import java.util.Stack;

/**
 * Valid Parentheses
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
 */
public class ValidParentheses {
    /**
     * @param s A string
     * @return whether the string is a valid parentheses
     */
    public boolean isValidParentheses(String s) {
        if (s == null)
            return true;
        char[] chars = s.toCharArray();
        Stack<Integer> stack = new Stack<>();
        int index = 0;
        for (char c : chars) {
            index = open.indexOf(c);
            if (index != -1) {
                stack.push(index);
                continue;
            }
            index = close.indexOf(c);
            if (index != -1) {
                if (stack.isEmpty() || (index != stack.pop())) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static String open = "{([";
    private static String close = "})]";

    public static void main(String args[]) {
        System.out.print(new ValidParentheses().isValidParentheses("}"));
    }
}
