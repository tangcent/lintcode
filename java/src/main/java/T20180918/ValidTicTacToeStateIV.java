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
public class ValidTicTacToeStateIV {

    /**
     * @param board: the given board
     * @return: True if and only if it is possible to reach this board position during the course of a valid tic-tac-toe game
     */
    public boolean validTicTacToe(String[] board) {
        // Write your code
        int boardBits = 0, xCount = 0, oCount = 0, i = 0;
        for (String s : board) {
            for (int lineIndex = 0; lineIndex < 3; lineIndex++) {
                switch (s.charAt(lineIndex)) {
                    case 'X':
                        boardBits = boardBits | (1 << i);//low 9 bit
                        ++xCount;
                        break;
                    case 'O':
                        boardBits = boardBits | (1 << (i + 9));//high 9 bit
                        ++oCount;
                        break;
                }
                ++i;
            }
        }

        int validStatus = 0;
        if (xCount == oCount) {
            validStatus = 0b101;//o win or not
        } else if (xCount == 5) {
            validStatus = 0b011;//x win or not
        } else if (xCount == oCount + 1) {
            validStatus = 0b010;//x win
        } else {
            return false;
        }


        int status = 0b001;//no winner
        for (; ; ) {
            int result = (boardBits & boardBits >> 1 & boardBits >> 2);
            if (result != 0) {
                if ((result & 0x3fe00) != 0) {//o win
                    status = 0b100;//o winner
                }
                if ((result & 0x1ff) != 0) {//x win
                    if (status != 0b001) {
                        return false;
                    }
                    status = 0b100;//x winner
                }
                break;
            }

            result = (boardBits & boardBits >> 3 & boardBits >> 6);
            if (result != 0) {
                if ((result & 0x3fe00) != 0) {//o win
                    status = 0b100;//o winner
                }
                if ((result & 0x1ff) != 0) {//x win
                    if (status != 0b001) {
                        return false;
                    }
                    status = 0b010;//x winner
                }
                break;
            }

            if ((boardBits & 0b000010000) == 0) {//center is not 'O'
                if ((0x22200 & boardBits) == 0x22200 || (0xa800 & boardBits) == 0xa800) {
                    return (0b100 & validStatus) != 0;
                }
            } else {//cent may be 'X'
                if ((0x111 & boardBits) == 0x111 || (0x54 & boardBits) == 0x54) {
                    return (0b010 & validStatus) != 0;
                }
            }

            break;
        }

        return (status & validStatus) != 0;

    }

    public static void main(String[] args) {
        System.out.println(new ValidTicTacToeStateIV().validTicTacToe(new String[]{
                "XXX", "   ", "OOO"}));
    }
}
