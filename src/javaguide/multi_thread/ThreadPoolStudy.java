package javaguide.multi_thread;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolStudy {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 5;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;
    public static void main(String[] args) {
        // 使用阿里巴巴推荐的创建线程池的方式
        // 通过ThreadPoolExecutor构造函数自定义参数创建
        /**
         * corePoolSize: the number of threads to keep in the pool, even if they are idle,
         * unless allowCoreThreadTimeOut is set.
         * 核心池大小: 池中保留的线程数量，尽管他们处于空闲状态，除非allowCoreThreadTimeOut参数被设置。
         * maximumPoolSize: the maximum number of threads to allow in the pool.
         * 最大池大小: 池中允许的最大的线程数量。
         * keepAliveTime: when the number of threads is greater than the core, this is the maximum time
         * that excess idle threads will wait for new tasks before terminating.
         * 保持活跃时间: 当线程数量超过核心池大小，该参数表示过多的空闲线程在结束之前等待新任务的最大时间。
         * unit: the time unit for the keepAliveTime argument.
         * 单位: keepAliveTime参数的时间单位
         * workQueue: the queue to use for holding tasks before they are executed.
         * This queue will hold only the Runnable tasks submitted by the execute method.
         * 工作队列: 用于保存未执行任务的队列，该队列只保存通过execute方法提交的Runnable任务。
         * handler: the handler to use when execution is blocked because the thread bounds and
         * queue capacities are reached.
         * 处理器: 由于线程用完和达到队列容量时导致阻塞时的处理。
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        for (int i = 0; i < 10; i++) {
            Runnable worker = new MyRunnable("" + i);
            /**
             * Executes the given task sometime in the future.
             * The task may execute in a new thread or in an existing pooled thread.
             * If the task cannot be submitted for execution, either because this executor has been shutdown
             * or because its capacity has been reached, the task is handled by the current RejectedExecutionHandler.
             * 在未来某个时候执行给定的任务。该任务可能执行于新线程中或者已经存在于池中的线程。
             * 如果该任务不能被提交以执行，要么是executor已经关闭，
             * 或者由于达到容量，该任务被当前的RejectedExecutionHandler处理。
             */
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()){

        }
        System.out.println("All threads finished.");
    }
}
class MyRunnable implements Runnable {
    private String command;

    public MyRunnable(String s) {
        this.command = s;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "; start time =" + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + "; end time =" + new Date());
    }

    private void processCommand() {
        try {
            System.out.println("command: " + command);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return this.command;
    }
}