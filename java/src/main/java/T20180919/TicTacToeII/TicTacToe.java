package T20180919.TicTacToeII;

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

    private static class AlreadyTakenException extends Exception {
    }

    private static class GameEndException extends Exception {
    }

    /**
     * Initialize your data structure here.
     */
    public TicTacToe() {
        action = xAction;
    }

    public boolean move(int row, int col) throws AlreadyTakenException, GameEndException {
        return action.move(this, row, col);
    }

    private Action action;//X turn

    private int oBits = 0;
    private int xBits = 0;
    private int oxBits = 0;

    private static XAction xAction = new XAction();
    private static OAction oAction = new OAction();
    private static EndedAction endedAction = new EndedAction();

    private interface Action {
        boolean move(TicTacToe ticTacToe, int row, int col) throws AlreadyTakenException, GameEndException;
    }

    private static abstract class NoEndedAction implements Action {

        @Override
        public boolean move(TicTacToe ticTacToe, int row, int col) throws AlreadyTakenException, GameEndException {
//            int index = row * 3 + col;
            int index = 0;
            switch (row) {
                case 0:
                    switch (col) {
                        case 0:
                            index = 1;
                            break;
                        case 1:
                            index = 2;
                            break;
                        case 2:
                            index = 4;
                            break;
                    }
                    break;
                case 1:
                    switch (col) {
                        case 0:
                            index = 8;
                            break;
                        case 1:
                            index = 16;
                            break;
                        case 2:
                            index = 32;
                            break;
                    }
                    break;
                case 2:
                    switch (col) {
                        case 0:
                            index = 64;
                            break;
                        case 1:
                            index = 128;
                            break;
                        case 2:
                            index = 256;
                            break;
                    }
                    break;
                default:
            }

            if (ticTacToe.oxBits == (ticTacToe.oxBits = ticTacToe.oxBits | index)) {
                throw new AlreadyTakenException();
            }
            return move(ticTacToe, index);
        }

        public boolean check(TicTacToe ticTacToe, int bits, int index) throws GameEndException {
            switch (index) {
                case 0x100:
                    return (bits & 0x1c0) == 0x1c0
                            || (bits & 0x124) == 0x124
                            || (bits & 0x111) == 0x111;
                case 0x80:
                    return (bits & 0x1c0) == 0x1c0
                            || (bits & 0x92) == 0x92;
                case 0x40:
                    return (bits & 0x1c0) == 0x1c0
                            || (bits & 0x49) == 0x49
                            || (bits & 0x54) == 0x54;
                case 0x20:
                    return (bits & 0x38) == 0x38
                            || (bits & 0x124) == 0x124;
                case 0x10:
                    return (bits & 0x38) == 0x38
                            || (bits & 0x124) == 0x124
                            || (bits & 0x111) == 0x111
                            || (bits & 0x54) == 0x54;
                case 0x8:
                    return (bits & 0x38) == 0x38
                            || (bits & 0x49) == 0x49;
                case 0x4:
                    return (bits & 0x7) == 0x7
                            || (bits & 0x124) == 0x124
                            || (bits & 0x54) == 0x54;
                case 0x2:
                    return (bits & 0x7) == 0x7
                            || (bits & 0x92) == 0x92;
                case 0x1:
                    return (bits & 0x7) == 0x7
                            || (bits & 0x49) == 0x49
                            || (bits & 0x111) == 0x111;

            }
            return false;
        }

        protected abstract boolean move(TicTacToe ticTacToe, int index) throws GameEndException;
    }

    private static class XAction extends NoEndedAction {

        @Override
        public boolean move(TicTacToe ticTacToe, int index) throws GameEndException {
            ticTacToe.xBits = ticTacToe.xBits | index;
            if (check(ticTacToe, ticTacToe.xBits, index)) {
                ticTacToe.action = endedAction;
                return true;
            } else {
                ticTacToe.action = oAction;
                return false;
            }
        }
    }

    private static class OAction extends NoEndedAction {

        @Override
        public boolean move(TicTacToe ticTacToe, int index) throws GameEndException {
            ticTacToe.oBits = ticTacToe.oBits | index;
            if (check(ticTacToe, ticTacToe.oBits, index)) {
                ticTacToe.action = endedAction;
                return true;
            } else {
                ticTacToe.action = xAction;
                return false;
            }
        }
    }

    private static class EndedAction implements Action {

        @Override
        public boolean move(TicTacToe ticTacToe, int row, int col) throws GameEndException {
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
