package javaguide.multi_thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * RL: ReentrantLock 实现生产者-消费者
 */
public class RLProducerConsumer {

    private final Queue<Integer> buffer = new LinkedList<>();
    private final int MAX_CAPACITY = 5;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();   // 缓冲区不满的条件
    private final Condition notEmpty = lock.newCondition();  // 缓冲区不空的条件

    // 生产者方法
    public void produce(int value) throws InterruptedException {
        lock.lock();
        try {
            // 为什么使用while判断，是为了避免虚假唤醒
            // 虚假唤醒：线程在调用condition.await()之后，即使没有收到signal()，也可能被唤醒，这是JVM允许的行为。
            while (buffer.size() == MAX_CAPACITY) {
                System.out.println("Buffer full, producer waiting...");
                // await():
                // 1) 自动释放当前持有的ReentrantLock
                // 2) 把当前线程放入notFull的等待队列
                // 3) 线程阻塞，暂停执行
                notFull.await();  // 等待缓冲区有空位
            }
            buffer.offer(value);
            System.out.println("Produced: " + value);
            notEmpty.signal();  // 唤醒消费者
        } finally {
            lock.unlock();
        }
    }

    // 消费者方法
    public int consume() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.isEmpty()) {
                System.out.println("Buffer empty, consumer waiting...");
                notEmpty.await();  // 等待缓冲区有数据
            }
            int value = buffer.poll();
            System.out.println("Consumed: " + value);
            // signal():
            // 1) 会从等待队列中唤醒一个或多个线程
            // 2) 被唤醒的线程会尝试重新获得ReentrantLock
            // 3) 拿到锁之后，继续执行await()之后的代码
            notFull.signal();  // 唤醒生产者
            return value;
        } finally {
            lock.unlock();
        }
    }

    // 测试入口
    public static void main(String[] args) {
        RLProducerConsumer pc = new RLProducerConsumer();

        // 生产者线程
        Thread producer = new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    pc.produce(i++);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 消费者线程
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    pc.consume();
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}

