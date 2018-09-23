package T20180923;

/**
 * Description
 * Reverse a linked list.
 * <p>
 * Example
 * For linked list 1->2->3, the reversed linked list is 3->2->1
 * <p>
 * Challenge
 * Reverse it in-place and in one-pass
 */
public class ReverseLinkedList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * @param head: n
     * @return: The new head of reversed linked list.
     */
    public ListNode reverse(ListNode head) {
        // write your code here

        if (head == null) {
            return head;
        }
        ListNode pre = null;
        ListNode curr = head;
        ListNode next = head.next;

        while (next != null) {
            curr.next = pre;
            pre = curr;
            curr = next;
            next = next.next;
        }
        curr.next = pre;
        return curr;

    }

}
