package T20161217;

import sun.plugin2.os.windows.FLASHWINFO;

import java.util.ArrayList;
import java.util.List;

/**
 * Maximal Rectangle
 * Given a 2D boolean matrix filled with False and True,
 * find the largest rectangle containing xay True and return its area.
 */
public class Maximal {
    /**
     * @param matrix a boolean 2D matrix
     * @return an integer
     */
    public int maximalRectangle(boolean[][] matrix) {
        // Write your code here
        int max = 0;
        List<Rectangle> cache = new ArrayList<>();
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                if (matrix[x][y]) {
                    max = max(max, maximalRectangle(cache, matrix, x, y, all));
                }
            }
        }
        return max;
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private int max(int a, int b, int c) {
        return max(a, max(b, c));
    }

    private int maximalRectangle(List<Rectangle> cache, boolean[][] matrix, int x, int y, int flag) {
        int max = -1;
        int length = cache.size();
        for (int i = 0; i < length; i++) {
            Rectangle rectangle = cache.get(i);
            if (rectangle.contain(x, y)) {
                for (int nx = x + 1; nx <= rectangle.bx; ++nx) {
                    max = max(max, maximalRectangle(cache, matrix, x, y, nx, rectangle.by, my));
                }
                for (int ny = y + 1; ny <= rectangle.by; ++ny) {
                    max = max(max, maximalRectangle(cache, matrix, x, y, rectangle.bx, ny, mx));
                }
                max = max(max, maximalRectangle(cache, matrix, x, y, rectangle.bx, rectangle.by, all));
            }
        }
        if (max == -1) {
            return maximalRectangle(cache, matrix, x, y, x, y, all);
        } else {
            return max;
        }
    }

    private int maximalRectangle(List<Rectangle> cache, boolean[][] matrix, int x, int y, int nx, int ny, int flag) {
        while (flag > 0) {
            flag = tryNext(matrix, x, y, nx, ny, flag);
            if (flag == all) {
                return max(
                        maximalRectangle(cache, matrix, x, y, nx + 1, ny, mx),
                        maximalRectangle(cache, matrix, x, y, nx, ny + 1, my),
                        maximalRectangle(cache, matrix, x, y, nx + 1, ny + 1, all)
                );
            }
            if (flag == xay) {//角落为0
                return max(
                        maximalRectangle(cache, matrix, x, y, nx + 1, ny, mx),
                        maximalRectangle(cache, matrix, x, y, nx, ny + 1, my)
                );
            }
            if ((flag & mx) > 0) {
                nx++;
            }
            if ((flag & my) > 0) {
                ny++;
            }
        }
        int max = (nx - x + 1) * (ny - y + 1);
        if((nx-x)>3&&(ny-y)>3) {
            add(cache, new Rectangle(x, y, nx, ny));
        }
        return max;
    }

    private void add(List<Rectangle> cache, Rectangle rec) {
        for (Rectangle rectangle : cache) {
            if (rectangle.contain(rec)) {
                return;
            }
        }
        cache.add(rec);
    }

    private int tryNext(boolean[][] matrix, int x, int y, int nx, int ny, int flag) {
        int i;
        int next = flag & xay;
        if ((flag & mx) > 0) {
            if (nx + 1 >= matrix.length) {
                next = next & ~mx;
            } else {
                for (i = y; i <= ny; ++i) {
                    if (!matrix[nx + 1][i]) {
                        next = next & ~mx;
                        break;
                    }
                }
            }
        }
        if ((flag & my) > 0) {
            if (ny + 1 >= matrix[0].length) {
                next = next & ~my;
            } else {
                for (i = x; i <= nx; ++i) {
                    if (!matrix[i][ny + 1]) {
                        next = next & ~my;
                        break;
                    }
                }
            }
        }
        if (next == xay) {//判断角落
            try {
                if (matrix[nx + 1][ny + 1]) {
                    next = next | corner;
                }
            } catch (ArrayIndexOutOfBoundsException ignore) {
            }
        }
        return next;
    }

    private static int mx = 1 << 1;
    private static int my = 1 << 2;
    private static int corner = 1 << 3;

    private static int xay = mx | my;
    private static int all = xay | corner;

    private class Rectangle {
        private int tx;
        private int ty;
        private int bx;
        private int by;

        public Rectangle(int tx, int ty, int bx, int by) {
            this.tx = tx;
            this.ty = ty;
            this.bx = bx;
            this.by = by;
        }

        boolean contain(Rectangle rec) {
            if (rec.tx < tx)
                return false;
            if (rec.ty < ty)
                return false;
            if (rec.bx > tx)
                return false;
            if (rec.bx > tx)
                return false;
            return true;
        }

        boolean contain(int x, int y) {
            return tx < x && x < bx && ty < y && y < by;
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
        System.out.print(new Maximal().maximalRectangle(matrix));
    }
}