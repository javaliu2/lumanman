package leetcode.linked_list;

import java.util.*;

public class _16_25LRU缓存回顾 {


    class LRUCache {
        // map存储
        private Map<Integer, DoubleLinkedList.Node> map;
        private DoubleLinkedList list;
        private int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();
            this.list = new DoubleLinkedList();
        }

        public int get(int key) {
            DoubleLinkedList.Node node = map.get(key);
            if (node == null) {
                return -1;
            }
            list.remove(node);
            list.addFirst(node);
            return node.getValue();
        }

        public void put(int key, int value) {
            DoubleLinkedList.Node node = map.get(key);
            if (node != null) {
                node.setValue(value);
                list.remove(node);
            } else {
                node = new DoubleLinkedList.Node(key, value);
            }
            list.addFirst(node);
            map.put(key, node);
            if (map.size() > capacity) {
                int t = list.getLastNode().getKey();
                map.remove(t);
                list.remove(list.getLastNode());
            }
        }
    }

    class DoubleLinkedList {
        private Node dummyHead;
        private Node dummyTail;

        static class Node {
            private int key;
            private int value;
            private Node next;
            private Node prev;

            Node() {
                this.prev = null;
                this.next = null;
            }

            Node(int key, int value) {
                this.key = key;
                this.value = value;
                this.prev = null;
                this.next = null;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public int getKey() {
                return key;
            }

            public int getValue() {
                return value;
            }
        }

        DoubleLinkedList() {
            dummyHead = new Node();
            dummyTail = new Node();
            dummyHead.next = dummyTail;
            dummyTail.prev = dummyHead;
        }

        public void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        public void addFirst(Node node) {
            Node t = dummyHead.next;
            node.next = t;
            dummyHead.next = node;
            node.prev = dummyHead;
            t.prev = node;
        }

        public Node getLastNode() {
            return this.dummyTail.prev;
        }
    }
}
/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */