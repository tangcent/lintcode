package T20161227;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by TomNg on 2016/12/27.
 */
public class HappyNumber {

    /**
     * @param n an integer
     * @return true if this is a happy number or false
     */
    public boolean isHappy(int n) {
        if (n == 1) {
            return true;
        }
        Set<Integer> set = new HashSet<>();
        int newInt;
        do {
            set.add(n);
            n = next(n);
            if (n == 1) {
                return true;
            }
        } while (!set.contains(n));
        return false;
    }

    private int next(int n) {
        int next = 0;
        while (n > 0) {
            int remainder = n % 10;
            next += remainder * remainder;
            n = n / 10;
        }
        return next;
    }
}
