package leetcode.linked_list;

import utils.ListNode;

import java.util.HashSet;
import java.util.Set;

public class _141环形链表 {
    public boolean hasCycle_my(ListNode head) {
        /**
         * 使用hashset存储遍历过的节点，如果通过next指针访问到hashset中的节点，表明存在环
         * next为null，不存在环
         */
        Set<ListNode> hashset = new HashSet<>();
        ListNode p = head;
        while (p != null) {
            if (hashset.contains(p)) {
                break;
            }
            hashset.add(p);
            p = p.next;
        }
        return p != null;
    }

    /**
     * 快慢指针
     * @author LeetCode Official
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
