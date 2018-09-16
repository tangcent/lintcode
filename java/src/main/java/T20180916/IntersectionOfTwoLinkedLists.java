package T20180916;

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
public class IntersectionOfTwoLinkedLists {
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
        int lA = lengthOf(headA);
        int lB = lengthOf(headB);
        if (lA > lB) {
            headA = move(headA, lA - lB);
        } else {
            headB = move(headB, lB - lA);
        }
        while (!headA.equals(headB)) {
            headA = headA.next;
            headB = headB.next;
        }
        return null;

    }

    private int lengthOf(ListNode head) {
        int i = 0;
        while ((head = head.next) != null) {
            ++i;
        }
        return i;
    }

    private ListNode move(ListNode head, int steps) {
        while (steps-- != 0 && (head = head.next) != null) {
        }
        return head;
    }


}
