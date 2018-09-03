package T20161217;

import java.util.HashSet;
import java.util.Set;

/**
 * Intersection of Two Arrays
 * Given two arrays, write a function to compute their intersection.
 * <p>
 * Notice
 * <p>
 * Each element in the result must be unique.
 * The result can be in any order.
 * Example
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].
 */
public class Intersection {
    /**
     * @param nums1 an integer array
     * @param nums2 an integer array
     * @return an integer array
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums1) {
            set.add(num);
        }
        Set<Integer> resultSet = new HashSet<>();
        for (int num : nums2) {
            if (set.contains(num))
                resultSet.add(num);
        }
        int[] result = new int[resultSet.size()];
        int index = 0;
        for (Integer num : resultSet) {
            result[index++] = num;
        }
        return result;
        // Write your code here
    }

    public static void main(String args[]) {
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 87, 9, 9, 4, 3};
        int[] b = new int[]{1, 2, 33, 4, 53, 6, 89, 92, 4, 3};
        int[] c = new Intersection().intersection(a, b);
        System.out.print(c.length);
    }
}
