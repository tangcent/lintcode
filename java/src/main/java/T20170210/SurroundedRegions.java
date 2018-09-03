package T20170210;

import java.util.*;

/**
 * Created by TomNg on 2017/2/10.
 * Surrounded Regions
 * Given a 2D board containing 'X' and 'O', capture all regions surrounded by 'X'.
 * <p>
 * A region is captured by flipping all 'O''s into 'X''s in that surrounded region.
 * Example
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After capture all regions surrounded by 'X', the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 */
public class SurroundedRegions {
    /**
     * @param board a 2D board containing 'X' and 'O'
     * @return void
     */
    public void surroundedRegions(char[][] board) {
        RunTime runTime = new RunTime(board);

        //get and check mx,my
        int mx = board.length;
        if (mx == 0)
            return;
        int my = board[0].length;
        if (my == 0)
            return;

        for (int i = 0; i < mx; i++) {
            checkZero(runTime, i, 0);
            checkZero(runTime, i, my - 1);
        }
        for (int i = 1; i < my - 1; i++) {
            checkZero(runTime, 0, i);
            checkZero(runTime, mx - 1, i);
        }
        while (!runTime.stack.empty()) {
            long coord = runTime.stack.pop();
            runTime.keep.add(coord);
            int x = getX(coord);
            int y = getY(coord);
            if (x - 1 > -1) {
                checkZero(runTime, x - 1, y);
            }
            if (x + 1 < mx) {
                checkZero(runTime, x + 1, y);
            }
            if (y - 1 > -1) {
                checkZero(runTime, x, y - 1);
            }
            if (y + 1 < my) {
                checkZero(runTime, x, y + 1);
            }
        }
        for (int x = 0; x < mx; x++) {
            for (int y = 0; y < my; y++) {
                if (!runTime.contain(coord(x, y))) {
                    board[x][y] = 'X';
                }
            }
        }
    }

    private class RunTime {
        Stack<Long> stack;
        Set<Long> keep;
        char[][] board;

        public RunTime(char[][] board) {
            this.board = board;
            stack = new Stack<>();
            keep = new HashSet<>();
        }

        private boolean contain(long coord) {
            return stack.contains(coord) || keep.contains(coord);
        }
    }

    private void checkZero(RunTime runTime, int x, int y) {
        long coord = coord(x, y);
        if (runTime.contain(coord))
            return;
        if (runTime.board[x][y] == 'O') {
            runTime.stack.push(coord(x, y));
        }
    }

    private int mark = 0x11111111;

    private int getX(long coordinate) {
        return (int) (coordinate & mark);
    }

    private int getY(long coordinate) {
        return (int) ((coordinate >> 32) & mark);
    }

    private long coord(int x, int y) {
        return ((long) x << 32) + y;
    }
}
