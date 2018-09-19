package T20180919;

/**
 * Description
 * Design Tic-Tac-Toe game.
 * <p>
 * board has fixed size of 3
 * X always take the first move
 * If a place already got taken, and one player want to take that place,
 * an AlreadyTakenException will be thrown
 * If one player wins, and somebody try to make another move, a GameEndException will be thrown.
 * <p>
 * Example
 * Input:
 * move(0, 0) // X turn
 * move(1, 0) // O trun
 * move(1, 1) // X turn
 * move(2, 0) // O turn
 * move(2, 2) // X turn and win
 * move(0, 0) //throw GameEndException
 * move(0, 0) // X turn
 * move(0, 0) // throw AlreadyTakenException
 * move(1, 0) // O turn
 * move(1, 1) // X turn
 * move(2, 0) // o turn
 * move(2, 2) // X turn and win
 * <p>
 * You should print blew:
 * <p>
 * x player wins!
 * x player wins!
 */
public class TicTacToe {

    private class AlreadyTakenException extends Exception {
    }

    private class GameEndException extends Exception {
    }

    /**
     * Initialize your data structure here.
     */
    public TicTacToe() {
        action = xAction;
    }

    public boolean move(int row, int col) throws AlreadyTakenException, GameEndException {
        System.out.println("move(" + row + "," + col + ")");
        return action.move(row, col);
    }

    private Action action;//X turn

    private int oBits = 0;
    private int xBits = 0;
    private int oxBits = 0;

    private void reset() {
        action = xAction;
        oBits = 0;
        xBits = 0;
        oxBits = 0;
    }

    private XAction xAction = new XAction();
    private OAction oAction = new OAction();
    private EndedAction endedAction = new EndedAction();

    private interface Action {
        boolean move(int row, int col) throws AlreadyTakenException, GameEndException;
    }

    private abstract class NoEndedAction implements Action {

        @Override
        public boolean move(int row, int col) throws AlreadyTakenException, GameEndException {
            int index = row * 3 + col;
            if (oxBits == (oxBits = oxBits | (1 << index))) {
                throw new AlreadyTakenException();
            }
            if (move(index)) {
                action = endedAction;
                return true;
            } else if (oxBits == 0x1ff) {
                action = endedAction;
            } else {
                action = nextAction();
            }
            return false;
        }

        protected abstract boolean move(int index) throws GameEndException;

        protected abstract Action nextAction();
    }

    private class XAction extends NoEndedAction {

        @Override
        public boolean move(int index) throws GameEndException {
            xBits = xBits | (1 << index);
            if ((xBits & xBits >> 1 & xBits >> 2 & 0x49) != 0
                    || (xBits & xBits >> 3 & xBits >> 6 & 0x7) != 0
                    || (index & 1) == 0 && ((xBits & 0x54) == 0x54) || (xBits & 0x111) == 0x111) {
                System.out.println("x player wins!");
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected Action nextAction() {
            return oAction;
        }
    }

    private class OAction extends NoEndedAction {

        @Override
        public boolean move(int index) throws GameEndException {
            oBits = oBits | (1 << index);
            if ((oBits & oBits >> 1 & oBits >> 2 & 0x49) != 0
                    || (oBits & oBits >> 3 & oBits >> 6 & 0x7) != 0
                    || (index & 1) == 0 && ((oBits & 0x54) == 0x54) || (oBits & 0x111) == 0x111) {
                System.out.println("o player wins!");
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected Action nextAction() {
            return xAction;
        }
    }

    private class EndedAction implements Action {

        @Override
        public boolean move(int row, int col) throws GameEndException {
            reset();
            throw new GameEndException();
        }
    }

    private void print() {
        System.out.println("-----------");
        for (int row = 0; row < 3; row++) {
            System.out.print("|");
            for (int col = 0; col < 3; col++) {
                int i = 1 << (row * 3 + col);
                if ((oBits & i) != 0) {
                    System.out.print("O");
                } else if ((xBits & i) != 0) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }
        System.out.println("\n-----------");
    }

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();

        move(ticTacToe, 1, 0);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 1, 0);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 1, 0);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 2, 0);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 2, 0);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 1, 0);
        move(ticTacToe, 2, 0);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 1, 0);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 2, 0);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 2, 0);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 1, 0);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 2, 0);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 2, 0);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 2, 0);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 2, 0);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 1, 0);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 0, 0);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 0, 2);
        move(ticTacToe, 1, 0);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 2, 2);
        move(ticTacToe, 1, 0);
        move(ticTacToe, 1, 1);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 2, 1);
        move(ticTacToe, 1, 2);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 0, 1);
        move(ticTacToe, 1, 1);

    }

    public static void move(TicTacToe ticTacToe, int row, int col) {
        try {
            ticTacToe.move(row, col);
        } catch (AlreadyTakenException e) {
//            e.printStackTrace();
        } catch (GameEndException e) {
//            e.printStackTrace();
        }
//        ticTacToe.print();
    }
}
