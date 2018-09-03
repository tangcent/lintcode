package T20161224;

/**
 * Sqrt(x)
 * Implement int sqrt(int x).
 * <p>
 * Compute and return the square root of x.
 * Example
 * sqrt(3) = 1
 * <p>
 * sqrt(4) = 2
 * <p>
 * sqrt(5) = 2
 * <p>
 * sqrt(10) = 3
 */
public class Sqrtx {
    /**
     * @param x: An integer
     * @return: The sqrt of x
     */
    public int sqrt(int x) {
        if (x == 0)
            return 0;
        if (x == 1)
            return 1;
        int start = 1;
        int end = (x >> 1) + 1;
        int mid;
        long square;
        while (start != end) {
            mid = (start + end) >> 1;
            square = (long) mid * mid;
            if (square == x)
                return mid;
            if (square > x) {
                end = mid;
            } else {
                if (start == mid)
                    return start;
                start = mid;
            }
        }
        return start;
    }

    public static void main(String args[]) {
        Sqrtx sqrtx = new Sqrtx();
        System.out.print(5 + "-->" + sqrtx.sqrt(5) + "\n");
        for (int i = 0; i < 100; i++) {
            System.out.print(i + "-->" + sqrtx.sqrt(i) + "\n");
        }
    }
}
