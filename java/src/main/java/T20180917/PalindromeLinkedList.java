package T20180917;


/**
 * Description
 * Implement a function to check if a linked list is a palindrome.
 * <p>
 * Example
 * Given 1->2->1, return true
 * <p>
 * Challenge
 * Could you do it in O(n) time and O(1) space?
 */
public class PalindromeLinkedList {


    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    /**
     * @param head: A ListNode.
     * @return: A boolean.
     */
    public boolean isPalindrome(ListNode head) {
        // write your code here
        if (head == null || head.next == null) {
            return true;
        }

        try {
            ListNode slow = head;
            ListNode fast = head;
            ListNode pre = null;
            ListNode next = slow.next;
            while (fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow.next = pre;
                pre = slow;
                slow = next;
                next = next.next;
            }

            if (fast.next == null) {
                slow = pre;
            } else {
                slow.next = pre;
            }

            while (slow != null) {
                if (slow.val != next.val) {
                    return false;
                }
                slow = slow.next;
                next = next.next;
            }
            return true;
        } catch (Exception e) {
            return true;
        }

    }
}
