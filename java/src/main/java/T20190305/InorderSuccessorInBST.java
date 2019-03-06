package T20190305;

/**
 * https://www.lintcode.com/problem/inorder-successor-in-bst/description
 * <p>
 * Given a binary search tree (See Definition) and a node in it, find the in-order successor of that node in the BST.
 * <p>
 * If the given node has no in-order successor in the tree, return null.
 * <p>
 * Example
 * Example 1:
 * <p>
 * Input: tree = {1,#,2}, node value = 1
 * Output: 2
 * Explanation:
 * 1
 * \
 * 2
 * Example 2:
 * <p>
 * Input: tree = {2,1,3}, node value = 1
 * Output: 2
 * Explanation:
 * 2
 * / \
 * 1   3
 * Binary Tree Representation
 * <p>
 * Challenge
 * O(h), where h is the height of the BST.
 * <p>
 * Notice
 * It's guaranteed p is one node in the given tree. (You can directly compare the memory address to find p)
 */
public class InorderSuccessorInBST {

    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /*
     * @param root: The root of the BST.
     * @param p: You need find the successor node of p.
     * @return: Successor of p.
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        try {
            if (p.right != null) {
                TreeNode next = p.right;
                while (next.left != null) {
                    next = next.left;
                }
                return next;
            }
            TreeNode successor = root;
            while (successor.val < p.val) {
                successor = successor.right;
            }
            if (successor == p) return null;
            TreeNode findP = successor.left;
            while (findP != null) {
                if (findP.val > p.val) {
                    successor = findP;
                    findP = findP.left;
                } else {
                    findP = findP.right;
                }
            }
            return successor;
        } catch (NullPointerException e) {
            return null;
        }
    }
}
