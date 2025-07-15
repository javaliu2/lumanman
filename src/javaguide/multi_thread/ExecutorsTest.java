package javaguide.multi_thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Q: 为什么不建议使用Executors来创建线程池？
 * A: 因为他创建的线程池，使用的是LinkedBlockingQueue，他是一个无界阻塞队列
 * 当线程数量有限，任务越来越多的时候，不被处理的任务不断加入LinkedBlockingQueue会造成内存溢出
 * 另外，使用Executors不能指定线程的名字，出现错误时不利于排查
 */
public class ExecutorsTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Executors.newSingleThreadExecutor();
    }
}
