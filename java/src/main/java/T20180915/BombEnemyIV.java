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
public class BombEnemyIV {

    /**
     * @param grid: Given a 2D grid, each cell is either 'W', 'E' or '0'
     * @return: an integer, the maximum enemies you can kill using one bomb
     */
    public int maxKilledEnemies(char[][] grid) {
        // write your code here
        try {
            final int xLength = grid.length;
            if (xLength == 0) {
                return 0;
            }
            if (xLength == 1) {
                return maxOnLine(grid[0]);
            }
            final int yLength = grid[0].length;

            NodeManager nodeManager = new NodeManager();
            Node[] preCountLine = null;
            for (char[] line : grid) {
                Node[] countLine = new Node[yLength];

                for (int y = 0; y < yLength; y++) {
                    char ch = line[y];
                    switch (ch) {
                        case '0':
                            Node node = nodeManager.zeroNode();
                            countLine[y] = node;
                            //尝试继承左边的line-Counter
                            if (y > 0) {
                                node.setLine(countLine[y - 1].getLine());
                            } else {
                                node.setLine(new Counter());
                            }
                            //尝试继承上面的row-Counter
                            if (preCountLine != null) {
                                node.setRow(preCountLine[y].getRow());
                            } else {
                                node.setRow(new Counter());
                            }
                            break;
                        case 'W':
                            countLine[y] = nodeManager.wNode();
                            break;
                        case 'E':
                            ENode eNode = nodeManager.eNode();
                            countLine[y] = eNode;
                            //尝试继承左边的line-Counter
                            if (y > 0) {
                                eNode.setLine(countLine[y - 1].getLine());
                            } else {
                                eNode.setLine(new Counter());
                            }
                            //尝试继承上面的row-Counter
                            if (preCountLine != null) {
                                eNode.setRow(preCountLine[y].getRow());
                            } else {
                                eNode.setRow(new Counter());
                            }
                            eNode.incr();
                            break;
                        default:
                            break;
                    }
                }
                preCountLine = countLine;
            }

            return nodeManager.findMax();
        } catch (Exception e) {
            return 0;
        }
    }

    private static int maxOnLine(char[] line) {
        int max = 0;
        int count = 0;
        boolean canBom = false;
        for (char c : line) {
            switch (c) {
                case 'E':
                    ++count;
                    break;
                case 'W':
                    if (canBom && count > max) {
                        max = count;
                    }
                    count = 0;
                    break;
                case '0':
                    canBom = true;
                    break;
            }
        }
        return (canBom && count > max) ? count : max;
    }

    private static class NodeManager {
        private WNode wNode = new WNode();

        public WNode wNode() {
            return wNode;
        }

        public ENode eNode() {
            return new ENode();
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

    private static interface Node {
        public int count();

        public void setLine(Counter line);

        public void setRow(Counter row);

        Counter getLine();

        Counter getRow();
    }

    private static class ZeroNode implements Node {
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

        @Override
        public Counter getLine() {
            return line;
        }

        @Override
        public Counter getRow() {
            return row;
        }

        public void setNext(ZeroNode next) {
            this.next = next;
        }

        public ZeroNode getNext() {
            return next;
        }
    }

    private static class ENode implements Node {

        Counter line;
        Counter row;

        @Override
        public int count() {
            return 0;
        }

        @Override
        public void setLine(Counter line) {
            this.line = line;
        }

        @Override
        public void setRow(Counter row) {
            this.row = row;
        }

        @Override
        public Counter getLine() {
            return line;
        }

        @Override
        public Counter getRow() {
            return row;
        }

        public void incr() {
            this.row.incr();
            this.line.incr();
        }
    }

    private static class WNode implements Node {

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

        @Override
        public Counter getLine() {
            return new Counter();
        }

        @Override
        public Counter getRow() {
            return new Counter();
        }
    }

    private static class Counter {
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
                "EEE".toCharArray(),
                "E0E".toCharArray(),
        };

        System.out.println(new BombEnemyIV().maxKilledEnemies(grid));
    }
}
