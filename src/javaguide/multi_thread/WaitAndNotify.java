package javaguide.multi_thread;

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

/**
 * wait & notify 的正确使用
 * 1、必须位于synchronized块中
 * 2、必须在同一个锁对象上等待或通知
 * 3、使用while进行条件判断和notifyAll方法避免虚假唤醒
 */
class ProperUsage {
    static final Object lock = new Object();
    static boolean hasPaper;
    static boolean hasTakeout;
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("是否有paper: " + hasPaper);
            synchronized (lock) {
                while (!hasPaper) {  // 使用 while 判断是否是自己的条件被满足，避免虚假唤醒
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("小明擦屁股");
            }
        }, "小明").start();

        new Thread(() -> {
            System.out.println("是否有takeout: " + hasTakeout);
            synchronized (lock) {
                while (!hasTakeout) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("小红吃外卖");
            }
        }, "小红").start();

        Thread.sleep(200);
        synchronized (lock) {
            System.out.println("老王送来了takeout");
            hasTakeout = true;
            lock.notifyAll();  // 使用 notifyAll 避免叫错了人
        }
    }
}

/**
 * 【设计模式-保护性暂停】
 * 守护对象，用来在两个线程之间传递数据
 * t1线程需要等待t2线程执行返回的结果
 */
class GuardedObject {
    private Object result;
    public Object get(long timeout) {
        synchronized (this) {
            long begin = System.currentTimeMillis();
            long passedTime = 0;
            while (result == null) {
                long waitTime = timeout - passedTime;
                if (waitTime <= 0) {
                    break;
                }
                try {
                    wait(waitTime);  // 不能直接使用timeout，因为如果虚假唤醒的话，继续等待timeout个时间，那么总的等待时间就超过timeout了
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return result;
        }
    }
    public void complete(Object result) {
        synchronized (this) {
            this.result = result;
            notifyAll();
        }
    }
}
class GuardedObjectTest {
    public static void main(String[] args) {
        GuardedObject object = new GuardedObject();
        new Thread(() -> {
            Object result = object.get(2000);
            System.out.println(result);
        }, "t1").start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            object.complete(null);
        }, "t2").start();
    }
}

