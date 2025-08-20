package javaguide.multi_thread;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.openjdk.jol.info.ClassLayout;
/**
 * 对象头markword格式：
 * 64bits
 * State   |   mark word (64 bits)  # 最后两位是锁标志位
 * Normal  |  unused:25 | hashCode:31 | unused:1 | age:4 | biased_lock:0 | 01 (无锁)
 * Biased  |  threadId:54 | epoch:2   | unused:1 | age:4 | biased_lock:1 | 01 (偏向锁)
 * lightweight locked | ptr_to_lock_record:62 | 00 (轻量级锁)
 * heavyweight locked | ptr_to_heavyweight_monitor:62 | 10 (重量级锁)
 * unused:62 | 11 (marked for GC)
 */

/**
 * synchronized锁升级
 * 1、偏向锁（默认）
 * 锁对象 markword 存放的是线程id
 * 2、轻量级锁（支持可重入）
 * 锁对象 markword 存放的是线程中锁记录的地址
 * 3、重量级锁
 * 使用操作系统提供的monitor作为锁机制实现
 */
public class SynchronizedLock {
    /**
     * 对象头
     * output：
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
     *   8   4        (object header: class)    0x01001800
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     */
    @Test
    public void testObjectLayout() {
        SynchronizedLock obj = new SynchronizedLock();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }

    /**
     * 偏向锁默认延迟开启，通过使用JVM参数控制其在程序启动时就开启
     * java15+之后，只支持轻量级锁和重量级锁
     * -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
     * 使用jdk1.8，output:
     * javaguide.multi_thread.SynchronizedLock object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
     *   8   4        (object header: class)    0xf800c925
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * javaguide.multi_thread.SynchronizedLock object internals: # 这里的线程id和thread.getId()不是同一个
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x0000717ba400c005 (biased: 0x0000001c5ee90030 (threadId); epoch: 0; age: 0)
     *   8   4        (object header: class)    0xf800c925
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * javaguide.multi_thread.SynchronizedLock object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x0000717ba400c005 (biased: 0x0000001c5ee90030; epoch: 0; age: 0)
     *   8   4        (object header: class)    0xf800c925
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     */
    @Test
    public void testBiasedLock() {
        SynchronizedLock obj = new SynchronizedLock();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj) {
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }

    /**
     * output:
     * javaguide.multi_thread.SynchronizedLock object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
     *   8   4        (object header: class)    0xf800c925
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * javaguide.multi_thread.SynchronizedLock object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x00007d2a50d16820 (thin lock: 0x00007d2a50d16820)
     *   8   4        (object header: class)    0xf800c925
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * javaguide.multi_thread.SynchronizedLock object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
     *   8   4        (object header: class)    0xf800c925
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     */
    @Test
    public void testLightWeightLock() {
        SynchronizedLock obj = new SynchronizedLock();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj) {
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }

    /**
     * 偏向锁和轻量级锁指的是不同线程在不同时间访问共享对象，如果是同一时刻访问的话，那么就是重量级锁了
     * JDK1.8 + jvm参数: -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
     * output:
     * thread: t1
     * # WARNING: Unable to attach Serviceability Agent. You can try again with escalated privileges. Two options: a) use -Djol.tryWithSudo=true to try with sudo; b) echo 0 | sudo tee /proc/sys/kernel/yama/ptrace_scope
     * javaguide.multi_thread.Helper object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
     *   8   4        (object header: class)    0xf8011131
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * javaguide.multi_thread.Helper object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x00007a593c422005 (biased: 0x0000001e964f1088（线程1 id）; epoch: 0; age: 0)
     *   8   4        (object header: class)    0xf8011131
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * javaguide.multi_thread.Helper object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x00007a593c422005 (biased: 0x0000001e964f1088（线程1 id）; epoch: 0; age: 0)
     *   8   4        (object header: class)    0xf8011131
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * thread: t2
     * javaguide.multi_thread.Helper object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x00007a593c422005 (biased: 0x0000001e964f1088（线程1 id）; epoch: 0; age: 0)
     *   8   4        (object header: class)    0xf8011131
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * javaguide.multi_thread.Helper object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x00007a58fadf98f8 (thin lock: 0x00007a58fadf98f8（线程2栈内存存放锁记录的地址）)
     *   8   4        (object header: class)    0xf8011131
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     * javaguide.multi_thread.Helper object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
     *   8   4        (object header: class)    0xf8011131
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     */
    @Test
    public void testBiased2LightWeight() {
        Helper obj = new Helper();
        Thread t1 = new Thread(() -> {
            System.out.println("thread: " + Thread.currentThread().getName());
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj) {
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            synchronized (SynchronizedLock.class) {
                SynchronizedLock.class.notify();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (SynchronizedLock.class) {
                try {
                    SynchronizedLock.class.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("thread: " + Thread.currentThread().getName());
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj) {
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        }, "t2");

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Helper {

}
