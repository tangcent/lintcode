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
public class BombEnemyV {

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
            int[] maxLine = new int[yLength];//当前上方每行最大数
            int[] rowEs = new int[yLength];//当前每行上方的E数

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
                            canBom = true;
                            break;
                        case 'W':
                            if (canBom) {
                                updateMax(maxLine, rowEs, line, start, y, count);
                                canBom = false;
                            }
                            if (maxLine[y] > max) {
                                max = maxLine[y];
                            }
                            start = y + 1;
                            rowEs[y] = 0;
                            maxLine[y] = 0;
                            count = 0;
                            break;
                        case 'E':
                            if (maxLine[y] != 0) {//上方有数才加
                                ++maxLine[y];
                            }
                            ++rowEs[y];
                            ++count;
                            break;
                        default:
                            break;
                    }
                }
                if (canBom) {
                    updateMax(maxLine, rowEs, line, start, yLength, count);
                }
            }
            for (int m : maxLine) {
                if (m > max) {
                    max = m;
                }
            }
            return max;
        } catch (Exception e) {
            return 0;
        }
    }

    private void updateMax(int[] maxLine, int[] rowEs, char[] line, int start, int end, int count) {
        if (count > 0) {
            for (int i = start; i < end; i++) {
                if (line[i] == '0') {
                    int c = count + rowEs[i];
                    if (c > maxLine[i]) {
                        maxLine[i] = c;
                    }
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (line[i] == '0' && maxLine[i] == 0) {
                    maxLine[i] = rowEs[i];
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

    public static void main(String[] args) {

        char[][] grid = new char[][]{
                "WWWWWWWWWW".toCharArray(),
                "EEEEEEEEEE".toCharArray(),
                "WWWWWEWWWW".toCharArray(),
                "EE000000E0".toCharArray(),
                "WWWWW0WWWW".toCharArray(),
                "EEEEEEEEEE".toCharArray()
        };

        System.out.println(new BombEnemyV().maxKilledEnemies(grid));
    }
}
