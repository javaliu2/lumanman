package newcoder.multi_thread;

import java.util.concurrent.locks.*;
import java.util.concurrent.CountDownLatch;

/**
 * 可重入（reentrant）：如果一个线程已经获得了某个锁，他可以再次获得这个锁，而不会被阻塞
 * output：
 * ReentrantLock took 39.72 ms
 * StampedLock (Optimistic Read) took 31.07 ms
 * StampedLock乐观读锁，适合读多写少的并发场景
 */
public class LockPerformanceTest {

    static final int THREADS = 20;
    static final int READS_PER_THREAD = 100_000;

    static class ReentrantLockTest {
        private final ReentrantLock lock = new ReentrantLock();
        private int data = 42;

        public int read() {
            lock.lock();
            try {
                return data;
            } finally {
                lock.unlock();
            }
        }
    }

    static class StampedLockTest {
        private final StampedLock lock = new StampedLock();
        private int data = 42;

        public int read() {
            long stamp = lock.tryOptimisticRead();  // 1. 尝试“乐观读锁”，stamp类似版本号
            int result = data;                      // 2. 不加锁地读取数据
            if (!lock.validate(stamp)) {            // 3. 验证读取期间有没有被写线程修改
                stamp = lock.readLock();            // 4. 如果失败，退回到“悲观读锁”
                try {
                    result = data;                  // 5. 重新读取数据
                } finally {
                    lock.unlockRead(stamp);         // 6. 释放读锁
                }
            }
            return result;
        }
    }

    static void runTest(String name, Runnable task) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(THREADS);
        long start = System.nanoTime();
        for (int i = 0; i < THREADS; i++) {
            new Thread(() -> {
                task.run();
                latch.countDown();
            }).start();
        }

        latch.await();
        long end = System.nanoTime();
        // 1毫秒=1000微秒，1微秒=1000纳秒
        System.out.printf("%s took %.2f ms%n", name, (end - start) / 1_000_000.0);
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest reentrant = new ReentrantLockTest();
        StampedLockTest stamped = new StampedLockTest();

        runTest("ReentrantLock", () -> {
            for (int i = 0; i < READS_PER_THREAD; i++) {
                reentrant.read();
            }
        });
        // 后面的lambda表达式就是run()方法的实现
        runTest("StampedLock (Optimistic Read)", () -> {
            for (int i = 0; i < READS_PER_THREAD; i++) {
                stamped.read();
            }
        });
    }
}

