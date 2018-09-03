package T20161217;

/**
 * House Robber
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * <p>
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
 * Example
 * Given [3, 8, 4], return 8.
 */
public class HouseRobber {

    /**
     * @param A: An array of non-negative integers.
     *           return: The maximum amount of money you can rob tonight
     */
    public long houseRobber(int[] A) {
        if (A.length == 0)
            return 0;
        else if (A.length == 1)
            return A[0];
        long[] result = new long[]{A[0], A[1]};
        boolean flag = true;
        for (int i = 2; i < A.length; i++) {
            if (flag) {
                result[0] = max(A[i] + result[0], result[1]);
            } else {
                result[1] = max(A[i] + result[1], result[0]);
            }
            flag = !flag;
        }
        return max(result[0], result[1]);
    }

    private long max(long a, long b) {
        return a > b ? a : b;
    }

    public static void main(String args[]) {
        int[] a = new int[]{828, 125, 740, 724, 983, 624, 814, 103, 294, 388};
        long c = new HouseRobber().houseRobber(a);
        System.out.print(c);
    }
}
