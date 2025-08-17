package newcoder.multi_thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class TicketSell {
    static Random random = new Random();  // Random为线程安全
    static int randomAmount() {
        return random.nextInt(5) + 1;
    }
    public static void main(String[] args) {
        TicketWindow ticketWindow = new TicketWindow(2000);
        List<Thread> list = new ArrayList<>();
        List<Integer> sellCount = new Vector<>();  // 用来存储卖出去多少张票
        for (int i = 0; i < 2000; i++) {
            Thread t = new Thread(()->{
                int count = ticketWindow.sell(randomAmount());
                sellCount.add(count);  // 不存在线程安全，因为Vector是线程安全类
            });
            list.add(t);  // 只有主线程一个线程使用，所以可以采用不保证线程安全的ArrayList
            t.start();
        }
        list.forEach((t) -> {
            try {
                t.join();  // 主线程阻塞等待其他线程的完成
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        // 卖出去的票求和
        System.out.printf("selled count: %d\n", sellCount.stream().mapToInt(c->c).sum());
        System.out.printf("remainder count: %d\n", ticketWindow.getCount());
    }
}
class TicketWindow {
    private int count;
    public TicketWindow(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public synchronized int sell(int amount) {
        if (this.count >= amount) {
            this.count -= amount;
            return amount;
        }
        return 0;
    }
}
