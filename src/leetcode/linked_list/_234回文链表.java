package leetcode.linked_list;

import utils.ListNode;

import java.util.ArrayList;
import java.util.List;

public class _234回文链表 {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode() {}
     *     ListNode(int val) { this.val = val; }
     *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    /**
     * 获取以head为头结点链表的反转链表
     */
    ListNode reverse(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode r = reverse(head.next);
        // 这样会死循环，比如1->2->3
        // 3作为head被返回，回到head为2的方法调用，下面这行代码将2<-3，但是还保留着2->3的关系
        // 回到head为1的方法调用，1<-2，这样2的next关系被设置，但是1->2的关系保留，同时这个关系不会被修正了
        // 或者一开始进入方法的时候，保留该头结点（反转后的尾节点），判断是否回到了头结点的方法层
        // 或者主动修正head.next关系，官方代码是采取的这种方式
        head.next.next = head;
        head.next = null;
        return r;
    }

    // 我的解法还是太啰嗦了
    public boolean isPalindrome_my(ListNode head) {
        // 使用list保存head的关系
        List<ListNode> arr = new ArrayList<>();
        ListNode p = head;
        while (p != null) {
            arr.add(p);
            p = p.next;
        }
        ListNode head2 = reverse(head);
        // while (head2 != null) {
        //     System.out.printf("%d ", head2.val);
        //     head2 = head2.next;
        // }
        // System.out.println();
        // while (head != null) {
        //     System.out.printf("%d", head.val);
        //     head = head.next;
        // }
        for (ListNode node : arr) {
            if (node.val != head2.val) {
                return false;
            }
            head2 = head2.next;
        }
        return true;
    }

    /**
     * Leetcode Official Idea
     */
    private ListNode frontPointer;

    public boolean isPalindrome(ListNode head) {
        frontPointer = head;
        return recursivelyCheck(head);
    }

    boolean recursivelyCheck(ListNode currentNode) {
        if (currentNode != null) {
            if (!recursivelyCheck(currentNode.next)) {
                return false;
            }
            if (currentNode.val != frontPointer.val) {
                return false;
            }
            frontPointer = frontPointer.next;
        }
        return true;
    }
}
