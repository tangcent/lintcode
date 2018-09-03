package T20170104;

/**
 * Created by TomNg on 2017/1/4.
 */
public class Fibonacci {
    /**
     * @param n: an integer
     * @return an integer f(n)
     */
    public int fibonacci(int n) {
        // write your code here
        if (n == 1)
            return 0;
        if (n == 2 || n == 3)
            return 1;
        int p = 1;
        int pp = 1;
        int result = 1;
        while (n-- > -1) {
            result = p + pp;
            pp = p;
            p = result;
        }
        return result;
    }

    public static void main(String args[]) {
        System.out.print(new Fibonacci().fibonacci(5));
    }
}
