package T20161224;

import java.util.Stack;
import java.util.function.BooleanSupplier;

/**
 * Pow(x, n)
 * Implement pow(x, n).
 * <p>
 * Notice
 * <p>
 * You don't need to care about the precision of your answer, it's acceptable if the expected answer and your answer 's difference is smaller than 1e-3.
 * Example
 * Pow(2.1, 3) = 9.261
 * Pow(0, 1) = 0
 * Pow(1, 0) = 1
 */
public class Pow {
    /**
     * @param x the base number
     * @param n the power number
     * @return the result
     */
    public double myPow(double x, int n) {
        boolean positive = true;
        if (n < 0) {
            positive = false;
            n = -n;
        }
        if (n == 0) {
            return 1;
        }
        int len = 0;
        int stack = 0;
        while (n > 1) {
            stack = stack << 1;
            stack = stack | (n & 1);
            n = n >> 1;
            len++;
        }
        double result = x;
        for (int i = 0; i < len; i++) {
            result = result * result;
            if ((stack & 1) != 0) {
                result = result * x;
            }
            stack = stack >> 1;
        }
        return positive ? result : (1 / result);
    }


    public static void main(String args[]) {
        double x = 2.145;
        int n = 99;
        System.out.print(x + "^" + n + "=" + new Pow().myPow(x, n));
    }
}
