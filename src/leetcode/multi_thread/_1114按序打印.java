package leetcode.multi_thread;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class _1114按序打印 {
    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();
        Runnable t1 = ()-> {
            try {
                foo.first(() -> {
                    System.out.println("first");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable t2 = ()-> {
            try {
                foo.second(() -> {
                    System.out.println("second");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable t3 = ()-> {
            try {
                foo.third(() -> {
                    System.out.println("third");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        // 三个不同的线程公共一个Foo实例，线程A调用first方法，线程B调用second方法，线程C调用third方法
        // 线程的调用顺序不唯一，设计程序，使得所有调用顺序中，方法执行顺序总是first()、second()、third()
        Runnable[] runnables = {t1, t2, t3};
        List<Runnable> list = Arrays.asList(runnables);
        Collections.shuffle(list); // 打乱顺序

        for (Runnable r : list) {
            new Thread(r).start();
        }
    }
}
class Foo {
    private boolean isFirstPrinted = false;
    private boolean isSecondPrinted = false;
    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (this) {
            printFirst.run();
            isFirstPrinted = true;
            notifyAll();  // 唤醒处于waiting set中的所有线程，让他们进入entry set去竞争锁
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (this) {
            while (!isFirstPrinted) {
                wait();
            }
            printSecond.run();
            isSecondPrinted = true;
            notifyAll();  // 唤醒t3
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (this) {
            while (!isSecondPrinted) {
                wait();
            }
            printThird.run();
        }
    }
}