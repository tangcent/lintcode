package T20161225;

import java.util.Arrays;

/**
 * Partition Array by Odd and Even
 * Partition an integers array into odd number first and even number second.
 * Example
 * Given [1, 2, 3, 4], return [1, 3, 2, 4]
 */
public class PartitionArrayByOddAndEven {
    /**
     * @param nums: an array of integers
     * @return: nothing
     */
    public void partitionArray(int[] nums) {
        int start = 0;
        int end = nums.length - 1;
        int tmp;
        while (true) {
            while (start < end) {
                //get odd
                if ((nums[start] & 1) == 0) {
                    break;
                }
                ++start;
            }
            while (start < end) {
                if ((nums[end] & 1) != 0) {
                    break;
                }
                --end;
            }
            if (start < end) {
                tmp = nums[start];
                nums[start] = nums[end];
                nums[end] = tmp;
            } else {
                break;
            }
        }
    }

    public static void main(String args[]) {
        PartitionArrayByOddAndEven p = new PartitionArrayByOddAndEven();
        int[] nums = new int[]{1, 3, 2, 4, 5, 7, 7, 7, 9, 4, 6, 8, 9};
        p.partitionArray(nums);
        System.out.print(Arrays.toString(nums));
    }
}
