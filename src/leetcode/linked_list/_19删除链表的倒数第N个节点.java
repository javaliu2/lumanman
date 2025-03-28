package leetcode.linked_list;

import utils.ListNode;

import java.util.Stack;

public class _19删除链表的倒数第N个节点 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 递归，利用调用栈记录倒数节点的信息
        // 退栈的时候，可以记录退到倒数第几个节点了
        // 不用调用栈，使用数据结构栈
        Stack<ListNode> s = new Stack<ListNode>();
        ListNode p = head, q;
        while (p != null) {
            s.push(p);
            p = p.next;
        }
        while (n > 0) {
            p = s.pop();
            n--;
        }
        if (!s.isEmpty()) {
            q = s.pop();
            q.next = p.next;
            while (!s.isEmpty()) {
                q = s.pop();
            }
        } else {
            q = p.next;
        }
        return q;
    }

    /**
     * two pointers, clever
     * @author Leetcode Official
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd_tp(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode first = head;
        ListNode second = dummy;  // 借助哑元节点完成统一化处理
        for (int i = 0; i < n; ++i) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }
}
