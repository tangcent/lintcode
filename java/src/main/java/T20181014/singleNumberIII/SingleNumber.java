package T20181014.singleNumberIII;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description
 * Given 2*n + 2 numbers, every numbers occurs twice except two, find them.
 * <p>
 * Example
 * Given [1,2,2,3,4,4,5,3] return 1 and 5
 * <p>
 * Challenge
 * O(n) time, O(1) extra space.
 */
public class SingleNumber {
    /**
     * @param A: An integer array
     * @return: An integer
     */
    public List<Integer> singleNumberIII(int[] A) {
        Set<Integer> sets = new HashSet<>();
        for (int i : A) {
            if (!sets.remove(i)) {
                sets.add(i);
            }
        }
        return new ArrayList<>(sets);
    }

    public static void main(String[] args) {

        System.out.println(new SingleNumber().singleNumberIII(new int[]{1, 2, 2, 3, 4, 4, 5, 3}));
    }
}
