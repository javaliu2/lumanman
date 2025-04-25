package javaguide.base.multi_thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 创建线程的四种方式
 * 1、继承Thread类
 * 重写的是run()，调用的方法是start()
 * 2、实现Runnable接口
 * 1）显式定义即通过class的方式
 * 2）隐式定义即匿名内部类
 * 3）lambda表达式，Runnable是函数式接口，函数式接口是只包含一个抽象方法的接口
 * 3、实现Callable接口
 * 4、线程池
 * 5、记，run()无返回值，无异常; call()有返回值、有异常
 */
public class ThreadCreateTest {
    public static void main(String[] args) {
        for (int i = 0; i < 3; ++i) {
            new ExtendsThreadOne().start();
        }
        // 第一种方式，继承Thread类
        Thread method1 = new ExtendsThreadOne();
        method1.start();

        // 第二种方式，实现Runnable接口
        Thread method2_1 = new Thread(new ImplementsRunnableTwo());
        method2_1.start();
        Thread method2_2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("通过匿名内部类实现Runnable接口的方式创建线程");
            }
        });
        method2_2.start();

        Thread method2_3 = new Thread(() -> System.out.println("通过lambda表达式实现Runnable接口的方式创建线程"));
        method2_3.start();

        // 第三种方式，实现Callable接口
        FutureTask<List<String>> futureTask = new FutureTask<>(new ImplementsCallableThree());
        Thread thread = new Thread(futureTask);
        thread.start();
        List<String> strings = null;
        try {
            strings = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println(strings);

        // 第四种方式，通过线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new ImplementsRunnableTwo());
        executorService.execute(new ExtendsThreadOne());
        Callable<String> task = () -> {
            Thread.sleep(1000);
            return "Hello, thread pool";
        };
        Future<String> future = executorService.submit(task);
        try {
            String s = future.get();
            System.out.println(s);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

class ExtendsThreadOne extends Thread {
    @Override
    public void run() {
        // 打印10个数，包括当前线程的id
        long threadId = Thread.currentThread().getId();
        for (int i = 0; i < 10; ++i) {
            System.out.println("ExtendsThreadOne, Thread ID: " + threadId + "; Number: " + i);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
class ImplementsRunnableTwo implements Runnable{

    @Override
    public void run() {
        System.out.println("通过显式实现Runnable接口的方式创建线程");
    }
}

/**
 * 接口泛型参数<T>就是下面call()的返回值类型
 */
class ImplementsCallableThree implements Callable<List<String>> {

    @Override
    public List<String> call() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        return list;
    }
}
