package newcoder.base;

/**
 * 1、问：java中double-checked locking用于解决什么问题？
 * 答：在多线程环境下，即要保证某个对象只被创建一次（懒加载、延迟初始化），
 * 又要避免每次访问都加锁带来的性能损耗。
 * 2、volatile关键字可以确保变量可见，禁止指令重排序，但是不能保证原子性
 * 3、在java中对象的构造分为三步：
 * 1）分配内存
 * 2）初始化对象
 * 3）将对象引用赋值给变量
 * 但是由于指令重排序的存在，以上三步的执行顺序可能是1）3）2）
 * 那么创建对象的线程执行完1）3），此时instance不为null，
 * 线程2进入getInstance()，判断instance不为null，返回instance（此时的instance未完成初始化）
 */
public class DoubleCheckedLockingTest {
}
class Singleton {
    /**
     * 必须使用volatile关键字的原因：
     * 禁止指令重排序，确保初始化过程对其他线程可见
     */
    private static volatile Singleton instance;

    /**
     * 只有第一次初始化才加锁，后续读取完全无锁，提高性能且保证线程安全
     * @return
     */
    public static Singleton getInstance() {
        if (instance == null) {  // 第一次检查（不加锁）
            synchronized (Singleton.class) {
                if (instance == null) {  // 第二次检查（加锁后再检查）
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}