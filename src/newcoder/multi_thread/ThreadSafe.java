package newcoder.multi_thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 临界区（critical section）: 访问共享资源的那部分代码
 * 竞态条件（race condition）: 当多个线程同时访问共享资源，没有使用同步机制，那么结果依赖于线程的执行顺序，就会发生竞态条件
 * 线程安全问题: 多个线程同时访问共享资源时，是否有并发问题发生
 */
public class ThreadSafe {
    public static void main(String[] args) {

    }
}
class UnSafe {
    // 调用方法使用的是栈内存，不同线程彼此独立，所以不存在线程安全问题
    public void method(int num) {
        List<Integer> l = new ArrayList<>();
        l.add(num);
        method2(l);  // 但是这里，如果子类对method2重写，那么由于重写方法行为的不可预测，可能会导致线程安全问题的出现
    }
    public void method2(List<Integer> l) {

    }
    // 所以说，如果不想让子类修改父类的行为，要么将父类中方法的访问修饰符设置为private，要么使用final关键字修饰方法
    // 参见String将类声明为final，避免了子类修改父类中方法行为可能导致的线程安全问题的出现
}
class UnSafeChild extends UnSafe {
    @Override
    public void method2(List<Integer> l) {
        new Thread(() -> {
            l.remove(0);  // 这样的话，就存在问题。因为是两个线程对同一个共享资源进行了访问
        }).start();
    }
}

