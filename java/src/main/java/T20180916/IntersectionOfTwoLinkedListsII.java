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
public class IntersectionOfTwoLinkedListsII {
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

        while (headA != null) {
            nodeSet.add(headA.val);
            headA = headA.next;
        }

        while (headB != null) {
            if (nodeSet.contain(headB.val)) {
                return headB;
            }
            headB = headB.next;
        }

        return null;

    }

    private static class NodeSet {

        int smallSet;//0-31
        long middleSet;//32-96
        Set<Integer> others = new HashSet<>();

        private void add(int val) {
            if (val < 0) {
                others.add(val);
            } else if (val < 32) {
                smallSet = smallSet | (1 << val);
            } else if (val < 96) {
                middleSet = middleSet | (1L << (val - 32));
            } else {
                others.add(val);
            }
        }

        private boolean contain(int val) {
            if (val < 0) {
                return others.contains(val);
            } else if (val < 32) {
                return (smallSet & (1 << val)) != 0;
            } else if (val < 96) {
                return (middleSet & (1L << (val - 32))) != 0;
            } else {
                return others.contains(val);
            }
        }
    }

    public static void main(String[] args) {

        NodeSet nodeSet = new NodeSet();
//        for (int i = 0; i < 65; i++) {
//            if (i != 32)
//                nodeSet.add(i);
//        }
        nodeSet.add(64);
        System.out.println(nodeSet.contain(32));


    }

}
