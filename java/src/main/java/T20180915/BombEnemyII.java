package T20180915;

/**
 * Description
 * Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty '0' (the number zero), return the maximum enemies you can kill using one bomb.
 * The bomb kills all the enemies in the same row and column from the planted point until it hits the wall since the wall is too strong to be destroyed.
 * <p>
 * You can only put the bomb at an empty cell.
 * <p>
 * Example
 * Given a grid:
 * <p>
 * 0 E 0 0
 * E 0 W E
 * 0 E 0 0
 * return 3. (Placing a bomb at (1,1) kills 3 enemies)
 */
public class BombEnemyII {

    /**
     * @param grid: Given a 2D grid, each cell is either 'W', 'E' or '0'
     * @return: an integer, the maximum enemies you can kill using one bomb
     */
    public int maxKilledEnemies(char[][] grid) {
        // write your code here
        try {
            final int xLength = grid.length;
            final int yLength = grid[0].length;

            Node[][] countGrid = new Node[xLength][yLength];
            NodeManager nodeManager = new NodeManager();
            for (int x = 0; x < xLength; x++) {
                char[] line = grid[x];
                Node[] countLine = new Node[yLength];
                countGrid[x] = countLine;

                Counter counter = new Counter();

                for (int y = 0; y < yLength; y++) {
                    char ch = line[y];
                    switch (ch) {
                        case 'E':
                            counter.incr();
                            countLine[y] = nodeManager.ewNode();
                            break;
                        case 'W':
                            counter = new Counter();
                            countLine[y] = nodeManager.ewNode();
                            break;
                        default:
                            countLine[y] = nodeManager.zeroNode();
                            countLine[y].setLine(counter);
                            break;
                    }
                }

            }

            for (int y = 0; y < yLength; y++) {

                Counter counter = new Counter();
                for (int x = 0; x < xLength; x++) {

                    char ch = grid[x][y];
                    switch (ch) {
                        case 'E':
                            counter.incr();
                            break;
                        case 'W':
                            counter = new Counter();
                            break;
                        default:
                            countGrid[x][y].setRow(counter);
                            break;
                    }
                }
            }

            return nodeManager.findMax();
        } catch (Exception e) {
            return 0;
        }
    }


    private class NodeManager {
        private EWNode ewNode;

        public EWNode ewNode() {
            if (ewNode == null) {
                ewNode = new EWNode();
            }
            return ewNode;
        }

        public ZeroNode head = new ZeroNode();
        public ZeroNode tail = head;

        public ZeroNode zeroNode() {
            ZeroNode zeroNode = new ZeroNode();
            tail.next = zeroNode;
            tail = zeroNode;
            return zeroNode;
        }

        public int findMax() {
            int max = 0;
            ZeroNode c = head.next;
            while (c != null) {
                max = Integer.max(max, c.count());
                c = c.next;
            }
            return max;
        }
    }

    private interface Node {
        public int count();

        public void setLine(Counter line);

        public void setRow(Counter row);
    }

    private class ZeroNode implements Node {
        Counter line;
        Counter row;
        ZeroNode next;

        public int count() {
            return line.get() + row.get();
        }

        public void setLine(Counter line) {
            this.line = line;
        }

        public void setRow(Counter row) {
            this.row = row;
        }

        public void setNext(ZeroNode next) {
            this.next = next;
        }

        public ZeroNode getNext() {
            return next;
        }
    }

    private class EWNode implements Node {

        @Override
        public int count() {
            return 0;
        }

        @Override
        public void setLine(Counter line) {
        }

        @Override
        public void setRow(Counter row) {
        }
    }

    private class Counter {
        int counter;

        public void incr() {
            ++counter;
        }

        public int get() {
            return counter;
        }
    }


    public static void main(String[] args) {

        char[][] grid = new char[][]{
                "E0E".toCharArray(),
                "EEE".toCharArray(),
                "EEE".toCharArray()
        };

        System.out.println(new BombEnemyII().maxKilledEnemies(grid));
    }
}
