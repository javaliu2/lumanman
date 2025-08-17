package newcoder.multi_thread;

import java.util.Random;

public class TransferMoney {
    static Random random = new Random();  // Random为线程安全
    static int randomAmount() {
        return random.nextInt(5) + 1;
    }
    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1000);
        Account b = new Account(1000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 2000; i++) {
                a.transfer(b, randomAmount());
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 2000; i++) {
                b.transfer(a, randomAmount());
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.printf("a + b: %d\n", a.getMoney() + b.getMoney());
    }
}

class Account {
    private int money;
    public Account(int money) {
        this.money = money;
    }
    public int getMoney() {
        return this.money;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * synchronized加在方法上，相等于是对象锁，
     * a.transfer(b, randomAmount());  锁a对象
     * b.transfer(a, randomAmount());  锁b对象
     * a可以修改b中的money，同时b可以修改a中的money
     * 在多线程并发访问共享资源时，由于指令执行的交错性，可能会发生数据不一致
     * solution：加类锁
     * @param target
     * @param amount
     */
    public void transfer(Account target, int amount) {
        synchronized (Account.class) {
            if (this.money >= amount) {
                this.setMoney(this.money - amount);
                target.setMoney(target.money + amount);
            }
        }
    }
}
