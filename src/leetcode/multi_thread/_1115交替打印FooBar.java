package leetcode.multi_thread;

public class _1115交替打印FooBar {
    public static void main(String[] args) {
        FooBar fooBar = new FooBar(5);
        Runnable t1 = () -> {
            try {
                fooBar.foo(()->{
                    System.out.print("foo");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable t2 = () -> {
            try {
                fooBar.bar(()->{
                    System.out.println("bar");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        new Thread(t2).start();
        new Thread(t1).start();
    }
}
class FooBar {
    private int n;
    private boolean fooTurn;

    public FooBar(int n) {
        this.n = n;
        this.fooTurn = true;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (!fooTurn) {
                    wait();
                }
                printFoo.run();
                fooTurn = false;
                notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (this) {
                while (fooTurn) {
                    wait();
                }
                printBar.run();
                fooTurn = true;
                notifyAll();
            }
        }
    }
}
