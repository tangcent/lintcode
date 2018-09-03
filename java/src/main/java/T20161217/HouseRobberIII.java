package T20161217;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * House RobberIII
 * <p>
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * <p>
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
 * Example
 * Given [3, 8, 4], return 8.
 */
public class HouseRobberIII {
    public class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int x) {
            val = x;
        }
    }

    /**
     * @param root: The root of binary tree.
     * @return: The maximum amount of money you can rob tonight
     */
    public int houseRobber3(TreeNode root) {
        return houseRobber3(root, new HashMap<TreeNode, Integer>());
    }

    public int houseRobber3(TreeNode root, Map<TreeNode, Integer> cache) {
        if (root == null) {
            return 0;
        }
        if (cache.containsKey(root)) {
            return cache.get(root);
        }
        if (root.left == null && root.right == null) {
            return Math.max(root.val, 0);
        }

        int rob = root.val;
        if (root.left != null) {
            rob += houseRobber3(root.left.left, cache);
            rob += houseRobber3(root.left.right, cache);
        }
        if (root.right != null) {
            rob += houseRobber3(root.right.left, cache);
            rob += houseRobber3(root.right.right, cache);
        }

        int noRob = houseRobber3(root.left, cache) + houseRobber3(root.right, cache);
        cache.put(root, Math.max(rob, noRob));
        return Math.max(rob, noRob);
    }
}
