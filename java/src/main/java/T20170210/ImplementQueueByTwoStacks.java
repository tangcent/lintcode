package T20170210;

import java.util.Stack;

/**
 * Created by TomNg on 2017/2/10.
 * Implement Queue by Two Stacks
 * As the title described, you should only use two stacks to implement a queue's actions.
 * <p>
 * The queue should support push(element), pop() and top() where pop is pop the first(a.k.a front) element in the queue.
 * <p>
 * Both pop and top methods should return the value of first element.
 * Example
 * push(1)
 * pop()     // return 1
 * push(2)
 * push(3)
 * top()     // return 2
 * pop()     // return 2
 */
public class ImplementQueueByTwoStacks {
    public class Queue {
        private Stack<Integer> stack1;
        private Stack<Integer> stack2;

        public Queue() {
            stack1 =new Stack<>();
            stack2 =new Stack<>();
        }

        public void push(int element) {
            stack1.push(element);
        }

        public int pop() {
            checkMove();
            return stack2.pop();
        }

        public int top() {
            checkMove();
            return stack2.peek();
        }

        private void checkMove() {
            if (stack2.empty()) {
                while (!stack1.empty()) {
                    stack2.push(stack1.pop());
                }
            }
        }
    }
}
