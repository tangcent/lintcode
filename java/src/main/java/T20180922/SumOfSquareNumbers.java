package T20180922;

/**
 * Description
 * Given a integer c, your task is to decide whether there're two integers a and b such that a^2 + b^2 = c.
 * <p>
 * Example
 * Given n = 5
 * Return true // 1 * 1 + 2 * 2 = 5
 * <p>
 * Given n = -5
 * Return false
 */
public class SumOfSquareNumbers {

    /**
     * @param num: the given number
     * @return: whether whether there're two integers
     */
    public boolean checkSumOfSquareNumbers(int num) {
        // write your code here
        long n = (long) Math.sqrt(num);
        long nearest = n * n;
        if (nearest == num) {
            return true;
        }
        while (nearest < num) {
            ++n;
            nearest = n * n;
        }

        long max = ((long) num) << 1;
        for (; nearest <= max; ++n, nearest = n * n) {

            long diff = nearest - num;
            if ((diff & 1) != 0) {
                continue;
            }
            long delta = nearest - (diff << 1);

            double dsqrt = Math.sqrt(delta);
            int sqrt = (int) dsqrt;
            if (sqrt != dsqrt) {
                continue;
            }
            if (((n ^ sqrt) & 1) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new SumOfSquareNumbers().checkSumOfSquareNumbers(2147483647));
    }
}
