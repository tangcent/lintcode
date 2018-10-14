package T20181014;

import java.util.Stack;

/**
 * Description
 * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
 * <p>
 * An example is the root-to-leaf path 1->2->3 which represents the number 123.
 * <p>
 * Find the total sum of all root-to-leaf numbers.
 * <p>
 * A leaf is a node with no children.
 * <p>
 * Have you met this question in a real interview?
 * Example
 * Example:
 * <p>
 * Input: [1,2,3]
 * 1
 * / \
 * 2   3
 * Output: 25
 * Explanation:
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * Therefore, sum = 12 + 13 = 25.
 * Example 2:
 * <p>
 * Input: [4,9,0,5,1]
 * 4
 * / \
 * 9   0
 * / \
 * 5   1
 * Output: 1026
 * Explanation:
 * The root-to-leaf path 4->9->5 represents the number 495.
 * The root-to-leaf path 4->9->1 represents the number 491.
 * The root-to-leaf path 4->0 represents the number 40.
 * Therefore, sum = 495 + 491 + 40 = 1026.
 */
public class SumRoot2LeafNumbers {

    public class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    /**
     * @param root: the root of the tree
     * @return: the total sum of all root-to-leaf numbers
     */
    public int sumNumbers(TreeNode root) {
        Stack<TreeNodeWithPrefix> stack = new Stack<>();

        stack.push(new TreeNodeWithPrefix(root, 0));

        int sum = 0;
        TreeNodeWithPrefix node;
        while (!stack.isEmpty()) {
            node = stack.pop();

            int val = node.prefix * 10 + node.node.val;

            if (node.node.left == null && node.node.right == null) {
                sum += val;
                continue;
            }

            if (node.node.left != null) {
                stack.push(new TreeNodeWithPrefix(node.node.left, val));
            }

            if (node.node.right != null) {
                stack.push(new TreeNodeWithPrefix(node.node.right, val));
            }
        }
        return sum;
    }

    public static class TreeNodeWithPrefix {
        TreeNode node;
        int prefix;

        public TreeNodeWithPrefix(TreeNode node, int prefix) {
            this.node = node;
            this.prefix = prefix;
        }
    }
}
