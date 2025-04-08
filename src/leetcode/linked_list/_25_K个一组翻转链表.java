package leetcode.linked_list;

import utils.ListNode;

public class _25_K个一组翻转链表 {

    /**
     * idea: LC Official
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummyHead = new ListNode(-1, head);
        ListNode pre = dummyHead, tail, next;
        while (true) {
            tail = pre;
            int t = k;
            while (tail != null && t > 0) {
                tail = tail.next;
                t--;
            }
            if (tail != null) {
                next = tail.next;
                // 翻转head至tail之间的链表
                ListNode p = reverseList(head, tail);
                while (p != null) {
                    System.out.printf("%d, ", p.val);
                    p = p.next;
                }
                System.out.println();
                pre.next = tail;
                head.next = next;
            } else {
                break;
            }
            pre = head;
            head = next;
        }
        return dummyHead.next;
    }

    /**
     * 借鉴 206反转链表
     * @param head
     * @param tail
     * @return
     */
    ListNode reverseList(ListNode head, ListNode tail) {
        if (head == tail) {
            return head;
        }
        ListNode newHead = reverseList(head.next, tail);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
