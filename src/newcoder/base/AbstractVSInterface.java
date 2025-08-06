package newcoder.base;

public class AbstractVSInterface {
    public static void main(String[] args) {
        InterfaceA interfaceA = null;
        interfaceA.fun();
    }
}

interface InterfaceA {
    int a = 0;  // 默认: public static final

    /**
     * 默认方法
     */
    default void fun() {
        System.out.println("default");
    }

    /**
     * 静态方法
     */
    static void fun2(){
        System.out.println("static code block");
    }
}
abstract class AbstractA {
    private int a;
    AbstractA() {

    }
    abstract void fun();
}
