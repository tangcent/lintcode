package T20170210;

import java.util.Stack;

/**
 * Created by TomNg on 2017/2/10
 * Min Stack
 * Implement a stack with min() function, which will return the smallest number in the stack.
 * <p>
 * It should support push, pop and min operation all in O(1) cost.
 * <p>
 * Notice
 * <p>
 * min operation will never be called if there is no number in the stack.
 * <p>
 * Example
 * push(1)
 * pop()   // return 1
 * push(2)
 * push(3)
 * min()   // return 2
 * push(1)
 * min()   // return 1.
 */
public class MinStack {
    private class Warp {
        int val;
        int min;

        public Warp(int val) {
            this.val = val;
            this.min = val;
        }

        public Warp(int val, int min) {
            this.val = val;
            this.min = min < val ? min : val;
        }
    }

    private Stack<Warp> coreStack;

    public MinStack() {
        coreStack = new Stack<>();
    }

    public void push(int number) {
        if (coreStack.empty()) {
            coreStack.push(new Warp(number));
        } else {
            coreStack.push(new Warp(number, min()));
        }
    }

    public int pop() {
        return coreStack.pop().val;
    }


    public int min() {
        return coreStack.peek().min;
    }
}
