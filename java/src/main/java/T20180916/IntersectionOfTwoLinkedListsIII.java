package T20180916;

import java.util.HashSet;
import java.util.Set;

/**
 * Description
 * Write a program to find the node at which the intersection of two singly linked lists begins.
 * <p>
 * If the two linked lists have no intersection at all, return null.
 * The linked lists must retain their original structure after the function returns.
 * You may assume there are no cycles anywhere in the entire linked structure.
 * <p>
 * Example
 * The following two linked lists:
 * <p>
 * A:          a1 → a2
 * ↘
 * c1 → c2 → c3
 * ↗
 * B:     b1 → b2 → b3
 * begin to intersect at node c1.
 * <p>
 * Challenge
 * Your code should preferably run in O(n) time and use only O(1) memory.
 */
public class IntersectionOfTwoLinkedListsIII {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * @param headA: the first list
     * @param headB: the second list
     * @return: a ListNode
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // write your code here
        if (headA == null || headB == null) {
            return null;
        }
        NodeSet nodeSet = new NodeSet();

        int i = 5;
        while (headA != null && --i > 4) {
            nodeSet.addFailed(headA.val);
            headA = headA.next;
        }
        while (headA != null || headB != null) {
            i = 9;

            while (headA != null && --i > 4) {
                if (nodeSet.addFailed(headA.val)) {
                    return headA;
                }
                headA = headA.next;
            }

            while (headB != null && --i > 0) {
                if (nodeSet.addFailed(headB.val)) {
                    return headB;
                }
                headB = headB.next;
            }

        }

        return null;

    }

    private static class NodeSet {

        long smallSet;//0-63
        long middleSet;//64-127
        long lagerSet;//128-191
        Set<Integer> others = new HashSet<>();

        private boolean addFailed(int val) {
            if (val < 0) {
                return !others.add(val);
            } else if (val < 64) {
                return smallSet == (smallSet = smallSet | (1L << val));
            } else if (val < 128) {
                return middleSet == (middleSet = middleSet | (1L << (val - 64)));
            } else if (val < 192) {
                return lagerSet == (lagerSet = lagerSet | (1L << (val - 128)));
            } else {
                return !others.add(val);
            }
        }
    }

}
