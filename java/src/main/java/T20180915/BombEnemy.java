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
public class BombEnemy {

    /**
     * @param grid: Given a 2D grid, each cell is either 'W', 'E' or '0'
     * @return: an integer, the maximum enemies you can kill using one bomb
     */
    public int maxKilledEnemies(char[][] grid) {
        // write your code here
        try {
            final int xLength = grid.length;
            final int yLength = grid[0].length;

            int[][] countGrid = new int[xLength][yLength];
            for (int x = 0; x < xLength; x++) {
                char[] line = grid[x];
                int[] countLine = countGrid[x];

                int start = 0;
                int count = 0;
                for (int y = 0; y < yLength; y++) {
                    char ch = line[y];
                    switch (ch) {
                        case 'E':
                            count++;
                            countLine[y] = Integer.MIN_VALUE;
                            break;
                        case 'W':
                            if (count != 0) {
                                for (int i = start; i < y; i++) {
                                    countLine[i] |= count;
                                }
                                count = 0;
                            }
                            start = y + 1;
                            break;
                    }
                }
                if (count != 0) {
                    for (int i = start; i < yLength; i++) {
                        countLine[i] |= count;
                    }
                }
            }

            int max = 0;
            for (int y = 0; y < yLength; y++) {
                int start = 0;
                int count = 0;
                for (int x = 0; x < xLength; x++) {

                    char ch = grid[x][y];
                    switch (ch) {
                        case 'E':
                            count++;
                            break;
                        case 'W':
                            if (count != 0) {
                                for (int i = start; i < x; i++) {
                                    max = Integer.max(max, countGrid[i][y] + count);
                                }
                                count = 0;
                            } else {
                                for (int i = start; i < x; i++) {
                                    max = Integer.max(max, countGrid[i][y]);
                                }
                            }
                            start = x + 1;
                            break;
                    }
                }
                if (count != 0) {
                    for (int i = start; i < xLength; i++) {
                        max = Integer.max(max, countGrid[i][y] + count);
                    }
                } else {
                    for (int i = start; i < xLength; i++) {
                        max = Integer.max(max, countGrid[i][y]);
                    }
                }
            }


            return Integer.max(max, 0);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {

        char[][] grid = new char[][]{
                "E0E".toCharArray()
        };

        System.out.println(new BombEnemy().maxKilledEnemies(grid));
    }
}
