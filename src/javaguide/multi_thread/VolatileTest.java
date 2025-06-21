package javaguide.multi_thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile关键字作用：
 * 1）禁止JVM指令重排序
 * 2）保证变量的可见性（即每次使用该变量时需要到内存中进行读取）
 * 3）不能保证对变量的操作是原子性的
 */
public class VolatileTest {
    public volatile static int inc = 0;
    public void increase() {
        inc++;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        VolatileTest volatileTest = new VolatileTest();
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 500; j++) {
                    volatileTest.increase();
                }
            });
        }
        /**
         * 运行4次，两次5000，一次4098，一次4573
         * 因为inc++不是原始操作：
         * 其分为三个步骤：
         * 1）去读inc的值
         * 2）inc值加1，记为ans
         * 3）将ans写回inc
         * 所以在线程1执行1）之后，线程2也执行1），两者后序分别执行2）、3），但是最终inc值只增加了1
         */
        Thread.sleep(1500);  // 不会释放当前线程持有的锁
        System.out.println(inc);
        executorService.shutdown();
    }

    private volatile int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger(num);
    void atomicIntegerIncrease() {
        atomicInteger.getAndIncrement();
    }

    @Test
    public void testAtomicIntegerIncrease() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        VolatileTest test = new VolatileTest();
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 500; j++) {
                    test.atomicIntegerIncrease();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);  // 等待线程执行完
        System.out.println(test.atomicInteger.get());  // 输出应该是 2500
        System.out.println(test.num);  // 输出应该是 2500
    }
}
