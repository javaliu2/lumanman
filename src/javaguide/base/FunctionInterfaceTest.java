package javaguide.base;

/**
 * 函数式接口：只包含一个抽象方法的接口，一般带有@FunctionalInterface注解
 * 可以有其他default的非抽象方法，即具有方法体，已经实现的方法
 * Java中的函数式接口有：
 * Runnable, Callable, Comparator等
 */
@FunctionalInterface
interface MyInterface {
    void say();
//    void say2();  //ERROR:  Multiple non-overriding abstract methods found in interface javaguide.base.MyInterface
    default void other() {
        System.out.println("default one");
    }
    default void other2() {
        System.out.println("default two");
    }
}
public class FunctionInterfaceTest {
    public static void main(String[] args) {
        MyInterface i = () -> {
            System.out.println("lambda implements abstract method.");
        };
        i.say();
    }
}
