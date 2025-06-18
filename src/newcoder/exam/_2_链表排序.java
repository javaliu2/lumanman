package newcoder.exam;

import java.util.Scanner;
public class _2_链表排序 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Node head = new Node();
        Node p = head;
        while (in.hasNextInt()) {
            int num = in.nextInt();
            p.next = new Node(num);
            p = p.next;
        }
        p = head;
        // while (p != null) {
        //     System.out.print(p.value + " ");
        //     p = p.next;
        // }
        p = head.sortList(head);
        while (p != null) {
            if (p.next != null) {
                System.out.print(p.value + " ");
            } else {
                System.out.print(p.value);
            }
            p = p.next;
        }
    }
}
class Node {
    int value;
    Node next;
    Node(){
        value = -1;
        next = null;
    }
    Node(int value){
        this.value = value;
    }
    Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
    Node sortList(Node head){
        Node p = head.next, prev_p = head, q, prev_q, qq;
        while (p.next != null) {
            qq = p.next;  // 下一个节点
            if (qq.value < p.value) {
                // 每次都从头开始找
                prev_q = head;
                q = head.next;
                while (q != null && q.value <= qq.value) {
                    prev_q = q;
                    q = q.next;
                }
                if (q != null) {
                    p.next = qq.next;
                    prev_q.next = qq;
                    qq.next = q;
                }
            } else {
                prev_p = p;
                p = p.next;
            }
        }
        return head.next;
    }
}