package T20180909;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Description
 * Given a directed graph, design an algorithm to find out whether there is a route between two nodes.
 * <p>
 * Example
 * Given graph:
 * <p>
 * A----->B----->C
 * \     |
 * \    |
 * \   |
 * \  v
 * ->D----->E
 * for s = B and t = E, return true
 * <p>
 * for s = D and t = C, return false
 */

public class RouteBetweenTwoNodesInGraph {

    class DirectedGraphNode {
        int label;
        ArrayList<DirectedGraphNode> neighbors;

        DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    /*
     * @param graph: A list of Directed graph node
     * @param s: the starting Directed graph node
     * @param t: the terminal Directed graph node
     * @return: a boolean value
     */
    public boolean hasRoute(ArrayList<DirectedGraphNode> graph, DirectedGraphNode s, DirectedGraphNode t) {
        // write your code here
        final int tLabel = t.label;
        if (s.label == tLabel)
            return true;
        DirectedGraphNode c = s;
        Stack<DirectedGraphNode> stack = new Stack<>();
        for (; ; ) {
            for (DirectedGraphNode neighbor : c.neighbors) {
                if (neighbor.label == tLabel) {
                    return true;
                } else {
                    stack.push(neighbor);
                }
            }

            if (stack.isEmpty()) {
                return false;
            }
            c = stack.pop();
        }
    }
}
