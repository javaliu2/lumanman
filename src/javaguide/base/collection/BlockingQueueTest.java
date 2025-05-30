package javaguide.base.collection;

import java.util.concurrent.*;

public class BlockingQueueTest {

    public static void main(String[] args) {
        // 1. ArrayBlockingQueue：容量为3，数组实现
        BlockingQueue<Integer> arrayQueue = new ArrayBlockingQueue<>(3);
        // 2. LinkedBlockingQueue：容量为3，链表实现
        BlockingQueue<Integer> linkedQueue = new LinkedBlockingQueue<>(3);

        System.out.println("使用 ArrayBlockingQueue:");
        testQueue(arrayQueue);

        System.out.println("\n使用 LinkedBlockingQueue:");
        testQueue(linkedQueue);
    }

    private static void testQueue(BlockingQueue<Integer> queue) {
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("生产者准备放入: " + i);
                    queue.put(i); // 队列满时阻塞
                    System.out.println("生产者已放入: " + i);
                    Thread.sleep(500); // 模拟生产间隔
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(1000); // 模拟消费间隔
                    Integer item = queue.take(); // 队列空时阻塞
                    System.out.println("消费者取出: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
