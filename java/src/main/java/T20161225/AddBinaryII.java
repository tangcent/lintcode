package T20161225;

/**
 * Add Binary
 * Given two binary strings, return their sum (also a binary string).
 * Example
 * a = 11
 * <p>
 * b = 1
 * <p>
 * Return 100
 */
public class AddBinaryII {

    /**
     * @param a a number
     * @param b a number
     * @return the result
     */
    public String addBinary(String a, String b) {
        return a.length() > b.length() ? doAddBinary(a, b) : doAddBinary(b, a);
    }

    /**
     * just make sure a long than b
     *
     * @param a a number
     * @param b a number
     * @return the result
     */
    private String doAddBinary(String a, String b) {
        char[] result = new char[a.length() + 1];
        int next = b.length() + 1;
        int add = 0;
        int carry = 0;
        int index;
        //first step
        for (index = 1; index < next; index++) {
            add = parse(a.charAt(a.length() - index)) + parse(b.charAt(b.length() - index)) + carry;
            if (add == 0) {
                result[result.length - index] = zero;
                carry = 0;
            } else if (add == 1) {
                result[result.length - index] = one;
                carry = 0;
            } else if (add == 2) {
                result[result.length - index] = zero;
                carry = 1;
            } else if (add == 3) {
                result[result.length - index] = one;
                carry = 1;
            }
        }

        //second step
        next = a.length() + 1;
        for (; index < next && carry == 1; index++) {
            add = parse(a.charAt(a.length() - index)) + 1;
            if (add == 0) {
                result[result.length - index] = zero;
                carry = 0;
            } else if (add == 1) {
                result[result.length - index] = one;
                carry = 0;
            } else if (add == 2) {
                result[result.length - index] = zero;
                continue;
            }
            index++;
            break;
        }

        //rest
        for (; index < next; index++) {
            result[result.length - index] = a.charAt(a.length() - index);
        }
        //resolve the carry
        if (carry == 1) {
            result[0] = one;
            return new String(result);
        } else {
            return new String(result, 1, result.length - 1);
        }
    }

    private int parse(char c) {
        return c == zero ? 0 : 1;
    }

    private final char zero = '0';
    private final char one = '1';


    public static void main(String args[]) {
        AddBinaryII addBinary = new AddBinaryII();
        String a = "101111";
        String b = "10";
        String r = addBinary.addBinary(a, b);
        System.out.print(a + "+" + b + "-->" + r + "\n");
        System.out.print(a.length() + "+" + b.length() + "-->" + r.length() + "\n");
    }
}
