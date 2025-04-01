package leetcode.linked_list;

import java.util.HashMap;
import java.util.Map;

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}

public class _138随机链表的复制 {
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        // 两次遍历
        // 第一次构造链表并且设置next属性
        Node newHead = new Node(head.val), tail = newHead;
        Node p = head.next;
        Map<Node, Node> mapping = new HashMap<>();  // 保存新节点和原始节点的映射，供设置random时使用
        mapping.put(head, newHead);
        while (p != null) {
            tail.next = new Node(p.val);
            tail = tail.next;
            mapping.put(p, tail);
            p = p.next;
        }
        // 第二次设置random属性
        p = head;
        tail = newHead;
        while (p != null) {
            tail.random = mapping.get(p.random);
            tail = tail.next;
            p = p.next;
        }
        return newHead;
    }


    /**
     * @author Leetcode Official
     * 递归创建节点设置next，回溯设置random
     * 关键点：cachedNode必须是全局的，作用域大于方法
     * @param head
     * @return
     */
    Map<Node, Node> cachedNode = new HashMap<Node, Node>();

    public Node copyRandomList_lo(Node head) {
        if (head == null) {
            return null;
        }
        if (!cachedNode.containsKey(head)) {
            Node headNew = new Node(head.val);
            cachedNode.put(head, headNew);
            headNew.next = copyRandomList_lo(head.next);
            headNew.random = copyRandomList_lo(head.random);
        }
        return cachedNode.get(head);
    }
}
