package javaguide.base.multi_thread;
import java.util.concurrent.locks.ReentrantLock;

/**
 * output:
 * [无锁] 最终计数值：95773（理论值：100000）
 * [synchronized] 最终计数值：100000（理论值：100000）
 * [ReentrantLock] 最终计数值：100000（理论值：100000）
 * reason:
 * count++; 非原子性操作，分为三步
 * 1）将count值读入寄存器
 * 2）寄存器值++
 * 3）将寄存器值写回count
 * 所以在多线程环境下，在1）OR 2）OR 3）均有可能发生线程的切换
 * 比如：当前count=1, t1线程读取到它的寄存器中记录为1，这时发生线程切换，t2读取到的count也是1
 * 然后t1寄存器对值+1，写回count，count=2. 发生线程调度，切换到t2，t2寄存器值+1，写回count，t1对count的修改被覆写，count还是为2
 * 出现了数据丢失的情况
 */
public class CounterTest {

    public static final int THREADS = 100;
    public static final int INCREMENTS_PER_THREAD = 1000;

    public static void main(String[] args) throws InterruptedException {
        testCounter(new NoLockCounter(), "无锁");
        testCounter(new SynchronizedCounter(), "synchronized");
        testCounter(new LockCounter(), "ReentrantLock");
    }

    static void testCounter(Counter counter, String name) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    counter.increment();
                }
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();  // 等待所有线程执行完毕，再继续执行本线程

        System.out.printf("[%s] 最终计数值：%d（理论值：%d）\n",
                name, counter.getCount(), THREADS * INCREMENTS_PER_THREAD);
    }

    interface Counter {
        void increment();
        int getCount();
    }

    // ❌ 无锁版本（会出错）
    static class NoLockCounter implements Counter {
        private int count = 0;
        public void increment() {
            count++;
        }
        public int getCount() {
            return count;
        }
    }

    // ✅ synchronized 版本
    static class SynchronizedCounter implements Counter {
        private int count = 0;
        public synchronized void increment() {
            count++;
        }
        public synchronized int getCount() {
            return count;
        }
    }

    // ✅ ReentrantLock 版本
    static class LockCounter implements Counter {
        private int count = 0;
        private final ReentrantLock lock = new ReentrantLock();

        public void increment() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }

        public int getCount() {
            lock.lock();
            try {
                return count;
            } finally {
                lock.unlock();
            }
        }
    }
}

