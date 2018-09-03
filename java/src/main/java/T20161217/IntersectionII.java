package T20161217;

import java.util.*;

/**
 * Intersection of Two Arrays II
 * http://www.lintcode.com/en/problem/intersection-of-two-arrays-ii/
 * Given two arrays, write a function to compute their intersection.
 * <p>
 * Notice
 * Each element in the result should appear as many times as it shows in both arrays.
 * The result can be in any order.
 * Example
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
 */
public class IntersectionII {
    /**
     * @param nums1 an integer array
     * @param nums2 an integer array
     * @return an integer array
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Integer time;
        for (int num : nums1) {
            time = map.get(num);
            map.put(num, (time == null ? 1 : (time + 1)));
        }
        List<Integer> resultList = new ArrayList<>();
        for (int num : nums2) {
            time = map.get(num);
            if (time != null && time > 0) {
                resultList.add(num);
                map.put(num, time - 1);
            }
        }
        int[] result = new int[resultList.size()];
        int index = 0;
        for (Integer num : resultList) {
            result[index++] = num;
        }
        return result;
    }

    public static void main(String args[]) {
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 87, 9, 9, 4, 3};
        int[] b = new int[]{1, 2, 33, 4, 53, 6, 89, 92, 4, 3};
        int[] c = new IntersectionII().intersection(a, b);
        System.out.print(c.length);
    }
}
