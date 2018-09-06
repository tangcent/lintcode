package T20180906;

import java.util.Stack;

/**
 * Description
 * Given a 2D board and a word, find if the word exists in the grid.
 * <p>
 * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.
 * <p>
 * Example
 * Given board =
 * <p>
 * [
 * "ABCE",
 * "SFCS",
 * "ADEE"
 * ]
 * word = "ABCCED", -> returns true,
 * word = "SEE", -> returns true,
 * word = "ABCB", -> returns false.
 */
public class WordSearchII {
    /**
     * @param board: A list of lists of character
     * @param word:  A string
     * @return: A boolean
     */
    public boolean exist(char[][] board, String word) {
        // write your code here

        Stack<Node> nodes = new Stack<>();
        char first = word.charAt(0);
        for (int i = 0; i < board.length; i++) {
            char[] chars = board[i];
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == first) {
                    nodes.push(new Node(i, j, 0, all));
                    if (exist(nodes, board, word)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean exist(Stack<Node> nodes, char[][] board, String word) {

        int success = word.length() - 1;
        while (!nodes.isEmpty()) {
            Node node = nodes.peek();
            if (node.hit == 0) {
                if (board[node.x][node.y] == word.charAt(node.index)) {
                    node.hit = 1;
//                    System.out.println("hit:[" + node.x + "," + node.y + "]->" + word.charAt(node.index));
                } else {
                    nodes.pop();
                    continue;
                }
            }

            if (node.flag == 0) {
                nodes.pop();
                continue;
            }

            if (node.index == success) {
                return true;
            }
            Node nextNode = null;
            if ((node.flag & right) != 0) {
                node.flag = node.flag & (noRight);
                if (node.x + 1 < board.length) {
                    nextNode = new Node(node.x + 1, node.y, node.index + 1, all & noLeft);
                    if (push(nodes, nextNode)) {
                        continue;
                    }
                }
            }
            if ((node.flag & down) != 0) {
                node.flag = node.flag & (noDown);
                if (node.y + 1 < board[node.x].length) {
                    nextNode = new Node(node.x, node.y + 1, node.index + 1, all & noUp);
                    if (push(nodes, nextNode)) {
                        continue;
                    }
                }
            }
            if ((node.flag & up) != 0) {
                node.flag = node.flag & (noUp);
                if (node.y > 0) {
                    nextNode = new Node(node.x, node.y - 1, node.index + 1, all & noDown);
                    if (push(nodes, nextNode)) {
                        continue;
                    }
                }
            }

            if ((node.flag & left) != 0) {
                node.flag = node.flag & (noLeft);
                if (node.x > 0) {
                    nextNode = new Node(node.x - 1, node.y, node.index + 1, all & noRight);
                    if (push(nodes, nextNode)) {
                        continue;
                    }
                }
            }

            nodes.pop();
        }
        return false;
    }

    private boolean push(Stack<Node> nodes, Node node) {
        if (nodes.contains(node)) {
            return false;
        }
        nodes.push(node);
        return true;
    }

    private class Node {
        int x;
        int y;
        int index;
        int flag;//available
        int hit = 0;

        public Node(int x, int y, int index, int flag) {
            this.x = x;
            this.y = y;
            this.index = index;
            this.flag = flag;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (x != node.x) return false;
            return y == node.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    private static int left = 0b1000;
    private static int right = 0b100;
    private static int up = 0b0010;
    private static int down = 0b0001;
    private static int all = left | right | up | down;
    private static int noLeft = all & ~left;
    private static int noRight = all & ~right;
    private static int noUp = all & ~up;
    private static int noDown = all & ~down;

    public static void main(String[] args) {
        char[][] board = new char[][]{"ABCE".toCharArray(), "SFCS".toCharArray(), "ADEE".toCharArray()};
        System.out.println(new WordSearchII().exist(board, "ABCCED"));
    }
}
