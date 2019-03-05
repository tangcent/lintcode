package T20190301;

import java.util.Iterator;
import java.util.List;

/**
 * Implement an iterator to flatten a 2d vector.
 * <p>
 * Your Vector2D object will be instantiated and called as such:
 * Vector2D i=new Vector2D(vec2d);
 * while(i.hasNext())v[f()]=i.next();
 */

public class Vector2D implements Iterator<Integer> {

    private Iterator<List<Integer>> currOut;
    private Iterator<Integer> curr;

    @SuppressWarnings("unchecked")
    public Vector2D(List<List<Integer>> vec2d) {
        if (vec2d == null || vec2d.isEmpty()) {
            currOut = emptyIterator;
            curr = emptyIterator;
        } else {
            currOut = vec2d.iterator();
            curr = emptyIterator;
        }
    }

    @Override
    public Integer next() {
        return curr.next();
    }

    @Override
    public boolean hasNext() {
        if (curr.hasNext()) {
            return true;
        }
        for (; ; ) {
            if (!currOut.hasNext()) {
                return false;
            }
            final List<Integer> next = currOut.next();
            if (next.isEmpty()) continue;
            curr = next.iterator();
            return true;
        }
    }

    @Override
    public void remove() {
        curr.remove();
    }

    private static Iterator emptyIterator = new Iterator() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            return null;
        }
    };
}