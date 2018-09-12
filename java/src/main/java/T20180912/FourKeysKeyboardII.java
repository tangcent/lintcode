package T20180912;

import java.util.LinkedList;
import java.util.List;

/**
 * Description
 * Imagine you have a special keyboard with the following keys:
 * <p>
 * Key 1: (A): Print one 'A' on screen.
 * <p>
 * Key 2: (Ctrl-A): Select the whole screen.
 * <p>
 * Key 3: (Ctrl-C): Copy selection to buffer.
 * <p>
 * Key 4: (Ctrl-V): Print buffer on screen appending it after what has already been printed.
 * <p>
 * Now, you can only press the keyboard for N times (with the above four keys), find out the maximum numbers of 'A' you can print on screen.
 * <p>
 * 1 <= N <= 50
 * Answers will be in the range of 32-bit signed integer.
 * Example
 * Given N = 3, return 3.
 * <p>
 * Explanation:
 * We can at most get 3 A's on screen by pressing following key sequence:
 * A, A, A
 * Given N = 7, return 9.
 * <p>
 * Explanation:
 * We can at most get 9 A's on screen by pressing following key sequence:
 * A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
 */
public class FourKeysKeyboardII {

    /**
     * @param N: an integer
     * @return: return an integer
     */
    public int maxA(int N) {
        Nodes nodes = new Nodes(N);
        Node curr = new InitNode(0, 0, 0);
        while (curr != null) {
            nodes.addNode(curr.A(N));
            nodes.addNode(curr.CtrlV(N));
            nodes.addNode(curr.CtrlACV(N));
            curr = nodes.pop();
        }
        return nodes.maxScreen();
    }

    private class Nodes {

        private int N;

        public Nodes(int n) {
            N = n;
        }

        List<Node> nodes = new LinkedList<>();

        List<Node> finalNodes = new LinkedList<>();

        private void addNode(Node addNode) {
            if (addNode == null) {
                return;
            }
            if (addNode.actions == N) {
                finalNodes.add(addNode);
                return;
            }
            int index = -1;
            boolean replaced = false;
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                if (node.actions == addNode.actions) {
                    if (node.screen <= addNode.screen && node.buffer <= addNode.buffer) {
                        //replace
                        index = i;
                        replaced = true;
                        break;
                    } else if (node.screen >= addNode.screen && node.buffer >= addNode.buffer) {
                        //no change
                        return;
                    }
                }

                if (node.screen >= addNode.screen && node.buffer >= addNode.buffer && node.actions <= addNode.actions) {
                    //no change
                    return;
                }

                if (node.actions > addNode.actions) {
                    index = i;
                    break;
                }


            }
            if (index == -1) {
                nodes.add(addNode);
            } else if (replaced) {
                nodes.set(index, addNode);
            } else {
                nodes.add(index, addNode);
            }
        }

        private Node pop() {
            try {
                return nodes.remove(0);
            } catch (Exception e) {
                return null;
            }
        }

        public int maxScreen() {
            int max = -1;
            for (Node finalNode : finalNodes) {
                if (finalNode.screen > max) {
                    max = finalNode.screen;
                }
            }
            return max;
        }
    }

    public abstract class Node {
        protected int screen;//num of A in screen

        protected int buffer;//num of A in buffer

        protected int actions;//count of actions

        //Key 1 (A)
        abstract Node A(int n);

        //粘贴
        //Key 4: (Ctrl-V)
        abstract Node CtrlV(int n);

        //Key 2-3-4: (Ctrl-A),(Ctrl-C),(Ctrl-V):
        abstract Node CtrlACV(int n);
    }

    class InitNode extends Node {
        public InitNode(int screen, int buffer, int actions) {
            this.screen = screen;
            this.buffer = buffer;
            this.actions = actions;
        }

        //Key 1 (A)
        @Override
        public Node A(int n) {
//            if (actions + 1 > n) {
//                return null;
//            }
            return new InitNode(screen + 1, buffer, actions + 1);
        }

        //粘贴
        //Key 4: (Ctrl-V)
        @Override
        public Node CtrlV(int n) {
            return null;
        }

        //Key 2-3-4: (Ctrl-A),(Ctrl-C),(Ctrl-V):
        @Override
        public Node CtrlACV(int n) {
            if (screen == 0 || actions + 3 > n) {
                return null;
            }
            //screen->buffer
            //screen*2->screen
            return new CommonNode(screen << 1, screen, actions + 3);
        }
    }

    class CommonNode extends Node {

        public CommonNode(int screen, int buffer, int actions) {
            this.screen = screen;
            this.buffer = buffer;
            this.actions = actions;
        }

        @Override
        Node A(int n) {
            return new CommonNode(screen + 1, buffer, actions + 1);
        }

        @Override
        Node CtrlV(int n) {
            if (actions + 1 > n) {
                return null;
            }
            return new CommonNode(screen + buffer, buffer, actions + 1);
        }

        @Override
        Node CtrlACV(int n) {
            if (actions + 3 > n) {
                return null;
            }
            //screen->buffer
            //screen*2->screen
            return new CommonNode(screen << 1, screen, actions + 3);
        }
    }

    public static void main(String[] args) {
//        FourKeysKeyboard fourKeysKeyboard = new FourKeysKeyboard();
//        FourKeysKeyboardII fourKeysKeyboardII = new FourKeysKeyboardII();
//        for (int i = 1; i < 20; i++) {
//            int a = maxA(i);
//            int b = fourKeysKeyboardII.maxA(i);
//            System.out.println("1->" + a + ",2->" + b);
//            if (a != b) {
//                System.out.println(i);
//                break;
//            }
//        }
        System.out.println(new FourKeysKeyboardII().maxA(9));
    }

}
