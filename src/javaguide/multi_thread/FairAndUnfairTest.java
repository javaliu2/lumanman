package javaguide.multi_thread;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized是非公平锁，
 * ReentrantLock既可以是公平锁，也可以是非公平锁
 */
public class FairAndUnfairTest {
    private final Object lock = new Object();

    @Test
    public void synchronized_unfair() throws InterruptedException {
        System.out.println("gg");
        // 创建多个线程来竞争同一把锁
        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    synchronized (lock) {
                        System.out.println(Thread.currentThread().getName() + " 获得锁");
                        try {
                            Thread.sleep(100); // 模拟占用锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(10); // 模拟一段间隔后再次尝试获取锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "线程-" + i);
            thread.start();
        }
        Thread.sleep(2000);
    }

    private static final ReentrantLock fairLock = new ReentrantLock(true); // 公平锁

    @Test
    public void ReentrantLock_fair() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            final int threadId = i;
            new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    fairLock.lock();
                    try {
                        System.out.println("【公平】线程-" + threadId + " 获得锁");
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        fairLock.unlock();
                    }
                }
            }).start();
        }
        Thread.sleep(1000);
    }
}
