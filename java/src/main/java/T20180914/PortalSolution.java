package T20180914;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://www.lintcode.com/problem/portal/description
 * Description
 * Chell is the protagonist of the Portal Video game series developed by Valve Corporation.
 * One day, She fell into a maze. The maze can be thought of as an array of 2D characters of size n x m. It has 4 kinds of rooms. 'S' represents where Chell started(Only one starting point). 'E' represents the exit of the maze(When chell arrives, she will leave the maze, this question may have multiple exits). '*' represents the room that Chell can pass. '#' represents a wall, Chell can not pass the wall.
 * She can spend a minute moving up,down,left and right to reach a room, but she can not reach the wall.
 * Now, can you tell me how much time she needs at least to leave the maze?
 * If she can not leave, return -1.
 * <p>
 * We guarantee that the size of the maze is n x m, and 1<=n<=200,1<=m<=200.
 * There is only one 'S', and one or more 'E'.
 * Example
 * Give
 * [
 * ['S','E','*'],
 * ['*','*','*'],
 * ['*','*','*']
 * ]
 * ,return 1.
 * <p>
 * Explanation:
 * Chell spent one minute walking from (0,0) to (0,1).
 * Give
 * [
 * ['S','#','#'],
 * ['#','*','#'],
 * ['#','*','*'],
 * ['#','*','E']
 * ]
 * ,return -1.
 * <p>
 * Explanation:
 * Chell can not leave the maze.
 * Give
 * [
 * ['S','*','E'],
 * ['*','*','*'],
 * ['#','*','*'],
 * ['#','#','E']
 * ]
 * ,return 2.
 * <p>
 * Explanation:
 * First step: Chell move from (0,0) to (0,1).
 * Second step: Chell move from (0,1) to (0,2).
 * (Chell can also leave from (3,2), but it would take 5 minutes. So it's better to leave from (0,2).)
 * Give
 * [
 * ['E','*','#'],
 * ['#','*','#'],
 * ['#','*','*'],
 * ['#','#','S']
 * ]
 * ,return 5.
 * <p>
 * Explanation:
 * First step: Chell move from (0,0) to (0,1).
 * Second step: Chell move from (0,1) to (1,1).
 * Third step: Chell move from (1,1) to (2,1).
 * Fourth step: Chell move from (2,1) to (2,2).
 * Fifth step: Chell move from (2,2) to (3,2).
 */
public class PortalSolution {
    /**
     * @param Maze:
     * @return: nothing
     */
    public int Portal(char[][] Maze) {
        int xLength = Maze.length;
        int yLength = Maze[0].length;
        SingleMaze singleMaze = new SingleMaze(xLength, yLength);

        Node curr = null;

        for (int x = 0; x < Maze.length; x++) {
            char[] line = Maze[x];
            for (int y = 0; y < line.length; y++) {
                char c = line[y];
                switch (c) {
                    case 'S':
                        curr = singleMaze.getNode(x, y);
                        break;
                    case 'E':
                        singleMaze.setValue(x, y, E);
                        break;
                    case '*':
                        break;
                    case '#':
                        singleMaze.setValue(x, y, non);
                        singleMaze.updateValue(x - 1, y, noRight);
                        singleMaze.updateValue(x + 1, y, noLeft);
                        singleMaze.updateValue(x, y - 1, noDown);
                        singleMaze.updateValue(x, y + 1, noUp);
                        break;
                }
            }
        }

        int step = 0;
        Queue<Node> cNodes = new LinkedList<>();
        cNodes.offer(curr);

        while (cNodes.size() > 0) {
            System.out.println("\n");
            int size = cNodes.size();
            for (int i = 0; i < size; i++) {
                curr = cNodes.poll();
                System.out.print(curr);
                if (curr.val == E) {
                    return step;
                }
                if (curr.go(right, noRight)) {
                    //right
                    cNodes.offer(singleMaze.right(curr));
                }
                if (curr.go(left, noLeft)) {
                    //left
                    cNodes.offer(singleMaze.left(curr));
                }
                if (curr.go(down, noDown)) {
                    //down
                    cNodes.offer(singleMaze.down(curr));
                }
                if (curr.go(up, noUp)) {
                    //up
                    cNodes.offer(singleMaze.up(curr));
                }
            }
            step++;
        }

        return -1;
    }

    private static class SingleMaze {

        int xLength;
        int yLength;
        Node[] data;
        int xBits;
        int yBits;
        int xMask;

        public SingleMaze(int xLength, int yLength) {
            this.xLength = preferableLength(xLength);
            this.yLength = preferableLength(yLength);
            xBits = Integer.bitCount(this.xLength - 1);
            yBits = Integer.bitCount(this.yLength - 1);
            xMask = (1 << xBits) - 1;
            this.data = new Node[1 << (xBits + yBits)];

            //初始化边缘
            for (int i = 0; i < xLength; i++) {
                initWithUpdate(i, 0, noUp);
                initWithUpdate(i, yLength - 1, noDown);
            }
            for (int i = 0; i < yLength; i++) {
                initWithUpdate(0, i, noLeft);
                initWithUpdate(xLength - 1, i, noRight);
            }
            //初始化中心
            for (int x = 1; x < xLength - 1; x++) {
                for (int y = 1; y < yLength - 1; y++) {
                    init(x, y);
                }
            }
        }

        private void init(int x, int y) {
            data[xyToIndex(x, y)] = new Node(x, y);
        }

        private void initWithUpdate(int x, int y, int val) {
            Node node = data[xyToIndex(x, y)];
            if (node == null) {
                node = new Node(x, y);
                data[xyToIndex(x, y)] = node;
            }
            node.val = node.val & val;

        }

        private void setValue(int x, int y, int val) {
            data[xyToIndex(x, y)].val = val;
        }

        private void updateValue(int x, int y, int val) {
            try {
                int index = xyToIndex(x, y);
                data[index].val = data[index].val & val;
            } catch (Exception ignored) {
            }
        }

        private int xyToIndex(int x, int y) {
            return x | (y << xBits);
        }

        private Node getNode(int x, int y) {
            return data[xyToIndex(x, y)];
        }

        private Node left(Node node) {
            Node leftNode = getNode(node.x - 1, node.y);
            leftNode.val = leftNode.val & noRight;
            return leftNode;
        }

        private Node right(Node node) {
            Node rightNode = getNode(node.x + 1, node.y);
            rightNode.val = rightNode.val & noLeft;
            return rightNode;
        }

        private Node up(Node node) {
            Node upNode = getNode(node.x, node.y - 1);
            upNode.val = upNode.val & noDown;
            return upNode;
        }

        private Node down(Node node) {
            Node downNode = getNode(node.x, node.y + 1);
            downNode.val = downNode.val & noUp;
            return downNode;
        }

    }

    private static class Node {
        int x;
        int y;
        int val;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.val = all;
        }

        public Node(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }

        private boolean go(int direction, int iDirection) {
            boolean result = (val & direction) != 0;
            if (result) {
                val = val & iDirection;
            }
            return result;
        }

        @Override
        public String toString() {
            return "[x=" + x +
                    ", y=" + y + "]";
        }
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int preferableLength(int length) {
        int n = length - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
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


    public static void main(String[] args) {
        char[][] p = new char[][]{

                "**#***E**#***#*#****".toCharArray(),
                "#****#**************".toCharArray(),
                "#***##********##****".toCharArray(),
                "*#*****#*##*******#*".toCharArray(),
                "##*****#*##*##******".toCharArray(),
                "##**#####*#*E*#*#*#*".toCharArray(),
                "##**#*********#*#*##".toCharArray(),
                "#*****##*##********#".toCharArray(),
                "#***#*******#*#*#***".toCharArray(),
                "**#*##*****#*****##*".toCharArray(),
                "**##******#********#".toCharArray(),
                "#E***#*#*#**#****##*".toCharArray(),
                "##*****#***#***#*#**".toCharArray(),
                "***##*##***#*#*#***#".toCharArray(),
                "***#*#*#E*#******#**".toCharArray(),
                "****#*******#*****#E".toCharArray(),
                "S**************#**#*".toCharArray(),
                "#***#*#**#*#********".toCharArray(),
                "*******###*###******".toCharArray(),
                "******#*#**#*******#".toCharArray()
        };
        System.out.println(new PortalSolution().Portal(p));
    }
}
