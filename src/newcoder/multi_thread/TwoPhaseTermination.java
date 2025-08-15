package newcoder.multi_thread;

/**
 * 使用interrupt优化终止线程
 */
public class TwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        monitor.start();
        Thread.sleep(3000);
        monitor.stop();
    }
}
class Monitor {
    private Thread monitor;
    public void start() {
        monitor = new Thread(()->{
            while (true) {
                Thread thread = Thread.currentThread();
                if (thread.isInterrupted()) {
                    System.out.println("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("执行监控");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 由于sleep()方法会将中断标记清除，故这里需要重新设置，以确保下次循环的时候可以正确退出循环，结束线程
                    thread.interrupt();
                }
            }
        });
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }
}
