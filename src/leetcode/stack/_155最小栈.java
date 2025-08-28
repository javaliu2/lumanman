package leetcode.stack;

public class _155最小栈 {
    class MinStack {

        private Node head;
        private Node min_head;

        public MinStack() {
            head = new Node();
            min_head = new Node();
        }

        public void push(int val) {
            Node node = new Node(val, head.next);
            head.next = node;
            // 通过另一个栈维护最小值
            int min = val;
            if (min_head.next != null && min_head.next.val < min) {
                min = min_head.next.val;
            }
            Node min_node = new Node(min, min_head.next);
            min_head.next = min_node;
        }

        public void pop() {
            head.next = head.next.next;
            min_head.next = min_head.next.next;
        }

        public int top() {
            return head.next.val;
        }

        public int getMin() {
            return min_head.next.val;
        }
    }
    class Node {
        int val;
        Node next;
        Node() {
            val = 0;
            next = null;
        }
        Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
}
