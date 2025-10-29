package heimaJUC;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * 一、线程状态转换
 * 1、Java中的线程状态分别有
 * 1) NEW
 * 2) RUNNABLE
 * 3) WAITING
 * 4) TIMED_WAITING
 * 5) BLOCKED
 * 6) TERMINATED
 * 2、不同状态转换涉及到的方法
 * 1) RUNNABLE <====> WAITING: wait/notify,notifyAll; join; park/unpark
 */
public class ThreadStateTransition {
    private static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("t线程被中断");
                }
            }
            System.out.println("thread id:" + Thread.currentThread().getId() + " is running");
        }, "t1");  // t state: NEW
        t.start();  // t state: RUNNABLE
        // 两种方式将处于waiting状态的线程唤醒
//        t.interrupt();  // 1）主线程主动中断t线程
//        lock.notifyAll();  // bug记录：不在synchronized块中调用notifyAll报错：Exception in thread "main" java.lang.IllegalMonitorStateException: current thread is not owner
        Thread.sleep(300);  // 确保主线程的唤醒信号在线程t进入wait状态之后，否则会造成信号丢失，t不会被唤醒的问题
        synchronized(lock) {
            lock.notifyAll();  // 2）notify
        }
        t.join();  // 当前线程即主线程进入waiting状态等待t线程运行结束
    }

    @Test
    public void testPark() {
        Thread t = new Thread(() -> {
            System.out.println("enter park");
            LockSupport.park();
        }, "t");
        LockSupport.unpark(t);
        t.start();
    }
}
