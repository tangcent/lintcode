package T20180907;

import java.util.ArrayList;
import java.util.List;

public class MajorityElementBak {

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

        System.out.println(new MajorityElementBak().majorityNumber(list));
    }

}
