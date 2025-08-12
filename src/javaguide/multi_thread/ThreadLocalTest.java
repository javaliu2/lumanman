package javaguide.multi_thread;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * output:
 * Thread A has value: 13
 * Thread C has value: 15
 * Thread B has value: 14
 * explain:
 * 每一个Thread 有一个 ThreadLocal.ThreadLocalMap threadLocals属性
 * threadLocal.set(threadId)：
 * public void set(T value) {
 * Thread t = Thread.currentThread();
 * ThreadLocalMap map = this.getMap(t);  // this是当前ThreadLocal对象实例
 * if (map != null) {
 * map.set(this, value);  // 设置key为当前threadLocal对象，value为用户要设置的值
 * } else {
 * this.createMap(t, value);
 * }
 * }
 * // 返回当前线程的 threadLocals 成员变量
 * ThreadLocalMap getMap(Thread t) {
 * return t.threadLocals;
 * }
 * // 创建threadLocals对象，key为this，即为当前ThreadLocal对象，value就是要设置的值，和set一样
 * void createMap(Thread t, T firstValue) {
 * t.threadLocals = new ThreadLocalMap(this, firstValue);
 * }
 * <p>
 * public T get() {
 * Thread t = Thread.currentThread();
 * ThreadLocalMap map = this.getMap(t);  // 拿到的是自己线程的 threadLocals
 * if (map != null) {
 * ThreadLocalMap.Entry e = map.getEntry(this);  // 所以说，拿到的是自己线程设置的值
 * if (e != null) {
 * T result = e.value;
 * return result;
 * }
 * }
 * return this.setInitialValue();
 * }
 * public void remove() {
 * ThreadLocalMap m = this.getMap(Thread.currentThread());
 * if (m != null) {
 * m.remove(this);
 * }
 * }
 */
public class ThreadLocalTest {

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        Runnable task = () -> {
            int threadId = (int) (Thread.currentThread().getId() % 1000);
            threadLocal.set(threadId);  // 设置线程本地变量
            try {
                Thread.sleep(100);  // 模拟操作
                System.out.println("Thread " + Thread.currentThread().getName()
                        + " has value: " + threadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                threadLocal.remove();  // 避免内存泄漏（强烈推荐）
            }
        };

        new Thread(task, "A").start();
        new Thread(task, "B").start();
        new Thread(task, "C").start();
    }

    /**
     * GC之后key是否为null?
     * output:
     * 弱引用key:java.lang.ThreadLocal@56a1d551,值:abc
     * --gc后--
     * 弱引用key:null,值:def  // 主动GC，key被回收
     */
    @Test
    public void testGC() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Thread t = new Thread(() -> test("abc", false));
        t.start();
        t.join();  // 阻塞调用这行代码的线程，即当前线程，当前线程进入waiting状态，直到线程 t 运行结束
        System.out.println("--gc后--");
        Thread t2 = new Thread(() -> test("def", true));
        t2.start();
        t2.join();
    }

    private void test(String s, boolean isGC) {
        try {
//            ThreadLocal<String> var = new ThreadLocal<>();
//            var.set(s);
            // 设置变量指向ThreadLocal对象，由于强引用的存在，所以不会被GC
            //弱引用key:java.lang.ThreadLocal@56a1d551,值:abc
            //--gc后--
            //弱引用key:java.lang.ThreadLocal@103546c6,值:def
            new ThreadLocal<>().set(s);
            if (isGC) {
                System.gc();
            }
            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object ThreadLocalMap = field.get(t);
            Class<?> tlmClass = ThreadLocalMap.getClass();
            Field tableField = tlmClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] arr = (Object[]) tableField.get(ThreadLocalMap);
            for (Object o : arr) {
                if (o != null) {
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.printf("弱引用key:%s,值:%s%n", referenceField.get(o), valueField.get(o));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

