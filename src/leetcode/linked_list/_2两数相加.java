package leetcode.linked_list;

import utils.ListNode;

public class _2两数相加 {
    /**
     * 温故而知新
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 首先计算这两个值，然后相加，最后将结果值映射到链表表示
        // 链表节点的个数为结果值的位数
        // 上面思路不可行，因为假设链表节点个数为100，每个节点值都为9，那么其值至少为9*10^99
        // 超出long类型数的最大值9*10^18
        // 所以模拟加法。因为链表中数值是逆序存储的
        // res[i] = l1[i] + l2[i] + carry[i-1]
        // 结果链表节点采用new的方式而不是借用l1或者l2的节点
        int carry = 0, t, l1_val, l2_val;
        ListNode ans = new ListNode(-1), tail = ans;
        // l1和l2均为null的时候，退出循环
        while (l1 != null || l2 != null) {
            if (l1 == null) {
                l1_val = 0;
            } else {
                l1_val = l1.val;
            }
            if (l2 == null) {
                l2_val = 0;
            } else {
                l2_val = l2.val;
            }
            t = l1_val + l2_val + carry;
            carry = t / 10;
            t %= 10;
            tail.next = new ListNode(t);
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
            tail = tail.next;
        }
        // 判断是否有进位
        if (carry == 1) {
            tail.next = new ListNode(1);
        }
        return ans.next;
    }
}
