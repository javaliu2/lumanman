package javaguide.multi_thread;

/**
 * JVM实现的多线程是 核心级线程（kernel level thread）以实现真正多核并发执行
 */
public class JVMThreadModel {
        public static void main(String[] args) {
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
                    try {
                        Thread.sleep(10000); // 每个线程阻塞10秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, "java-" + i).start();
            }
        }
}
