package leetcode.linked_list;

import utils.ListNode;

public class _24两两交换链表中的节点 {
    /**
     * @author xs
     * 胸有成竹，游刃有余
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(-1, head);
        ListNode pre_p = dummy, p = dummy.next, q, next_q;
        while (p != null && p.next != null) {
            q = p.next;
            next_q = q.next;
            // 交换节点
            pre_p.next = q;
            q.next = p;
            p.next = next_q;
            // 交换下一组节点
            pre_p = p;
            p = p.next;
        }
        return dummy.next;
    }
}
