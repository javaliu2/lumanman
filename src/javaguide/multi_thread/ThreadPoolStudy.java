package javaguide.multi_thread;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 一、在ThreadPoolExecutor中，任务的执行流程：
 * 1、如果运行的线程数 < corePoolSize，直接创建新线程来执行任务（不进队列）
 * 2、否则，线程数 >= corePoolSize，尝试吧任务放进工作队列（workQueue）
 * 3、如果队列满了，并且当前线程数 < maximumPoolSize，创建新线程来执行任务（绕过队列）
 * 4、如果线程数=maximumPoolSize并且队列也满了，触发给定的拒绝策略
 * 二、核心线程数5，最大线程数7，队列大小5，提交20个任务，拒绝策略: AbortPolicy
 * 结果：抛出 RejectedExecutionException来拒绝新任务的处理
 * Exception in thread "main" java.util.concurrent.RejectedExecutionException:
 * Task xs12 rejected from java.util.concurrent.ThreadPoolExecutor@4eec7777
 * [Running, pool size = 7, active threads = 7, queued tasks = 5, completed tasks = 0]
 * 程序执行完提交的12个任务之后，卡住不动。这是由于程序没有对异常进行捕获，抛出RejectedExecutionException之后
 * 主线程结束循环，程序结束。由于线程池中的线程类型是非守护线程（用户线程），其不会随着JVM的退出而退出。
 * 没有调用线程池的shutdown方法，所以线程池会继续运行以处理任务请求
 * 三、核心线程数5，最大线程数5，队列大小3，提交10个任务，拒绝策略: CallerRunsPolicy
 * 结果：main线程执行被拒绝的任务
 */
public class ThreadPoolStudy {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 7;
    private static final int QUEUE_CAPACITY = 5;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) throws InterruptedException {
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
//                new ThreadPoolExecutor.CallerRunsPolicy()，
                new ThreadPoolExecutor.AbortPolicy()
        );
        try {
            for (int i = 0; i < 20; i++) {
                Runnable worker = new MyRunnable("xs" + i);
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
        } catch (RejectedExecutionException e) {
            System.out.println(e.getMessage());
            executor.shutdown();
        }
        /**
         * Initiates an orderly shutdown in which previously submitted tasks are executed,
         * but no new tasks will be accepted.
         * 执行先前提交的任务，并且有序关机，不会接受新任务。
         * Invocation has no additional effect if already shut down.
         * 如果线程池已经关闭，那么重复调用没有副作用。
         * This method does not wait for previously submitted tasks to complete execution.
         * 该方法不会等待已经提交的任务完成执行。就是说不会阻塞主线程。
         * Use awaitTermination to do that.
         * 使用awaitTermination来等待线程池中所有任务完成执行。
         */
        executor.shutdown();
//        while (!executor.isTerminated());  // 线程池关闭 且 所有任务执行完返回true
        // 更建议使用awaitTermination()
        boolean finished = executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        if (finished) {
            System.out.println("All threads finished.");
        }
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