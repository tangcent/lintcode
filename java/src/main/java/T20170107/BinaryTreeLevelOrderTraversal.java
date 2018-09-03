package T20170107;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by TomNg on 2017/2/8.
 */
public class BinaryTreeLevelOrderTraversal {

    public class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    public class TreeNodeWrap {
        public TreeNode node;
        public int level;

        public TreeNodeWrap(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }

        public int val() {
            return node.val;
        }

        public boolean hasLeft() {
            return node.left != null;
        }

        public TreeNodeWrap left() {
            return new TreeNodeWrap(node.left, level + 1);
        }

        public TreeNodeWrap right() {
            return new TreeNodeWrap(node.right, level + 1);
        }

        public boolean hasRight() {
            return node.right != null;
        }
    }

    /**
     * @param root: The root of binary tree.
     * @return: Level order a list of lists of integer
     */
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        // write your code here
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Stack<TreeNodeWrap> stack = new Stack<>();
        stack.push(new TreeNodeWrap(root, 0));
        while (!stack.isEmpty()) {
            TreeNodeWrap node = stack.pop();
            ArrayList<Integer> list = getList(result, node.level);
            list.add(node.val());
            if (node.hasRight()) {
                stack.push(node.right());
            }
            if (node.hasLeft()) {
                stack.push(node.left());
            }
        }
        return result;
    }

    private ArrayList<Integer> getList(ArrayList<ArrayList<Integer>> listArrayList, int index) {
        if (listArrayList.size() == index) {
            ArrayList<Integer> list = new ArrayList<>();
            listArrayList.add(list);
            return list;
        }
        return listArrayList.get(index);
    }
}
