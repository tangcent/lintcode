package T20180916;

/**
 * Description
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 * <p>
 * Assume a BST is defined as follows:
 * <p>
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 * A single node tree is a BST
 * Example
 * An example:
 * <p>
 * 2
 * / \
 * 1   4
 * / \
 * 3   5
 * The above binary tree is serialized as {2,1,4,#,#,3,5} (in level order).
 */
public class ValidateBinarySearchTree {

    public class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    /**
     * @param root: The root of binary tree.
     * @return: True if the binary tree is BST, or false
     */
    public boolean isValidBST(TreeNode root) {
        // write your code here
        if (root == null) {
            return true;
        }

        return (root.left == null || isValidBST(root.left, null, root.val))
                && (root.right == null || isValidBST(root.right, root.val, null));
    }


    public boolean isValidBST(TreeNode root, Integer min, Integer max) {
        return (min == null || root.val > min)
                && (max == null || root.val < max)
                && (root.left == null || isValidBST(root.left, min, root.val))
                && (root.right == null || isValidBST(root.right, root.val, max));
    }

}
