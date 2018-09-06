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
public class WordSearch {
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
                if (board[i][j] == first) {
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

        while (!nodes.isEmpty()) {
            Node node = nodes.peek();
            if (node.hit == -1) {
                if (board[node.x][node.y] == word.charAt(node.index)) {
                    node.hit = 1;
//                    System.out.println("hit:[" + node.x + "," + node.y + "]->" + word.charAt(node.index));
                } else {
                    node.hit = 0;
                }
            }
            if (node.hit == 1) {//hit
                if (node.index == word.length() - 1) {
                    return true;
                }
                Node nextNode = null;
                if ((node.flag & right) != 0) {
                    node.flag = node.flag & (~right);
                    if (node.x + 1 < board.length) {
                        nextNode = new Node(node.x + 1, node.y, node.index + 1, all & ~left);
                        if (push(nodes, nextNode)) {
                            continue;
                        }
                    }
                }
                if ((node.flag & down) != 0) {
                    node.flag = node.flag & (~down);
                    if (node.y + 1 < board[node.x].length) {
                        nextNode = new Node(node.x, node.y + 1, node.index + 1, all & ~up);
                        if (push(nodes, nextNode)) {
                            continue;
                        }
                    }
                }
                if ((node.flag & up) != 0) {
                    node.flag = node.flag & (~up);
                    if (node.y > 0) {
                        nextNode = new Node(node.x, node.y - 1, node.index + 1, all & ~down);
                        if (push(nodes, nextNode)) {
                            continue;
                        }
                    }
                }

                if ((node.flag & left) != 0) {
                    node.flag = node.flag & (~left);
                    if (node.x > 0) {
                        nextNode = new Node(node.x - 1, node.y, node.index + 1, all & ~right);
                        if (push(nodes, nextNode)) {
                            continue;
                        }
                    }
                }

                nodes.pop();
            } else {
                nodes.pop();//丢弃
            }
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
        int hit = -1;

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

    public static void main(String[] args) {
        char[][] board = new char[][]{"ABCE".toCharArray(), "SFCS".toCharArray(), "ADEE".toCharArray()};
        System.out.println(new WordSearch().exist(board, "ABCCED"));
    }
}
