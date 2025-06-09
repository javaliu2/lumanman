package newcoder.multi_thread;

/**
 * synchronized保证线程之间对共享资源的互斥访问，但并不禁止CPU进行线程切换或中断调度。
 * output：
 * 1）
 * Hello
 * World
 * 程序正常退出
 * 2）
 * World
 * Hello
 * 程序挂住，不结束
 * 原因：先进入t2 syn代码块，执行notify操作，打印World，然后t2释放锁
 * t1获得锁，进入syn代码块，打印Hello，进行wait操作，一直不被唤醒，因为notify信号丢失，导致程序挂住
 */
public class Main {

    public static void main(String[] args) {
        // 通过lambda表达式实现Runnable接口的方式创建线程
        Thread t1 = new Thread(()->{
            synchronized (Main.class) {
                System.out.println("Hello");
                try {
                    Main.class.wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        Thread t2 = new Thread(()->{
            synchronized (Main.class) {
                Main.class.notify();
                System.out.println("World");
            }
        });
        t1.start();
        t2.start();
    }
}
