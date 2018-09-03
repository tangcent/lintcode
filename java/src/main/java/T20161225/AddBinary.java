package T20161225;

import com.sun.deploy.util.StringUtils;

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
public class AddBinary {

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
        StringBuilder sb = new StringBuilder();
        String ca, cb;
        int index;
        boolean carry = false;
        while (b.length() > 63) {
            System.out.print("a:" + a.length() + ",b:" + b.length() + ",r:" + sb.length() + "\n");
            index = a.length() - 63;
            ca = a.substring(index);
            a = a.substring(0, index);
            index = b.length() - 63;
            cb = b.substring(index);
            b = b.substring(0, index);
            carry = addLong(sb, ca, cb, carry);
        }

        //add rest b
        index = a.length() - b.length();
        ca = a.substring(index);
        a = a.substring(0, index);
        carry = addLong(sb, ca, b, carry);
        System.out.print("a:" + a.length() + ",b:" + b.length() + ",r:" + sb.length() + "\n");
        index = a.length() - 1;
        while (carry && index > -1) {
            char x = a.charAt(index--);
            if (x == '0') {
                sb.insert(0, '1');
                carry = false;
                break;
            } else {
                sb.insert(0, '0');
            }
        }
        if (index > -1) {
            sb.insert(0, a.substring(0, index + 1));
        } else if (carry) {
            sb.insert(0, '1');
        }
        // Write your code here
        return sb.toString();
    }

    private boolean addLong(StringBuilder sb, String a, String b, boolean carry) {
        Long add = parseLong(a) + parseLong(b);
        if (carry) {
            ++add;
        }
        String result = Long.toBinaryString(add);
        if (result.length() > a.length()) {
            sb.insert(0, result.substring(1));
            return true;
        } else if (result.length() < a.length()) {
            int pad = a.length() - result.length();
            char[] pads = new char[pad];
            for (int i = 0; i < pad; i++) {
                pads[i] = '0';
            }
            sb.insert(0, result);
            sb.insert(0, pads);
            return false;
        } else {
            sb.insert(0, result);
            return false;
        }
    }

    private long parseLong(String s) {
        long result = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) != '0') {
                result = result | (1 << (len - i - 1));
            }
        }
        return result;
    }

    public static void main(String args[]) {
        AddBinary addBinary = new AddBinary();
        String a = "10011111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        String b = "101010101010101010101010101010101010010101010010101010101010010101010010101010010101010010101010010101010110100101010101010101010010101010100101010101001010101001010101010101011111111111111111111111111111111100000000000000000000000000000000000000000111111111111111111111111111111111111";
        String r = addBinary.addBinary(a, b);
        System.out.print(a + "+" + b + "-->" + r + "\n");
        System.out.print(a.length() + "+" + b.length() + "-->" + r.length() + "\n");
    }
}
