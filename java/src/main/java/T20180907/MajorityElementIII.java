package T20180907;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * Given an array of integers and a number k, the majority number is the number that occurs more than 1/k of the size of the array.
 * <p>
 * Find it.
 * <p>
 * There is only one majority number in the array.
 * <p>
 * Example
 * Given [3,1,2,3,2,3,3,4,4,4] and k=3, return 3.
 * <p>
 * Challenge
 * O(n) time and O(k) extra space
 */
public class MajorityElementIII {

    /*
     * @param nums: a list of integers
     * @return: find a  majority number
     */
    public int majorityNumber(List<Integer> nums, int k) {
        // write your code here

        Map<Integer, Count> majoritys = new HashMap<>();

        int size = nums.size();
        int standard = size / k;
        int preSize = Integer.min(size >> 1, standard + 2);

        for (int i = 0; i < preSize; i++) {
            Integer num = nums.get(i);
            if (majoritys.containsKey(num)) {
                majoritys.get(num).count += 1;
            } else {
                majoritys.put(num, new Count(1));
            }
        }


        for (int i = preSize; i < size; i++) {
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
        System.out.println(new MajorityElementIII().majorityNumber(list, 3));
    }

}
