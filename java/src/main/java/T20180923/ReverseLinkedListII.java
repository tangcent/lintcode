package T20180923;

/**
 * Description
 * Reverse a linked list from position m to n.
 * <p>
 * Given m, n satisfy the following condition: 1 ≤ m ≤ n ≤ length of list.
 * <p>
 * Example
 * Given 1->2->3->4->5->NULL, m = 2 and n = 4, return 1->4->3->2->5->NULL.
 * <p>
 * Challenge
 * Reverse it in-place and in one-pass
 */
public class ReverseLinkedListII {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * @param head: ListNode head is the head of the linked list
     * @param m:    An integer
     * @param n:    An integer
     * @return: The head of the reversed ListNode
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        // write your code here
        ListNode newTail;
        ListNode newTailNext;

        newTailNext = null;
        newTail = head;
        int i = 1;
        for (; i < m; ++i) {
            newTailNext = newTail;
            newTail = newTail.next;
        }

        ListNode pre = null;
        ListNode curr = newTail;
        ListNode next = newTail.next;
        for (; i < n; ++i) {
            curr.next = pre;
            pre = curr;
            curr = next;
            next = next.next;
        }
        curr.next = pre;
        newTail.next = next;
        if (newTailNext != null) {
            newTailNext.next = curr;
            return head;
        } else {
            return curr;
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode node = head;
        node.next = new ListNode(2);
        node = node.next;
        node.next = new ListNode(3);
        node = node.next;
        node.next = new ListNode(4);
        node = node.next;
        node.next = new ListNode(5);
        node = node.next;
        node.next = new ListNode(6);

        node = new ReverseLinkedListII().reverseBetween(head, 2, 3);
        while (node != null) {
            System.out.println(node.val + "->");
            node = node.next;
        }
    }
}
