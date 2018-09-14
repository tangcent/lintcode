package T20180914;

import java.util.AbstractList;
import java.util.Iterator;

/**
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
public class PortalSolutionII {
    /**
     * @param Maze:
     * @return: nothing
     */
    public int Portal(char[][] Maze) {

        int xLength = Maze.length;
        int yLength = Maze[0].length;

        MazeFlag mazeFlag = new MazeFlag(xLength, yLength);

        int step = 0;
        int root = -1;

        for (int x = 0; x < Maze.length && root == -1; x++) {
            char[] line = Maze[x];
            for (int y = 0; y < yLength && root == -1; y++) {
                switch (line[y]) {
                    case '#':
                        mazeFlag.visit(x, y);
                        break;
                    case 'S':
                        line[y] = '*';
                        root = mazeFlag.xyToIndex(x, y);
                        mazeFlag.visit(x, y);
                        break;
                }
            }
        }

        FastArrayList listA = new FastArrayList(xLength);
        FastArrayList listB = new FastArrayList(yLength);
        listA.setNext(listB);
        listB.setNext(listA);
        FastArrayList workList = listA;
        listA.add(root);

        --xLength;
        --yLength;

        while (workList.size() > 0) {
            FastArrayList nextList = workList.getNext();
            for (Integer curr : workList) {
                int x = mazeFlag.indexTox(curr);
                int y = mazeFlag.indexToy(curr);
//                System.out.print("[" + x + "," + y + "]");
                switch (Maze[x][y]) {
                    case '*':
                        if (x > 0 && mazeFlag.notVisited(x - 1, y)) {
                            nextList.add(mazeFlag.xyToIndex(x - 1, y));
                        }
                        if (x < xLength && mazeFlag.notVisited(x + 1, y)) {
                            nextList.add(mazeFlag.xyToIndex(x + 1, y));
                        }
                        if (y > 0 && mazeFlag.notVisited(x, y - 1)) {
                            nextList.add(mazeFlag.xyToIndex(x, y - 1));
                        }
                        if (y < yLength && mazeFlag.notVisited(x, y + 1)) {
                            nextList.add(mazeFlag.xyToIndex(x, y + 1));
                        }
                        break;
                    case 'E':
                        return step;
                }
            }
            workList.clear();
            workList = nextList;
            ++step;
        }

        return -1;
    }

    private static class FastArrayList extends AbstractList<Integer> {
        /**
         * The array buffer into which the elements of the ArrayList are stored.
         * The capacity of the ArrayList is the length of this array buffer. Any
         * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
         * will be expanded to DEFAULT_CAPACITY when the first element is added.
         */
        transient int[] elementData; // non-private to simplify nested class access
        FastArrayList next;

        public void setNext(FastArrayList next) {
            this.next = next;
        }

        public FastArrayList getNext() {
            return next;
        }

        public FastArrayList(int length) {
            this.elementData = new int[length];
        }

        /**
         * The size of the ArrayList (the number of elements it contains).
         *
         * @serial
         */
        private int size;

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean add(Integer integer) {
            if (size == elementData.length) {
                int[] newData = new int[size + (size >> 1)];
                System.arraycopy(elementData, 0, newData, 0, size);
                elementData = newData;
            }
            elementData[size++] = integer;
            return true;
        }

        @Override
        public Integer get(int index) {
            return elementData[index];
        }

        //clear quickly
        @Override
        public void clear() {
            size = 0;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Itr();
        }

        /**
         * An optimized version of AbstractList.Itr
         */
        private class Itr implements Iterator<Integer> {
            int cursor;       // index of next element to return

            Itr() {
            }

            public boolean hasNext() {
                return cursor != size;
            }

            @SuppressWarnings("unchecked")
            public Integer next() {
                return FastArrayList.this.elementData[cursor++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }

    private static class MazeFlag {

        boolean[] data;
        int xBits;
        int xMask;

        public MazeFlag(int xLength, int yLength) {
            int pxLength = preferableLength(xLength);
            int pyLength = preferableLength(yLength);
            xBits = Integer.bitCount(pxLength - 1);
            int yBits = Integer.bitCount(pyLength - 1);
            this.xMask = (1 << xBits) - 1;
            this.data = new boolean[1 << (xBits + yBits)];
        }

        private void visit(int x, int y) {
            data[xyToIndex(x, y)] = true;
        }

        private boolean notVisited(int x, int y) {
            int index = xyToIndex(x, y);
            if (data[index]) {
                return false;
            } else {
                data[index] = true;
                return true;
            }
        }

        private int xyToIndex(int x, int y) {
            return x | (y << xBits);
        }

        private int indexTox(int index) {
            return index & xMask;
        }

        private int indexToy(int index) {
            return index >> xBits;
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
        System.out.println(new PortalSolutionII().Portal(p));
    }
}
