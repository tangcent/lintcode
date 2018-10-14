package T20181014.singleNumberII;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * Given 3*n + 1 numbers, every numbers occurs triple times except one, find it.
 * <p>
 * Example
 * Given [1,1,2,3,3,3,2,2,4,1] return 4
 * <p>
 * Challenge
 * One-pass, constant extra space.
 */
public class SingleNumber {
    /**
     * @param A: An integer array
     * @return: An integer
     */
    public int singleNumberII(int[] A) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : A) {
            Integer times = map.get(i);
            if (times == null) {
                map.put(i, 1);
            } else if (times == 1) {
                map.put(i, 2);
            } else {
                map.remove(i);
            }
        }
        return map.keySet().iterator().next();
    }

    public static void main(String[] args) {

        System.out.println(new SingleNumber().singleNumberII(new int[]{1,1,2,3,3,3,2,2,4,1}));
    }
}
