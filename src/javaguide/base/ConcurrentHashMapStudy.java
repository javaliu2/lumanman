package javaguide.base;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class ConcurrentHashMapStudy {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    }
    @Test
    public void testLongAdder() {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<String, LongAdder>();
        freqs.computeIfAbsent("xs", k -> new LongAdder()).increment();
        LongAdder xs = freqs.get("xs");
        System.out.println(xs);
    }


    class InitTableSimulator {

        private static final int DEFAULT_CAPACITY = 16;
        private volatile Node[] table = null;
        private final AtomicInteger sizeCtl = new AtomicInteger(0); // 初始值为0

        // 模拟Node数组
        static class Node {
            int val;
            Node(int val) {
                this.val = val;
            }
        }

        // 模拟initTable方法
        private Node[] initTable() {
            Node[] tab;
            int sc;
            while ((tab = table) == null || tab.length == 0) {
                sc = sizeCtl.get();
                if (sc < 0) {
                    System.out.println(Thread.currentThread().getName() + ": waiting...");
                    Thread.yield(); // 让出CPU使用权
                } else if (sizeCtl.compareAndSet(sc, -1)) {
                    try {
                        if ((tab = table) == null || tab.length == 0) {
                            System.out.println(Thread.currentThread().getName() + ": initializing table...");
                            Node[] nt = new Node[DEFAULT_CAPACITY];
                            for (int i = 0; i < nt.length; i++) {
                                nt[i] = new Node(i);
                            }
                            table = tab = nt;
                            sc = DEFAULT_CAPACITY - (DEFAULT_CAPACITY >>> 2); // 0.75负载因子
                        }
                    } finally {
                        sizeCtl.set(sc);
                    }
                    break;
                }
            }
            return tab;
        }

        public void runInit() {
            Node[] result = initTable();
            System.out.println(Thread.currentThread().getName() + ": table size = " + result.length);
        }
    }
    @Test
    public void initTableSimulator() {
        InitTableSimulator simulator = new InitTableSimulator();

        Runnable task = simulator::runInit;  // 方法引用语法，表示创建一个Runnable，它的run()方法会调用该方法

        // 创建多个线程并发执行initTable
        for (int i = 0; i < 5; i++) {
            new Thread(task, "Thread-" + i).start();
        }
    }
}
