package T20180907;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * Given an array of integers, the majority number is the number that occurs more than 1/3 of the size of the array.
 *
 * Find it.
 *
 * There is only one majority number in the array.
 *
 * Example
 * Given [1, 2, 1, 2, 1, 3, 3], return 1.
 *
 * Challenge
 * O(n) time and O(1) extra space.
 */
public class MajorityElementII {

    /*
     * @param nums: a list of integers
     * @return: find a  majority number
     */
    public int majorityNumber(List<Integer> nums) {
        // write your code here

        Map<Integer, Count> majoritys = new HashMap<>();

        int size = nums.size();
        int middleSize = size >> 1;
        int standard = size / 3;

        for (int i = 0; i < middleSize; i++) {
            Integer num = nums.get(i);
            if (majoritys.containsKey(num)) {
                majoritys.get(num).count += 1;
            } else {
                majoritys.put(num, new Count(1));
            }
        }


        for (int i = middleSize; i < size; i++) {
            Integer num = nums.get(i);
            if (majoritys.containsKey(num)) {
                final Count count = majoritys.get(num);
                if (++count.count > standard) {
                    return num;
                }
            } else {
                majoritys.put(num, new Count(1));
            }
        }

        if (majoritys.size() > 0) {
            for (Integer num : majoritys.keySet()) {
                if (majoritys.get(num).count > standard) {
                    return num;
                }
            }
        }

        return -1;
    }

    private class Count {
        private int count;

        public Count(int count) {
            this.count = count;
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(99);
        list.add(2);
        list.add(99);
        list.add(2);
        list.add(99);
        list.add(3);
        list.add(3);
        System.out.println(new MajorityElementII().majorityNumber(list));
    }

}
