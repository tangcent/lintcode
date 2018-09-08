package T20180907;

/**
 * Description
 * Given an integer, return its base 7 string representation.
 * <p>
 * The input will be in range of [-1e7, 1e7].
 * <p>
 * Example
 * Given num = 100, return "202".
 * <p>
 * Given num = -7, return "-10".
 */
public class Base7 {

    /**
     * @param num: the given number
     * @return: The base 7 string representation
     */
    public String convertToBase7(int num) {
        // Write your code here
        boolean negative = false;
        if (num < 0) {
            negative = true;
            num = -num;
        }

        StringBuilder sb = new StringBuilder(10);
        while (num > 0) {
            sb.append(digits[num % 7]);
            num = num / 7;
        }
        if (negative) {
            sb.append('-');
        }
        return sb.reverse().toString();
    }

    private final static char[] digits = {
            '0', '1', '2', '3', '4', '5', '6'
    };
}
