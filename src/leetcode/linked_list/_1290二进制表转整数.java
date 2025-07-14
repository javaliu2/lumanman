package leetcode.linked_list;

import utils.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class _1290二进制表转整数 {
    public int getDecimalValue(ListNode head) {
        Deque<Integer> stack = new ArrayDeque<>();
        while (head != null) {
            stack.push(head.val);
            head = head.next;
        }
        int base = 1, ans = 0;
        while (!stack.isEmpty()) {
            int v = stack.pop();
            ans += v * base;
            base <<= 1;
        }
        return ans;
    }
    public int getDecimalValue_lo(ListNode head) {
        ListNode curNode = head;
        int ans = 0;
        while (curNode != null) {
            ans = ans * 2 + curNode.val;
            curNode = curNode.next;
        }
        return ans;
    }
}
