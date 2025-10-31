package heimaJUC;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印，1-100
 */
public class Print100 {
    private static final Object lock = new Object();
    private static int num = 1;
    private static final int MAX = 100;
    private static boolean t1Turn = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!t1Turn && num <= MAX) {  // 不是 t1 的回合则等待
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (num > MAX) {
                        lock.notifyAll();
                        break;
                    }
                    System.out.println("t1打印: " + num++);
                    t1Turn = false;
                    lock.notifyAll();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (t1Turn && num <= MAX) {  // 不是 t2 的回合则等待
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (num > MAX) {
                        lock.notifyAll();
                        break;
                    }
                    System.out.println("t2打印: " + num++);
                    t1Turn = true;
                    lock.notifyAll();
                }
            }
        });

        t1.start();
        t2.start();
    }

    @Test
    public void reentrantLockImpl() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        final boolean[] t1Turn = {true};
        final int[] num = {1};
        final int MAX = 100;
        Condition condition = lock.newCondition();
        Thread t1 = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (!t1Turn[0] && num[0] <= MAX) {  // 不是 t1 的回合则等待
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (num[0] > MAX) {
                        condition.signal();
                        break;
                    }
                    System.out.println("t1打印: " + num[0]++);
                    t1Turn[0] = false;
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (t1Turn[0] && num[0] <= MAX) {  // 不是 t2 的回合则等待
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (num[0] > MAX) {
                        condition.signal();
                        break;
                    }
                    System.out.println("t2打印: " + num[0]++);
                    t1Turn[0] = true;
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
