package T20181014;

import java.util.Stack;

/**
 * Description
 * Given a two-dimensional matrix, the value of each grid represents the height of the terrain. The flow of water will only flow up, down, right and left, and it must flow from the high ground to the low ground. As the matrix is surrounded by water, it is now filled with water from (R,C) and asked if water can flow out of the matrix.
 * <p>
 * The input matrix size is n x n, n <= 200.
 * Ensure that each height is a positive integer.
 * <p>
 * Example
 * Given
 * <p>
 * mat =
 * [
 * [10,18,13],
 * [9,8,7],
 * [1,2,3]
 * ]
 * R = 1, C = 1, return "YES"。
 * <p>
 * Explanation:
 * (1,1) →(1,2)→Outflow.
 * Given
 * <p>
 * mat =
 * [
 * [10,18,13],
 * [9,7,8],
 * [1,11,3]
 * ]
 * R = 1, C = 1, return "NO"。
 * <p>
 * Explanation:
 * Since (1,1) cannot flow to any other grid, it cannot flow out.
 * 0
 */
public class MatrixWaterInjection {

    /**
     * @param matrix: the height matrix
     * @param R:      the row of (R,C)
     * @param C:      the columns of (R,C)
     * @return: Whether the water can flow outside
     */
    public String waterInjection(int[][] matrix, int R, int C) {

        int xM = matrix.length - 1;
        if (R == 0 || R == xM) {
            return YES;
        }

        int yM = matrix[0].length - 1;
        if (C == 0 || C == yM) {
            return YES;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(new Node(R, C, matrix[R][C], all));

        while (!stack.isEmpty()) {
            Node node = stack.peek();
            if (node.go(right, noRight)) {
                int x = node.x + 1;
                int right = matrix[x][node.y];
                if (right < node.val) {
                    if (x == xM) {
                        return YES;
                    } else {
                        stack.push(new Node(x, node.y, right, noLeft));
                        continue;
                    }
                }
            }
            if (node.go(left, noLeft)) {
                int x = node.x - 1;
                int left = matrix[x][node.y];
                if (left < node.val) {
                    if (x == 0) {
                        return YES;
                    } else {
                        stack.push(new Node(x, node.y, left, noRight));
                        continue;
                    }
                }
            }
            if (node.go(down, noDown)) {
                int y = node.y - 1;
                int up = matrix[node.x][y];
                if (up < node.val) {
                    if (y == 0) {
                        return YES;
                    } else {
                        stack.push(new Node(node.x, y, up, noUp));
                        continue;
                    }
                }
            }
            if (node.go(up, noUp)) {
                int y = node.y + 1;
                int down = matrix[node.x][y];
                if (down < node.val) {
                    if (y == yM) {
                        return YES;
                    } else {
                        stack.push(new Node(node.x, y, down, noDown));
                        continue;
                    }
                }
            }
            stack.pop();
        }
        return NO;
    }

    private static final String YES = "YES";
    private static final String NO = "NO";

    private static class Node {
        int x;
        int y;
        int val;
        int dir;

        public Node(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
            this.dir = all;
        }

        public Node(int x, int y, int val, int dir) {
            this.x = x;
            this.y = y;
            this.val = val;
            this.dir = dir;
        }

        private boolean go(int direction, int iDirection) {
            boolean result = (dir & direction) != 0;
            if (result) {
                dir = dir & iDirection;
            }
            return result;
        }

        @Override
        public String toString() {
            return "[x=" + x +
                    ", y=" + y + "]";
        }
    }

    private static int left = 0b1000;
    private static int right = 0b100;
    private static int up = 0b0010;
    private static int down = 0b0001;
    private static int E = 0b10000;
    private static int non = 0;
    private static int all = left | right | up | down;
    private static int EAll = E | all;
    private static int noLeft = EAll & ~left;
    private static int noRight = EAll & ~right;
    private static int noUp = EAll & ~up;
    private static int noDown = EAll & ~down;
}
