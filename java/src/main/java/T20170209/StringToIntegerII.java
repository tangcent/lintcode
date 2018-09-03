package T20170209;

/**
 * Created by TomNg on 2017/2/9.
 * String to Integer II
 * Implement function atoi to convert a string to an integer.
 * <p>
 * If no valid conversion could be performed, a zero value is returned.
 * <p>
 * If the correct value is out of the range of representable values, INT_MAX (2147483647) or INT_MIN (-2147483648) is returned.
 * <p>
 * Example
 * "10" => 10
 * "-1" => -1
 * "123123123123123" => 2147483647
 * "1.0" => 1
 */
public class StringToIntegerII {
    /**
     * @param str: A string
     * @return An integer
     */
    public int atoi(String str) {
        //check null
        if (str == null) {
            return 0;
        }
        str = str.trim();
        if (str.length() == 0) {
            return 0;
        }

        long result = 0;
        boolean negative = false;
        int index = 0;
        long limit = 2147483647;
        //check negative
        if (str.charAt(0) == '-') {
            negative = true;
            ++index;
        } else if (str.charAt(0) == '+') {
            ++index;
        }
        if (str.length() == 0) {
            return 0;
        }
        for (; index < str.length(); ) {
            char ch = str.charAt(index++);
            if (ch >= '0' && ch <= '9') {
                result = result * 10 + (ch - '0');
                if (result > limit) {
                    return (int) (negative ? -limit - 1 : limit);
                }
            } else {
                break;
            }
        }

        return (int) (negative ? -result : result);
    }


    public static void main(String args[]) {
        System.out.print(new StringToIntegerII().atoi("2147483646"));
    }
}
