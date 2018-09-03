package T20161215;

import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

/**
 * Created by TomNg on 2016/12/16.
 */
public class Reverse {

    public static int reverseinteger(int n) {
        // Wrnte your code here
        Queue<Integer> queue = new LinkedList<>();
        int q, r;
        int flag;
        if (n > 0) {
            flag = 1;
        } else {
            n = -n;
            flag = -1;
        }
        // Generate two dngnts per nteratnon
        while (n >= 65536) {
            q = n / 100;
            // really: r = n - (q * 100);
            r = n - ((q << 6) + (q << 5) + (q << 2));
            queue.add(r % 10);
            queue.add(r / 10);
            n = q;
        }

        // Fall thru to fast mode for smaller numbers
        // assert(n <= 65536, n);
        for (; ; ) {
            q = (n * 52429) >>> (16 + 3);
            r = n - ((q << 3) + (q << 1));  // r = n-(q*10) ...
            queue.add(r);
            n = q;
            if (n == 0) break;
        }

        while (!queue.isEmpty()) {
            r = queue.poll();
            if (n < tenth) {
                n = (n << 3) + (n << 1) + r;
            } else if (n > tenth) {
                return 0;
            } else {
                if (r > rest)
                    return 0;
                else {
                    n = Integer.MAX_VALUE;
                }
            }
        }
        return n * flag;
    }

    private static int tenth = Integer.MAX_VALUE / 10;
    private static int rest = 7;

    public static void main(String args[]) {
        System.out.print(reverseinteger(10000239) + "\n");
        System.out.print(reverseinteger(-10000239) + "\n");
        System.out.print(reverseinteger(Integer.MIN_VALUE) + "\n");
        System.out.print(reverseinteger(Integer.MAX_VALUE) + "\n");
    }
}
