package T20180909;

import java.util.ArrayList;
import java.util.Iterator;
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

public class RouteBetweenTwoNodesInGraphII {

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
        NodeProvider provider = new NodeNeighborWrap(s);
        Stack<NodeProvider> stack = new Stack<>();
        try {
            for (; ; ) {
                DirectedGraphNode neighbor = provider.neighbor();
                if (neighbor == null) {
                    stack.pop();
                    provider = stack.peek();
                    continue;
                }

                if (neighbor.label == tLabel) {
                    return true;
                } else {
                    provider = new NodeNeighborWrap(neighbor);
                    stack.push(provider);
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    interface NodeProvider {
        DirectedGraphNode neighbor();
    }

    class NodeNeighborWrap implements NodeProvider {
        Iterator<DirectedGraphNode> iterator;

        public NodeNeighborWrap(DirectedGraphNode node) {
            iterator = node.neighbors.iterator();
        }

        public DirectedGraphNode neighbor() {
            if (iterator.hasNext()) {
                return iterator.next();
            } else {
                return null;
            }
        }
    }

}
