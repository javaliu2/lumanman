package javaguide.base.multi_thread;

/**
 * wait(): 当前线程释放锁并且挂起，等待被notify()或notifyAll()唤醒。必须在synchronized中使用。
 * notify(): 随机唤醒一个正在wait()该对象的线程，唤醒后它会竞争锁。
 * notifyAll(): 唤醒所有wait()该对象的线程（但只有一个能成功拿到锁）。
 * 这三个方法是Object类中的方法，作用于某个共享对象，用于线程之间在该对象上的等待-通知机制（wait-notify mechanism）。
 * 线程在执行这些方法时，必须持有该对象的锁，它们实现的是基于对象的同步通信机制，不是通用的线程间通信。
 */
public class WaitAndNotify {
    public static void main(String[] args) {
        Basket basket = new Basket();

        // 生产者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    basket.put();
                    Thread.sleep(500); // 模拟生产时间
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 消费者线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    basket.take();
                    Thread.sleep(800); // 模拟消费时间
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

class Basket {
    private boolean hasApple = false;

    // 放苹果
    public synchronized void put() throws InterruptedException {
        while (hasApple) {
            wait(); // 如果有苹果了，等待消费者拿走
        }
        System.out.println("🍎 生产者放了一个苹果");
        hasApple = true;
        notify(); // 通知消费者可以拿了
    }

    // 拿苹果
    public synchronized void take() throws InterruptedException {
        while (!hasApple) {
            wait(); // 如果没苹果，等待生产者放
        }
        System.out.println("👶 消费者拿走了一个苹果");
        hasApple = false;
        notify(); // 通知生产者可以继续放
    }
}

