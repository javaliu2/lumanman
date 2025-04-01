package leetcode.linked_list;

import utils.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _148排序链表 {
    public ListNode sortList_my(ListNode head) {
        if (head == null) {
            return null;
        }
        // 遍历链表，将所有值存储于arraylist，对list排序
        ListNode p = head;
        List<Integer> list = new ArrayList<>();
        while (p != null) {
            list.add(p.val);
            p = p.next;
        }
        Collections.sort(list);
        // 再将排序后的有序值逐一赋值给原始链表
        p = head;
        int i = 0;
        while (i < list.size()) {
            p.val = list.get(i);
            p = p.next;
            ++i;
        }
        return head;
    }

    /**
     * @author DeepSeek
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        // 递归终止条件：链表为空或只有一个节点
        if (head == null || head.next == null) {
            return head;
        }
        // 找到链表的中点
        ListNode mid = findMiddle(head);
        ListNode rightHead = mid.next; // 右半部分的头节点
        mid.next = null; // 断开链表
        // 递归排序左半部分和右半部分
        ListNode left = sortList(head);
        ListNode right = sortList(rightHead);
        // 合并两个有序链表
        return mergeTwoLists(left, right);
    }

    // 找到链表的中点（快慢指针法）
    private ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next; // fast 从 head.next 开始，确保 slow 指向中点或左中点
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 合并两个有序链表
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1); // 虚拟头节点
        ListNode curr = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        // 将剩余的链表接上
        if (l1 != null) {
            curr.next = l1;
        }
        if (l2 != null) {
            curr.next = l2;
        }
        return dummy.next;
    }
}
