package javaguide.base;

/**
 * 在java7之前，接口的方法都是抽象的；
 * 在java8之后，接口中可以包含具体实现的方法(default, static, private)，不再全部是抽象的
 */
public interface InterfaceTest {
    void abstractMeth();
    default void withBodyMeth(){  // java8 引入
        System.out.println("default with body method.");
    }
    static void staticMeth(){  // java8 引入
        System.out.println("static method");
    }
//    private void privateMeth(){  // java9 引入
//        System.out.println("private meth");
//    }
}
