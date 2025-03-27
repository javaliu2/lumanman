package leetcode.linked_list;

import utils.ListNode;

public class _21合并两个有序链表 {

    public ListNode mergeTwoLists_recursion(ListNode p, ListNode q) {
        ListNode head;
        if (p == null) {
            return q;
        } else if (q == null) {
            return p;
        }
        if (p.val < q.val) {
            head = p;
            head.next = mergeTwoLists_recursion(p.next, q);
        } else {
            head = q;
            head.next = mergeTwoLists_recursion(p, q.next);
        }
        return head;
    }
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode head, p = list1, q = list2, tail;
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        // 选择list1和list2中值小的结点作为结果链表的头结点
        if (list1.val < list2.val) {
            head = p;
            p = p.next;
        } else {
            head = q;
            q = q.next;
        }
        tail = head;
        // 循环链表判断p和q谁的val更小，将更小的节点追加为tail节点，并且将其后移
        while (p != null && q != null) {
            if (p.val < q.val) {
                tail.next = p;
                tail = tail.next;
                p = p.next;
            } else {
                tail.next = q;
                tail = tail.next;
                q = q.next;
            }
        }
        while (p != null) {
            tail.next = p;
            p = p.next;
            tail = tail.next;
        }
        while (q != null) {
            tail.next = q;
            q = q.next;
            tail = tail.next;
        }
        return head;
    }

    /**
     *
     * @author Leetcode Official
     * 还得是官方题解，有理有据
     * 题解：https://leetcode.cn/problems/merge-two-sorted-lists/solutions/226408/he-bing-liang-ge-you-xu-lian-biao-by-leetcode-solu/?envType=study-plan-v2&envId=top-100-liked
     * @return
     */
    public ListNode mergeTwoLists_lo(ListNode l1, ListNode l2) {
        ListNode prehead = new ListNode(-1);
        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = l1 == null ? l2 : l1;
        return prehead.next;
    }
}
