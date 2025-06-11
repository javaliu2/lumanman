package newcoder.base;

import java.io.Serializable;
import java.util.*;

/**
 * 抛出异常ConcurrentModificationException
 *
 */
public class HashMapTest {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");
        map.forEach((key, value) -> {
            if (key == 2) {
                map.remove(key);
            }
        });
        System.out.println(map.size());
    }
    void testLInkedLIst() {
        LinkedList<String> l = new LinkedList<>();
//    public class LinkedList<E> extends AbstractSequentialList<E>
//    implements List<E>, Deque<E>, Cloneable, Serializable
        // 实现了List、Deque、Cloneable、Serializable接口
        // 1、作为栈使用
        l.push("hello");
        l.pop();
        // 2、作为队列使用
        l.offer("world");
        l.poll();
    }
}
