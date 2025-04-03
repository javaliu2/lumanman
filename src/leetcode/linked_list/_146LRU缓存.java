package leetcode.linked_list;

import java.util.HashMap;
import java.util.Map;

public class _146LRU缓存 {

}
class LRUCache2 {
    /**
     * 使用双向链表的原因是为了在O(1)的时间内完成删除最后一个节点、移动节点到头部等操作
     * 使用空间换取时间
     */
    private Map<Integer, DLLNode> hash;
    private DLLNode head, tail;
    private int capacity;
    public LRUCache2(int capacity) {
        this.capacity = capacity;
        head = new DLLNode();
        tail = new DLLNode();
        head.next = tail;
        tail.prev = head;
        hash = new HashMap<>();
    }

    public int get(int key) {
        boolean isExist = hash.containsKey(key);
        if (!isExist) {
            return -1;
        }
        // 还是不够一般化，存在node的prev或者next指向自身的情况
        DLLNode node = hash.get(key);
        DLLNode node_prev = node.prev;
        DLLNode node_next = node.next;
        DLLNode head_next = head.next;
        head.next = node;
        node.prev = head;
        node.next = head_next;
        head_next.prev = node;
        node_prev.next = node_next;
        node_next.prev = node_prev;
        return node.value;
    }

    public void put(int key, int value) {
        boolean isExist = hash.containsKey(key);
        if (!isExist) {
            DLLNode newNode = new DLLNode(key, value);
            DLLNode head_next = head.next;
            head.next = newNode;
            newNode.prev = head;
            newNode.next = head_next;
            head_next.prev = newNode;
            hash.put(key, newNode);
            if (hash.size() > this.capacity) {
                DLLNode tail_prev = tail.prev;
                DLLNode tail_prev_prev = tail.prev.prev;
                tail_prev_prev.next = tail;
                tail.prev = tail_prev_prev;
                hash.remove(tail_prev.key);
            }
        } else {
            DLLNode node = hash.get(key);
            node.value = value;
            DLLNode node_prev = node.prev;
            DLLNode node_next = node.next;
            DLLNode head_next = head.next;
            head.next = node;
            node.prev = head;
            node.next = head_next;
            head_next.prev = node;
            node_prev.next = node_next;
            node_next.prev = node_prev;
        }
    }
}
/**
 Double linked list node
 */
class DLLNode {
    int key;
    int value;
    DLLNode next, prev;
    DLLNode() {

    }
    DLLNode(int key, int value) {
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
    }
    DLLNode(int key, int value, DLLNode prev, DLLNode next) {
        this.key = key;
        this.value = value;
        this.prev = prev;
        this.next = next;
    }
}

/**
 * @author G-bro
 */
class LRUCache {
    private Map<Integer, DLLNode> hash;
    private DLLNode head, tail;
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        hash = new HashMap<>();
        head = new DLLNode();
        tail = new DLLNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!hash.containsKey(key)) {
            return -1;
        }
        DLLNode node = hash.get(key);
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (hash.containsKey(key)) {
            DLLNode node = hash.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            DLLNode newNode = new DLLNode(key, value);
            hash.put(key, newNode);
            insertToHead(newNode);

            if (hash.size() > capacity) {
                DLLNode tailPrev = tail.prev;
                removeNode(tailPrev);
                hash.remove(tailPrev.key);  // 这里正确删除 key
            }
        }
    }

    // 将节点移动到头部
    private void moveToHead(DLLNode node) {
        removeNode(node);
        insertToHead(node);
    }

    // 插入到头部
    private void insertToHead(DLLNode node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    // 删除某个节点
    private void removeNode(DLLNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}