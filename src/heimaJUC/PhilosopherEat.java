package heimaJUC;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 哲学家就餐问题：
 * 有n位哲学家以及n根筷子，每根筷子放在哲学家两侧，每位哲学家都要吃饭-思考，不断重复吃饭-思考
 * 吃饭的时候需要使用两根筷子
 */
public class PhilosopherEat {
    public static void main(String[] args) {
        // 5根筷子，5个共享资源
        Chopstick c1 = new Chopstick("c1");
        Chopstick c2 = new Chopstick("c2");
        Chopstick c3 = new Chopstick("c3");
        Chopstick c4 = new Chopstick("c4");
        Chopstick c5 = new Chopstick("c5");
        System.out.println("c1 hashcode:" + c1.hashCode());
        System.out.println("c2 hashcode:" + c2.hashCode());
        System.out.println("c3 hashcode:" + c3.hashCode());
        System.out.println("c4 hashcode:" + c4.hashCode());
        System.out.println("c5 hashcode:" + c5.hashCode());
        // 5位哲学家就餐
        new Philosopher("柏拉图", c1, c2).start();
        new Philosopher("亚里士多德", c2, c3).start();
        new Philosopher("阿基米德", c3, c4).start();
        new Philosopher("苏格拉底", c4, c5).start();
        new Philosopher("米开朗琪罗", c5, c1).start();
        // 会出现死锁，每位哲学家拿起自己左边的筷子，等待自己右边的筷子，而这根筷子被右边的哲学家所占有
    }
}

class Philosopher extends Thread {
    private final Chopstick left;
    private final Chopstick right;
    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    /**
     * 会出现死锁的代码
     * 1、死锁的第二个条件，占有且等待（hold and wait）
     * 一个线程已经占有了至少一个资源，同时又请求新的资源，但被阻塞时，不释放已占有的资源
     * 哲学家A获得左手的筷子，去尝试获取右手的筷子时，假如哲学家A右手的筷子已经被他右边的哲学家B占有（该筷子是哲学家B左手的筷子），
     * 所以出现了阻塞（synchronized关键字是阻塞获取锁），同时哲学家A不释放已经占有的左手筷子
     * 2、互斥条件: 资源不能被多个线程同时使用，即某一时刻，一个资源只能分配给一个线程
     * 3、不可剥夺条件: 资源一旦被线程占有，不能强行剥夺，只能由线程自己释放
     * 4、循环等待条件: 存在一个线程-资源的循环等待链
     */
    public void run_deadlock() {
        // 重复吃饭-思考
        while (true) {
            synchronized (left) {
                synchronized (right) {
                    eating();
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            // 尝试获取左筷子，拿不到的话，释放对左筷子的占有
            if (left.tryLock()) {
                try {
                    if (right.tryLock()) {
                        try {
                            eating();
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }
            }
        }
    }
    private void eating() {
        try {
            Thread.sleep(1000);  // 思考
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "正在吃饭");
    }
}
class Chopstick extends ReentrantLock {
    private String name;
    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子" + name;
    }
}
