package leetcode.linked_list;

import utils.ListNode;

import java.util.HashSet;
import java.util.Set;
public class _160相交链表 {
    public ListNode getIntersectionNode_trivial(ListNode headA, ListNode headB) {
        /**
         * 朴素的做法，两次遍历
         */
        for (ListNode p = headA; p != null; p = p.next) {
            for (ListNode q = headB; q != null; q = q.next) {
                if (p == q) {
                    return p;
                }
            }
        }
        return null;
    }
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        /**
         * 官方解法，将任意一个链表中所有节点存储于hashset中，遍历另外一个链表
         * 如果节点出现于hashset中，即表明这是第一个相交的节点，返回该节点
         */
        Set<ListNode> set = new HashSet<>();
        for (ListNode p = headA; p != null; p = p.next) {
            set.add(p);
        }
        for (ListNode p = headB; p != null; p = p.next) {
            if (set.contains(p)) {
                return p;
            }
        }
        return null;
    }
}
