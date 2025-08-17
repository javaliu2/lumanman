package newcoder.multi_thread;

/**
 * 使用两个线程交替打印1-100
 * 涉及到两个线程的同步，
 */
public class Print100 {
    static int i;
    public static void main(String[] args) {
        i = 1;
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized(lock) {
                System.out.println(i);
                i++;
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized(lock) {
                System.out.println(i);
                i++;
            }
        }, "t2");


    }
}
