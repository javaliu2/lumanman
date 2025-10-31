package leetcode.multi_thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class _1116打印零与奇偶数 {
    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(8);

        IntConsumer printNumber = System.out::print;

        Thread t1 = new Thread(() -> {
            try {
                zeroEvenOdd.zero(printNumber);
            } catch (InterruptedException e) { e.printStackTrace(); }
        });

        Thread t2 = new Thread(() -> {
            try {
                zeroEvenOdd.even(printNumber);
            } catch (InterruptedException e) { e.printStackTrace(); }
        });

        Thread t3 = new Thread(() -> {
            try {
                zeroEvenOdd.odd(printNumber);
            } catch (InterruptedException e) { e.printStackTrace(); }
        });

        t2.start();
        t3.start();
        t1.start();
    }
}
class ZeroEvenOdd {
    private int n;
    private ReentrantLock lock;
    private Condition zero_cond;
    private Condition odd_cond;
    private Condition even_cond;
    private int state;
    public ZeroEvenOdd(int n) {
        this.n = n;
        this.lock = new ReentrantLock();
        this.zero_cond = lock.newCondition();
        this.odd_cond = lock.newCondition();
        this.even_cond = lock.newCondition();
        this.state = 0;  // 0: zero_turn; 1: odd_turn; 2: even_turn
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; ++i) {
            lock.lock();
            try {
                while (state != 0) {
                    zero_cond.await();  // 条件不满足，就等待
                }
                printNumber.accept(0);
                if (i % 2 == 1) {
                    state = 1;
                    odd_cond.signal();
                } else {
                    state = 2;
                    even_cond.signal();
                }
            } finally{
                lock.unlock();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            lock.lock();
            try {
                while (state != 2) {
                    even_cond.await();
                }
                printNumber.accept(i);
                state = 0;
                zero_cond.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            lock.lock();
            try {
                while (state != 1) {
                    odd_cond.await();
                }
                printNumber.accept(i);
                state = 0;
                zero_cond.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
