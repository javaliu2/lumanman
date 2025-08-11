package javaguide.multi_thread;

import org.junit.Test;

/**
 * 线程生命周期，有什么状态，以及各个状态之间的转换
 */
public class ThreadStatusTest {

    /**
     * 线程声明周期说明
     * @throws InterruptedException
     */
    @Test
    public void ThreadLifecycleDemo() throws InterruptedException {
        Object lock = new Object();

        Thread t = new Thread(() -> {
            try {
                // TIMED_WAITING
                Thread.sleep(500);
                synchronized (lock) {
                    // WAITING
                    lock.wait();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("1. 状态：" + t.getState()); // NEW

        t.start();
        System.out.println("2. 状态：" + t.getState()); // RUNNABLE

        Thread.sleep(100); // 让线程先进入sleep
        System.out.println("3. 状态：" + t.getState()); // TIMED_WAITING

        Thread.sleep(600); // 等待sleep结束，进入WAITING
        System.out.println("4. 状态：" + t.getState()); // WAITING

        synchronized (lock) {
            lock.notify(); // 唤醒
        }

        Thread.sleep(100); // 等待线程结束
        System.out.println("5. 状态：" + t.getState()); // TERMINATED
    }

    /**
     * java中线程的blocked状态指的是，线程正在等待进入一个synchronized保护的临界区，因为他没能获取到对象的监视器锁
     * 而与IO操作阻塞无关
     *
     * @throws InterruptedException
     */
    @Test
    public void testBlocked() throws InterruptedException {
        Object lock = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(3000); // 持有锁3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread 2 acquired the lock");
            }
        });

        t1.start();
        Thread.sleep(100); // 确保 t1 先拿到锁
        t2.start();

        Thread.sleep(100); // 给 t2 一点时间进入等待
        System.out.println("t2 状态: " + t2.getState()); // BLOCKED
        Thread.sleep(5000);
    }
}
