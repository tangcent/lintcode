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
 * <p>
 * <p>
 */
public class BombEnemyVI {

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

            int max = 0;
            Node[] nods = new Node[yLength];
            for (int i = 0; i < yLength; i++) {
                nods[i] = new Node();
            }

            int start;
            int count;
            boolean canBom;

            for (char[] line : grid) {

                start = 0;
                count = 0;
                canBom = false;
                for (int y = 0; y < yLength; y++) {
                    switch (line[y]) {
                        case '0':
                            nods[y].addZero();
                            canBom = true;
                            break;
                        case 'W':
                            if (canBom) {
                                updateMax(nods, line, start, y, count);
                                canBom = false;
                            }
                            if (nods[y].max > max) {
                                max = nods[y].max;
                            }
                            start = y + 1;
                            nods[y].resetForW();
                            count = 0;
                            break;
                        case 'E':
                            nods[y].addE();
                            ++count;
                            break;
                        default:
                            break;
                    }
                }
                if (canBom) {
                    updateMax(nods, line, start, yLength, count);
                }
            }
            for (Node node : nods) {
                if (node.max > max) {
                    max = node.max;
                }
            }
            return max;
        } catch (Exception e) {
            return 0;
        }
    }

    private void updateMax(Node[] nods, char[] line, int start, int end, int count) {
        if (count > 0) {
            for (int i = start; i < end; i++) {
                if (line[i] == '0') {
                    nods[i].updateForCount(count);
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (line[i] == '0') {
                    nods[i].tryMaxWithE();
                }
            }
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

    private class Node {
        int max = -1;//当前本列最大数
        int eCount;//当前本列上方E的数量

        private void resetForW() {
            max = -1;
            eCount = 0;
        }

        private void addE() {
            if (max != -1) {
                max += 1;
            }
            ++eCount;
        }

        private void addZero() {
            if (max == -1) {
                max = eCount;
            }
        }

        private void updateForCount(int count) {
            int c = count + eCount;
            if (c > max) {
                max = c;
            }
        }

        private void tryMaxWithE() {
            if (max == 0) {
                max = eCount;
            }
        }
    }

    public static void main(String[] args) {

        char[][] grid = new char[][]{
                "0".toCharArray(),
                "E".toCharArray(),
                "E".toCharArray(),
                "E".toCharArray(),
                "E".toCharArray(),
        };

        System.out.println(new BombEnemyVI().maxKilledEnemies(grid));
    }
}
