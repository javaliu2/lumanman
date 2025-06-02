package javaguide.base.multi_thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

/**
 * Code from G-bro.
 * output:
 * Thread[线程 1,5,main] get lock1
 * Thread[线程 2,5,main] get lock2
 * Thread[线程 1,5,main] 获取 lock2 失败，释放 lock1 重试
 * Thread[线程 2,5,main] 获取 lock1 失败，释放 lock2 重试
 * Thread[线程 1,5,main] get lock1
 * Thread[线程 2,5,main] get lock2
 * Thread[线程 1,5,main] 获取 lock2 失败，释放 lock1 重试
 * Thread[线程 2,5,main] 获取 lock1 失败，释放 lock2 重试
 * Thread[线程 1,5,main] get lock1
 * Thread[线程 2,5,main] get lock2
 * Thread[线程 1,5,main] 获取 lock2 失败，释放 lock1 重试
 * Thread[线程 2,5,main] 获取 lock1 失败，释放 lock2 重试
 * Thread[线程 1,5,main] get lock1
 * Thread[线程 2,5,main] get lock2
 * Thread[线程 1,5,main] 获取 lock2 失败，释放 lock1 重试
 * Thread[线程 2,5,main] get lock1
 * Thread[线程 2,5,main] 成功获取两个锁，执行操作
 * Thread[线程 1,5,main] get lock1
 * Thread[线程 1,5,main] get lock2
 * Thread[线程 1,5,main] 成功获取两个锁，执行操作
 */
public class DeadLockAvoidDemo {
    private static final Lock lock1 = new ReentrantLock(); // 对应 resource1
    private static final Lock lock2 = new ReentrantLock(); // 对应 resource2

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    if (lock1.tryLock(500, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread() + " get lock1");
                        try {
                            Thread.sleep(300); // 模拟持有锁后做的事

                            if (lock2.tryLock(500, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread() + " get lock2");
                                    System.out.println(Thread.currentThread() + " 成功获取两个锁，执行操作");
                                    break; // 成功获取两个锁，退出循环
                                } finally {
                                    lock2.unlock();
                                }
                            } else {
                                System.out.println(Thread.currentThread() + " 获取 lock2 失败，释放 lock1 重试");
                            }
                        } finally {
                            lock1.unlock();
                        }
                    }
                    Thread.sleep(100); // 避免忙等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程 1").start();

        new Thread(() -> {
            while (true) {
                try {
                    if (lock2.tryLock(500, TimeUnit.MILLISECONDS)) {
                        System.out.println(Thread.currentThread() + " get lock2");
                        try {
                            Thread.sleep(300); // 模拟持有锁后做的事

                            if (lock1.tryLock(500, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread() + " get lock1");
                                    System.out.println(Thread.currentThread() + " 成功获取两个锁，执行操作");
                                    break;
                                } finally {
                                    lock1.unlock();
                                }
                            } else {
                                System.out.println(Thread.currentThread() + " 获取 lock1 失败，释放 lock2 重试");
                            }
                        } finally {
                            lock2.unlock();
                        }
                    }
                    Thread.sleep(100); // 避免忙等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程 2").start();
    }
}

