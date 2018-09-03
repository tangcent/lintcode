package T20161217;

import java.util.ArrayList;
import java.util.List;

/**
 * Backpack VI
 * Given an integer array nums with all positive numbers and no duplicates,
 * find the number of possible combinations that add up to a positive integer target.
 * Notice
 * <p>
 * The different sequences are counted as different combinations.
 * <p>
 * Example
 * Given nums = [1, 2, 4], target = 4
 * <p>
 * The possible combination ways are:
 * [1, 1, 1, 1]
 * [1, 1, 2]
 * [1, 2, 1]
 * [2, 1, 1]
 * [2, 2]
 * [4]
 * return 6
 */
public class Backpack {
    /**
     * @param nums   an integer array and all positive numbers, no duplicates
     * @param target an integer
     * @return an integer
     */
    public int backPackVI(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        sort(nums);
        for (int i = 1; i <= target; ++i) {
            for (int n : nums) {
                if (i < n) break;
                dp[i] += dp[i - n];
            }
        }
        return dp[target];
    }

    private void sort(int[] nums) {
        int temp;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; ++j) {
                if (nums[i] > nums[j]) {
                    temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }


    public static void main(String args[]) {
        int[] a = new int[]{4, 1, 2, 3};
        int c = new Backpack().backPackVI(a, 32);
        System.out.print(c);
    }
}