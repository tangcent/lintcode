package T20161227;


import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Flatten Nested List Iterator
 * http://www.lintcode.com/en/problem/flatten-nested-list-iterator/
 * Given a nested list of integers, implement an iterator to flatten it.
 * <p>
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 * Example
 * Given the list [[1,1],2,[1,1]], By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].
 * <p>
 * Given the list [1,[4,[6]]], By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,4,6].
 * Tags
 * Recursion Stack Data Structure Design Google
 */
public class NestedIterator implements Iterator<Integer> {
    private Stack<NestedInteger> stack = new Stack<>();

    public NestedIterator(List<NestedInteger> nestedList) {
        pushList(nestedList);
    }

    private void pushList(List<NestedInteger> nestedList) {
        for (int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }
    }

    // @return {int} the next element in the iteration
    @Override
    public Integer next() {
        NestedInteger nestedInteger = stack.pop();
        while (!nestedInteger.isInteger()) {
            pushList(nestedInteger.getList());
            nestedInteger = stack.pop();
        }
        return nestedInteger.getInteger();
    }

    // @return {boolean} true if the iteration has more element or false
    @Override
    public boolean hasNext() {
        if (stack.isEmpty())
            return false;
        NestedInteger nestedInteger = stack.peek();
        while (!nestedInteger.isInteger()) {
            nestedInteger = stack.pop();
            pushList(nestedInteger.getList());
            if (stack.isEmpty()) {
                return false;
            }
            nestedInteger = stack.peek();
        }
        return true;
    }

    @Override
    public void remove() {
    }

    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer,
        // rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds,
        // if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds,
        // if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }
}
