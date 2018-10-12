package T20181012;

/**
 * Description
 * Given a string representing an expression of fraction addition and subtraction, you need to return the calculation result in string format. The final result should be irreducible fraction. If your final result is an integer, say 2, you need to change it to the format of fraction that has denominator 1. So in this case, 2 should be converted to 2/1.
 * <p>
 * The input string only contains '0' to '9', '/', '+' and '-'. So does the output.
 * Each fraction (input and output) has format ±numerator/denominator. If the first input fraction or the output is positive, then '+' will be omitted.
 * The input only contains valid irreducible fractions, where the numerator and denominator of each fraction will always be in the range [1,10]. If the denominator is 1, it means this fraction is actually an integer in a fraction format defined above.
 * The number of given fractions will be in the range [1,10].
 * The numerator and denominator of the final result are guaranteed to be valid and in the range of 32-bit int.
 * <p>
 * Example
 * Input:"-1/2+1/2"
 * Output: "0/1"
 * <p>
 * Input:"-1/2+1/2+1/3"
 * Output: "1/3"
 * <p>
 * Input:"1/3-1/2"
 * Output: "-1/6"
 * <p>
 * Input:"5/3+1/3"
 * Output: "2/1"
 * 0
 */
public class FractionAdditionAndSubtraction {

    /**
     * @param expression: a string
     * @return: return a string
     */
    public String fractionAddition(String expression) {
        // write your code here
        Reader reader = new Reader(expression);
        Fraction fraction = reader.readFraction();

        while (reader.hasMore()) {
            int symbol = reader.readSymbol();
            Fraction next = reader.readFraction();
            if (symbol == 1) {
                next.negative();
            }
            fraction.add(next);
        }
        return fraction.toString();
    }

    private static class Reader {
        String expression;
        int index = 0;

        boolean hasMore() {
            return index < expression.length();
        }

        public Reader(String expression) {
            this.expression = expression;
        }

        private Fraction readFraction() {
            Fraction fraction = new Fraction();
            int symbol = 0;//0->+,1->-
            char next = expression.charAt(index);
            if (next == '-') {
                fraction.negative();
                ++index;
            } else if (next == '+') {
                ++index;
            }
            while ((next = expression.charAt(index++)) != '/') {
                fraction.numerator *= 10;
                fraction.numerator += next - '0';
            }
            while (index < expression.length() && (next = expression.charAt(index)) != '/') {
                if (next >= '0' && next <= '9') {
                    ++index;
                    fraction.denominator *= 10;
                    fraction.denominator += next - '0';
                } else return fraction;
            }
            return fraction;
        }

        private int readSymbol() {
            char next = expression.charAt(index++);
            if (next == '-') {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * mean numerator/denominator
     * a/b+c/d = (ad+bc)/bd
     */
    private static class Fraction {

        private int symbol = 0;//0->+,1->-
        private long numerator;
        private long denominator;

        private void negative() {
            this.symbol = this.symbol ^ 1;
        }

        private static long gcd(long n1, long n2) {

            if (n1 < n2) {
                n1 = n1 + n2;
                n2 = n1 - n2;
                n1 = n1 - n2;
            }

            if (n2 == 0) {
                return n1;
            }

            long rest = n1 % n2;
            if (rest == 0) {
                return n2;
            }

            for (; ; ) {
                n1 = rest;

                if (n1 < n2) {
                    n1 = n1 + n2;
                    n2 = n1 - n2;
                    n1 = n1 - n2;
                }
                rest = n1 % n2;
                if (rest == 0) {
                    return n2;
                }
            }
        }

        private void add(Fraction other) {
            long gcd = gcd(denominator, other.denominator);
            long lcm = denominator / gcd * other.denominator;

            if (symbol == 1) {
                numerator = -numerator;
            }
            if (other.symbol == 1) {
                other.numerator = -other.numerator;
            }
            numerator = numerator * lcm / denominator + other.numerator * lcm / other.denominator;
            if (numerator < 0) {
                symbol = 1;
                numerator = -numerator;
            } else {
                symbol = 0;
            }

            denominator = lcm;
            gcd = gcd(numerator, denominator);
            numerator /= gcd;

            denominator /= gcd;
        }

        @Override
        public String toString() {
            return String.format("%s%d/%d", symbol == 0 ? "" : "-", numerator, denominator);
        }
    }

    public static void main(String[] args) {
        System.out.println(new FractionAdditionAndSubtraction().fractionAddition("-10/7+1/9+2/7+2/1-1/3+3/10-1/10+8/7-4/9-3/2"));
    }
}
