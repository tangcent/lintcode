package T20181014.singleNumber;

import java.util.HashSet;
import java.util.Set;

/**
 * Description
 * Given 2*n + 1 numbers, every numbers occurs twice except one, find it.
 * <p>
 * Example
 * Given [1,2,2,1,3,4,3], return 4
 * <p>
 * Challenge
 * One-pass, constant extra space.
 */
public class SingleNumber {

    /**
     * @param A: An integer array
     * @return: An integer
     */
    public int singleNumber(int[] A) {
        Set<Integer> sets = new HashSet<>();
        for (int i : A) {
            if (!sets.remove(i)) {
                sets.add(i);
            }
        }
        return sets.iterator().next();
    }
}
