package T20161225;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by TomNg on 2016/12/25.
 */
public class ValidNumber {
    /**
     * @param s the string that represents a number
     * @return whether the string is a valid number
     */
    public boolean isNumber(String s) {
        s = s.trim();
        try {
            if (s.equals("."))
                return false;
            int index = 0;
            int last = s.length() - 1;
            if (isSymbol(s.charAt(0)))
                ++index;
            int checkIndex = index;
            boolean check;
            while (isNum(s.charAt(index))) {
                if (index == last)
                    return true;
                index++;
            }
            check = checkIndex == index;
            char symbol = s.charAt(index);
            if (symbol == '.') {
                index++;
                if (index > last)
                    return true;
            } else if (symbol == 'e') {
                index++;
                if (check || index > last)
                    return false;
                if (isSymbol(s.charAt(index))) {
                    index++;
                    if (index > last)
                        return false;
                }
            } else {
                return false;
            }
            while (isNum(s.charAt(index))) {
                if (index == last)
                    return true;
                index++;
            }
            return false;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isSymbol(char c) {
        return c == '+' || c == '-';
    }


    public static void main(String args[]) {
        ValidNumber v = new ValidNumber();
        String s = ".";
        System.out.print(s + "-->" + v.isNumber(s));
    }
}
