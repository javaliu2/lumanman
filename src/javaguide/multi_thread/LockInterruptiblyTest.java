package javaguide.multi_thread;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * lock()和lockInterruptibly()均是阻塞方法，意思就是说执行该方法时，如果获取锁失败
 * 线程会让出CPU，进入阻塞状态，等待锁被其他线程释放。
 * 但是lock()不响应中断，而后者响应中断，可中断退出等待。
 * java中的interrupt()是设置线程状态为中断状态，而不是中断信号那样中断当前线程的运行，
 * 是否响应中断，取决于代码中是否检查中断状态，或调用会响应中断的方法
 * output:
 * lock() : lock count :1
 * Current thread is intrupted
 * tryLock() on intrupted thread lock count :2
 * Current Thread isInterrupted:true
 * Error
 * lockInterruptibly() not able to Acqurie lock: lock count :2
 * lock count :1
 * lock count :0
 */
public class LockInterruptiblyTest {
    Thread t = new Thread() {
        @Override
        public void run() {
            ReentrantLock r = new ReentrantLock();
            // 1.1、第一次尝试获取锁，可以获取成功
            r.lock();

            // 1.2、此时锁的重入次数为 1
            System.out.println("lock() : lock count :" + r.getHoldCount());

            // 2、中断当前线程，通过 Thread.currentThread().isInterrupted() 可以看到当前线程的中断状态为 true
            interrupt();
            System.out.println("Current thread is interrupted");

            // 3.1、尝试获取锁，可以成功获取
            r.tryLock();
            // 3.2、此时锁的重入次数为 2
            System.out.println("tryLock() on interrupted thread lock count :" + r.getHoldCount());
            try {
                // 4、打印线程的中断状态为 true，那么调用 lockInterruptibly() 方法就会抛出 InterruptedException 异常
                System.out.println("Current Thread isInterrupted:" + Thread.currentThread().isInterrupted());
                r.lockInterruptibly();
                System.out.println("lockInterruptibly() --NOt executable statement" + r.getHoldCount());
            } catch (InterruptedException e) {
                r.lock();
                System.out.println("Error");
            } finally {
                System.out.println("in finally, lock count :" + r.getHoldCount());
                r.unlock();
            }

            // 5、打印锁的重入次数，可以发现 lockInterruptibly() 方法并没有成功获取到锁
            System.out.println("lockInterruptibly() not able to Acquire lock: lock count :" + r.getHoldCount());

            r.unlock();
            System.out.println("lock count :" + r.getHoldCount());
            r.unlock();
            System.out.println("lock count :" + r.getHoldCount());
        }
    };

    public static void main(String[] str) {
        LockInterruptiblyTest m = new LockInterruptiblyTest();
        m.t.start();
    }

    /**
     * lockInterruptibly()会在调用前 检查中断状态，以及在调用后 阻塞等待期间监听中断。
     * 因此，无论中断是在调用之前还是之后发生，他都会响应并抛出InterruptedException.
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        // 线程1先获取锁，保持一段时间
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("[T1] 已获取锁，休眠中");
                try {
                    Thread.sleep(10000);  // 模拟长时间持锁
                } catch (InterruptedException e) {
                    System.out.println("[T1] 被中断");
                }
            } finally {
                lock.unlock();
                System.out.println("[T1] 释放锁");
            }
        });

        // 情况1：中断发生在调用 lockInterruptibly() 之前
        Thread t2_beforeInterrupt = new Thread(() -> {
            Thread.currentThread().interrupt();  // 提前设置中断状态
            try {
                System.out.println("[T2-before] 准备调用 lockInterruptibly()");
                lock.lockInterruptibly();  // 会立即抛出 InterruptedException
                System.out.println("[T2-before] 获取到锁（不会打印）");
            } catch (InterruptedException e) {
                System.out.println("[T2-before] 在调用前就被中断，抛出异常");
            }
        });

        // 情况2：中断发生在 lockInterruptibly() 等待过程中
        Thread t3_duringInterrupt = new Thread(() -> {
            try {
                System.out.println("[T3-during] 准备调用 lockInterruptibly()");
                lock.lockInterruptibly();  // 进入阻塞等待
                System.out.println("[T3-during] 获取到锁（不会打印）");
            } catch (InterruptedException e) {
                System.out.println("[T3-during] 在等待锁的过程中被中断，抛出异常");
            }
        });

        t1.start();  // 让线程1先占有锁
        Thread.sleep(100);  // 确保t1先拿到锁

        t2_beforeInterrupt.start();  // 中断在前，立刻抛出异常
        t2_beforeInterrupt.join();

        t3_duringInterrupt.start();  // 中断在后，等待中
        Thread.sleep(500);  // 等待t3进入阻塞
        t3_duringInterrupt.interrupt();  // 中断它
    }
}
