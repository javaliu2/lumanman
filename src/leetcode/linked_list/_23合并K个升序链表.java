package leetcode.linked_list;

import utils.ListNode;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class _23合并K个升序链表 {
    /**
     * @author xs
     * trivial solution
     */
    public ListNode mergeKLists(ListNode[] lists) {
        /**
         * 两两合并
         */
        // ListNode h = mergeList(lists[0], lists[1]);
        // while (h != null) {
        //     System.out.printf("%d, ", h.val);
        //     h = h.next;
        // }
        if (lists == null || lists.length == 0) {
            return null;
        }
        int n = lists.length;
        if (n == 1) {
            return lists[0];
        }
        ListNode l1 = mergeList(lists[0], lists[1]);
        for (int i = 2; i < n; ++i) {
            l1 = mergeList(l1, lists[i]);
        }
        return l1;
    }

    ListNode mergeList(ListNode p, ListNode q) {
        ListNode head;
        if (p == null) {
            return q;
        }
        if (q == null) {
            return p;
        }
        if (p.val < q.val) {
            head = p;
            p = p.next;
        } else {
            head = q;
            q = q.next;
        }
        ListNode tail = head;
        while (p != null && q != null) {
            if (p.val < q.val) {
                tail.next = p;
                p = p.next;
            } else {
                tail.next = q;
                q = q.next;
            }
            tail = tail.next;
        }
        if (p != null) {
            tail.next = p;
        }
        if (q != null) {
            tail.next = q;
        }
        return head;
    }

    /**
     * 使用优先级队列存储K个链表的头元素，
     * 每次选择队列的队首元素，将该元素所在链表的第二个元素加入队列（如果有的话）
     * 重复以上过程直至队列为空
     * 构建Status类存储链表首节点的值以及节点自身
     * @param lists
     * @return
     */
    public ListNode mergeKLists_lo(ListNode[] lists) {
        PriorityQueue<Status> queue = new PriorityQueue<Status>();
        // 将所有链表头节点值以及其自身加入优先级队列
        for (ListNode node : lists) {
            if (node != null) {
                queue.offer(new Status(node.val, node));
            }
        }
        ListNode head = new ListNode(0), tail = head;
        while (!queue.isEmpty()) {
            Status f = queue.poll();
            tail.next = f.ptr;
            tail = tail.next;
            if (f.ptr.next != null) {
                queue.offer(new Status(f.ptr.next.val, f.ptr.next) );
            }
        }
        return head.next;
    }
}
class Status implements Comparable<Status> {
    int val;
    ListNode ptr;
    Status(int val, ListNode ptr) {
        this.val = val;
        this.ptr = ptr;
    }

    public int compareTo(Status status2) {
        return this.val - status2.val;
    }
}
