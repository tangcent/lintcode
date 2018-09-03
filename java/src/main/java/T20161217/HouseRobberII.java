package T20161217;

/**
 * Created by TomNg on 2016/12/17.
 */
public class HouseRobberII {

    /**
     * @param nums: An array of non-negative integers.
     *              return: The maximum amount of money you can rob tonight
     */
    public int houseRobber(int[] nums) {
        //region check
        if (nums == null) {
            return 0;
        }
        if (nums.length < 3) {
            if (nums.length == 1)
                return nums[0];
            else if (nums.length == 2) {
                return Math.max(nums[0], nums[1]);
            }
        }
        //endregion

        int len = nums.length;
        return Math.max(houseRobber(nums, 0, len - 1), houseRobber(nums, 1, len));
    }

    public int houseRobber(int[] nums, int start, int end) {

        int len = nums.length;
        int[] dp = new int[len + 1];// max for 0-i
        dp[start] = 0;
        dp[start + 1] = nums[start];
        for (int s = start + 2; s <= end; s++) {
            dp[s] = Math.max(dp[s - 1], dp[s - 2] + nums[s - 1]);
        }
        return dp[end];
    }

    public static void main(String args[]) {
        int[] a = new int[]{828, 125, 740, 724, 983, 624, 814, 103, 294, 388};
//        int[] a = new int[]{1, 3, 2};
        int c = new HouseRobberII().houseRobber(a);
        System.out.print(c);
    }
}
