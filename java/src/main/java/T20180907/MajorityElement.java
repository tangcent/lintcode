package T20180907;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Given an array of integers, the majority number is the number that occurs more than half of the size of the array. Find it.
 * <p>
 * You may assume that the array is non-empty and the majority number always exist in the array.
 * <p>
 * Example
 * Given [1, 1, 1, 1, 2, 2, 2], return 1
 * <p>
 * Challenge
 * O(n) time and O(1) extra space
 */
public class MajorityElement {

    /*
     * @param nums: a list of integers
     * @return: find a  majority number
     */
    public int majorityNumber(List<Integer> nums) {
        // write your code here

        int majority = nums.get(0);
        int count = 1;
        int size = nums.size();
        int middleSize = size >> 1;
        int i;
        for (i = 1; i < size; ++i) {
            int num = nums.get(i);
            if (count == 0) {
                majority = num;
                ++count;
            } else if (num == majority) {
                ++count;
            } else {
                --count;
            }
        }
        if (count > 0) {
            return majority;
        } else if (count == 0) {
            return -1;
        }
//        if (count == 0) {
//            return -1;
//        } else if (count > middleSize) {
//            return majority;
//        }
//
//        count = 0;
//
//        //8 4
//        //7 3
//        int pre = size - middleSize;
//        if ((size & 1) != 0) {
//            --pre;
//        }
//        for (i = 0; i < pre; ++i) {
//            int num = nums.get(i);
//            if (num == majority) {
//                ++count;
//            }
//        }
//        for (; i < size; ++i) {
//            int num = nums.get(i);
//            if (num == majority) {
//                ++count;
//                if (count > middleSize) {
//                    return majority;
//                }
//            }
//        }

        return -1;
    }

    public int majorityNumber2(List<Integer> nums) {
        // write your code here

        int majority = nums.get(0);
        int count = 1;
        int size = nums.size();
        int middleSize = size >> 1;
        for (int i = 1; i < middleSize; i++) {
            int num = nums.get(i);
            if (count == 0) {
                majority = num;
                ++count;
            } else if (num == majority) {
                ++count;
            } else {
                --count;
            }
        }

        int rest = size - middleSize;
        for (int i = middleSize; i < size; i++, rest--) {
            int num = nums.get(i);
            if (count == 0) {
                majority = num;
                ++count;
            } else if (num == majority) {
                if (++count > rest) {
                    return num;
                }
            } else {
                --count;
            }
        }

        return majority;

    }


    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);

        list.add(1);

        System.out.println(new MajorityElement().majorityNumber(list));
    }

}
