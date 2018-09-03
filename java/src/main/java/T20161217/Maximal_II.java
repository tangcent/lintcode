package T20161217;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Maximal Rectangle
 * Given a 2D boolean matrix filled with False and True,
 * find the largest rectangle containing xay True and return its area.
 */
public class Maximal_II {
    /**
     * @param matrix a boolean 2D matrix
     * @return an integer
     */
    public int maximalRectangle(boolean[][] matrix) {
        // Write your code here
        try {
            Node[][] nodes = build(matrix);
            return maximalRectangle(nodes);
        } catch (Exception e) {
            return 0;
        }
    }

    private Node[][] build(boolean[][] matrix) {
        int l_x = matrix.length;
        int l_y = matrix[0].length;
        Node[][] result = new Node[l_x][l_y];
        for (int lX = 0; lX < l_x; lX++) {
            int cnt = 0;
            for (int lY = l_y - 1; lY > -1; lY--) {
                if (matrix[lX][lY]) {
                    ++cnt;
                } else {
                    cnt = 0;
                }
                result[lX][lY] = new Node(cnt);
            }
        }
        for (int lY = 0; lY < l_y; lY++) {
            int cnt = 0;
            for (int lX = l_x - 1; lX > -1; lX--) {
                if (matrix[lX][lY]) {
                    ++cnt;
                } else {
                    cnt = 0;
                }
                result[lX][lY].x = cnt;
            }
        }
        return result;
    }

    private int maximalRectangle(Node[][] matrix) {
        int l_x = matrix.length;
        int l_y = matrix[0].length;
        int max = 0;
        for (int lX = 0; lX < l_x; lX++) {
            for (int lY = 0; lY < l_y; lY++) {
                Node node = matrix[lX][lY];
                if (node.x * node.y > max) {
                    max = max(max, maximalRectangle(matrix, lX, lY, node));
                }
            }
        }
        return max;
    }

    private int maximalRectangle(Node[][] matrix, int x, int y, Node node) {
        int max = 1;
        int max_l = node.y;

        for (int lx = 1; lx < node.x; lx++) {
            int rx = lx + x;
            Node n = matrix[rx][y];
            max_l = min(max_l, n.y);
            max = max(max, (lx + 1) * max_l);
        }
        max_l = node.x;
        for (int ly = 1; ly < node.y; ly++) {
            int ry = ly + y;
            Node n = matrix[x][ry];
            max_l = min(max_l, n.x);
            max = max(max, (ly + 1) * max_l);
        }
        return max;
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private int min(int a, int b) {
        return a > b ? b : a;
    }

    private class Node {
        int x = 0;
        int y = 0;

        public Node(int y) {
            this.y = y;
        }
    }

    public static void main(String args[]) {
        boolean[][] matrix = new boolean[][]{
                new boolean[]{true, true, false, false, true, false, false},
                new boolean[]{false, true, false, true, true, false, false},
                new boolean[]{false, false, true, true, true, true, true},
                new boolean[]{false, false, true, true, true, true, true},
                new boolean[]{false, false, true, true, true, false, false}
        };
        System.out.print(new Maximal_II().maximalRectangle(matrix));
    }
}