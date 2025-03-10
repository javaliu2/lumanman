package leetcode.linked_list;

import utils.ListNode;

public class _206反转链表 {
    /**
     * 以head为头结点的链表
     * 子问题：func(head.next)
     * 相当于是使用调用栈保存了节点之间的关系
     * 写的不好，没有官方简洁巧妙
     */
    ListNode ans;

    ListNode func(ListNode head) {
        if (head != null && head.next == null) {
            ans = head;
            return head;
        }
        ListNode l = func(head.next);
        l.next = head;
        return head;
    }

    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        func(head);
        head.next = null;
        return ans;
    }
}
