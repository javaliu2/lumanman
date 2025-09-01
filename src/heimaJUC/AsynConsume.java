package heimaJUC;

import java.sql.SQLOutput;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 生产者、消费者
 * 异步消费，生产者和消费者没有一一对应的关系
 */
public class AsynConsume {
    public static void main(String[] args) throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue(3);
        for (int i = 0; i < 5; i++) {
            int t = i;
            new Thread(() -> {
                messageQueue.put(new Message(t, "message"));
            }, "product" + i).start();
        }
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    messageQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "consumer").start();
    }
}

class MessageQueue {
    private LinkedList<Message> queue = new LinkedList<>();

    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take() {
        String threadName = Thread.currentThread().getName();
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    System.out.printf("[%s]: 队列为空，%s等待\n", LocalDateTime.now(), threadName);
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Message message = queue.removeFirst();
            System.out.printf("[%s]: 队列不为空，%s获取到消息: %s\n", LocalDateTime.now(), threadName, message);
            queue.notifyAll();
            return message;
        }
    }

    public void put(Message message) {
        String threadName = Thread.currentThread().getName();
        synchronized (queue) {
            while (queue.size() == capacity) {
                try {
                    System.out.printf("[%s]: 队列已满，%s进入等待状态\n", LocalDateTime.now(), threadName);
                    queue.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.addLast(message);
            System.out.printf("[%s]: 队列有剩余容量，%s将消息加入队列\n", LocalDateTime.now(), threadName);
            queue.notifyAll();
        }
    }
}

class Message {
    private int id;
    private Object message;

    public Message(int id, Object message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public Object getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message=" + message +
                '}';
    }
}
