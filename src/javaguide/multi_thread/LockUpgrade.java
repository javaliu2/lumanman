package javaguide.multi_thread;

import org.openjdk.jol.info.ClassLayout;

public class LockUpgrade {

    static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        // 延迟等待 JVM 启动完毕，偏向锁默认延迟 4 秒启用
        System.out.println("等待偏向锁激活...");
        Thread.sleep(5000);

        System.out.println("---- 初始状态（无锁） ----");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("---- T1 获取偏向锁 ----");
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        });

        t1.start();
        t1.join();

        Thread.sleep(1000);

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("---- T2 获取锁，偏向锁撤销，升级为轻量级锁或重量级锁 ----");
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
                try {
                    Thread.sleep(3000); // 保持持有锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                Thread.sleep(100); // 确保 T2 先持有锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                System.out.println("---- T3 竞争锁，可能升级为重量级锁 ----");
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        });

        t2.start();
        t3.start();

        t2.join();
        t3.join();
    }
}
