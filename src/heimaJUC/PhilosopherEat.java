package heimaJUC;

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

    @Override
    public void run() {
        // 重复吃饭-思考
        while (true) {
            synchronized (left) {
                synchronized (right) {
                    eating();
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
class Chopstick {
    private String name;
    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子" + name;
    }
}
