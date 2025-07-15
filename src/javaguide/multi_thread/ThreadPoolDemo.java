package javaguide.multi_thread;

import java.util.LinkedList;
import java.util.List;

/**
 * 线程池学习， G-bro提供的code
 */
public class ThreadPoolDemo {
    // 线程池大小
    private final int poolSize;
    // 任务队列
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    // 工作线程列表
    private final List<Worker> workers = new LinkedList<>();
    // 线程池是否关闭
    private volatile boolean isShutdown = false;

    public ThreadPoolDemo(int poolSize) {
        this.poolSize = poolSize;
        initWorkers();
    }

    // 初始化工作线程
    private void initWorkers() {
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker("Worker-" + i);
            workers.add(worker);
            worker.start();
        }
    }

    // 提交任务
    public void submit(Runnable task) {
        synchronized (taskQueue) {
            if (isShutdown) {
                throw new IllegalStateException("ThreadPool is shutdown, cannot accept new tasks.");
            }
            taskQueue.addLast(task);
            taskQueue.notify(); // 唤醒一个等待的线程
        }
    }

    // 关闭线程池
    public void shutdown() {
        isShutdown = true;
        synchronized (taskQueue) {
            taskQueue.notifyAll(); // 唤醒所有工作线程，准备退出
        }
    }

    // 内部类：工作线程
    private class Worker extends Thread {
        public Worker(String name) {
            super(name);
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " start.");
            Runnable task;
            while (true) {
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " wait.");
                            taskQueue.wait();
                            System.out.println(Thread.currentThread().getName() + " wake up.");
                        } catch (InterruptedException ignored) {}
                    }
                    if (isShutdown && taskQueue.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " exit.");
                        break; // 没有任务且线程池关闭，退出
                    }
                    task = taskQueue.removeFirst();
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.err.println("Task execution failed: " + e.getMessage());
                }
            }
            System.out.println(Thread.currentThread().getName() + " exiting.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolDemo obj = new ThreadPoolDemo(3);
        Thread.sleep(500);
        Runnable task = () -> {
            System.out.println("Hello ThreadPool");
        };
        obj.submit(task);
        Thread.sleep(500);
        Runnable task2 = () -> {
            System.out.println("Hello ThreadPool2");
        };
        obj.submit(task2);
        obj.shutdown();
    }
}

