package T20180916;

/**
 * Description
 * Given a linked list, determine if it has a cycle in it.
 * <p>
 * Example
 * Given -21->10->4->5, tail connects to node index 1, return true
 * <p>
 * Challenge
 * Follow up:
 * Can you solve it without using extra space?
 */
public class LinkedListCycle {

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
     * @return: True if it has a cycle, or false
     */
    public boolean hasCycle(ListNode head) {
        // write your code here
        ListNode slow = head;

        try {
            ListNode fast = head.next;
            while (slow != null & fast != null) {
                if (slow == fast || slow == fast.next) {
                    return true;
                }
                slow = slow.next;
                fast = fast.next.next;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
