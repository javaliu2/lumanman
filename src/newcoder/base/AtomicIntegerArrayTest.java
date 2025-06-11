package newcoder.base;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * AtomicIntegerArray复制传入的对象数组，而不是引用原有对象
 * output:
 * 3
 * 1
 */
public class AtomicIntegerArrayTest {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        AtomicIntegerArray o = new AtomicIntegerArray(arr);
        o.getAndSet(0, 3);
        System.out.println(o.get(0));
        System.out.println(arr[0]);
    }
}
