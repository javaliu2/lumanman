package javaguide.base.multi_thread;

/**
 * output:
 * Thread[线程 2,5,main]get resource2
 * Thread[线程 1,5,main]get resource1
 * Thread[线程 2,5,main]waiting get resource1
 * Thread[线程 1,5,main]waiting get resource2
 * 程序不结束，陷入死循环
 */
public class DeadLockTest {
    private static Object resource1 = new Object();//资源 1
    private static Object resource2 = new Object();//资源 2

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (resource1) {
                // Thread[线程名字,线程优先级(默认为5),线程组名]
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "线程 1").start();

//        new Thread(() -> {
//            synchronized (resource2) {
//                System.out.println(Thread.currentThread() + "get resource2");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread() + "waiting get resource1");
//                synchronized (resource1) {
//                    System.out.println(Thread.currentThread() + "get resource1");
//                }
//            }
//        }, "线程 2").start();

        // 这样就不会死锁了，通过统一加锁顺序
        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread() + "get resource2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource1");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        }, "线程 2").start();
    }
}
