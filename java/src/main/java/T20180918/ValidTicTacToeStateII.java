package T20180918;

/**
 * Description
 * A Tic-Tac-Toe board is given as a string array board. Return True if and only if it is possible to reach this board position during the course of a valid tic-tac-toe game.
 * <p>
 * The board is a 3 x 3 array, and consists of characters " ", "X", and "O". The " " character represents an empty square.
 * <p>
 * Here are the rules of Tic-Tac-Toe:
 * <p>
 * Players take turns placing characters into empty squares (" ").
 * The first player always places "X" characters, while the second player always places "O" characters.
 * "X" and "O" characters are always placed into empty squares, never filled ones.
 * The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
 * The game also ends if all squares are non-empty.
 * No more moves can be played if the game is over.
 * board is a length-3 array of strings, where each string board[i] has length 3.
 * Each board[i][j] is a character in the set {" ", "X", "O"}.
 * <p>
 * Example
 * Example 1:
 * Input: board = ["O  ", "   ", "   "]
 * Output: false
 * Explanation: The first player always plays "X".
 * <p>
 * Example 2:
 * Input: board = ["XOX", " X ", "   "]
 * Output: false
 * Explanation: Players take turns making moves.
 * <p>
 * Example 3:
 * Input: board = ["XXX", "   ", "OOO"]
 * Output: false
 * <p>
 * Example 4:
 * Input: board = ["XOX", "O O", "XOX"]
 * Output: true
 */
public class ValidTicTacToeStateII {

    /**
     * @param board: the given board
     * @return: True if and only if it is possible to reach this board position during the course of a valid tic-tac-toe game
     */
    public boolean validTicTacToe(String[] board) {
        // Write your code
        int xBits = 0, oBits = 0, xCount = 0, oCount = 0, i = 0;
        for (String s : board) {
            for (int lineIndex = 0; lineIndex < 3; lineIndex++) {
                switch (s.charAt(lineIndex)) {
                    case 'X':
                        xBits = xBits | (1 << i);//low 9 bit
                        ++xCount;
                        break;
                    case 'O':
                        oBits = oBits | (1 << i);//high 9 bit
                        ++oCount;
                        break;
                }
                ++i;
            }
        }

        int validStatus = 0;
        if (xCount == oCount) {
            validStatus = 0b101;//o win or not
        } else if (xCount - oCount == 1) {
            if (xCount == 5) {
                validStatus = 0b011;//x win or not
            } else {
                validStatus = 0b010;//x win
            }
        } else {
            return false;
        }

        for (int win : diagonalWins) {
            if ((win & xBits) == win) {
                return (0b010 & validStatus) != 0;
            }
            if ((win & oBits) == win) {
                return (0b100 & validStatus) != 0;
            }
        }

        int status = 0b001;//no winner
        int[] wins = lineWins;
        for (; ; ) {
            for (int win : wins) {
                if ((win & xBits) == win) {
                    if (status == 0b100) {//o has win
                        return false;
                    } else {
                        status = 0b010;//x win
                        if ((status & validStatus) == 0) {
                            return false;
                        }
                    }
                }
                if ((win & oBits) == win) {
                    if (status != 0b001) {//someone has win
                        return false;
                    } else {
                        status = 0b100;//o win
                        if ((status & validStatus) == 0) {
                            return false;
                        }
                    }
                }
            }
            if (status != 0b001) {
                return (status & validStatus) != 0;
            }

            if (wins == lineWins) {
                wins = rowWins;
                continue;
            }
            break;
        }

        return (status & validStatus) != 0;

    }

    private static int[] diagonalWins = new int[]{0x111, 0x54};
    private static int[] lineWins = new int[]{0x124, 0x92, 0x49};
    private static int[] rowWins = new int[]{0x1c0, 0x38, 0x7};
    private static int[][] lineOrRowWins = new int[][]{lineWins, rowWins};

    public static void main(String[] args) {
        System.out.println(new ValidTicTacToeStateII().validTicTacToe(new String[]{
                "XXX", "   ", "OOO"}));
    }
}
