package T20170107;

/**
 * Created by TomNg on 2017/2/8.
 */
public class Nth2LastNodeInList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    /**
     * @param head: The first node of linked list.
     * @param n:    An integer.
     * @return: Nth to last node of a singly linked list.
     */
    ListNode nthToLast(ListNode head, int n) {
        ListNode pre = head;
        ListNode nLast = pre;
        for (int i = 0; i < n; i++) {
            nLast = nLast.next;
        }
        while (nLast != null) {
            pre = pre.next;
            nLast = nLast.next;
        }
        return pre;
    }
}
