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
public class LinkedListCycleII {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * @param head: The first node of linked list.
     * @return: The node where the cycle begins. if there is no cycle, return null
     */
    public ListNode detectCycle(ListNode head) {
        // write your code here
        ListNode slow = head;

        try {
            ListNode fast = head.next;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next.next;
            }
            while (head != slow.next) {
                head = head.next;
                slow = slow.next;
            }
            return head;
        } catch (Exception e) {
            return null;
        }
    }
}
