package javaguide.base;

import java.io.Serializable;

/**
 * 静态方法不支持重写（只能隐藏），不支持多态（静态绑定），编译时确定
 */
public class StaticOverwriteTest {

    public static void main(String[] args) {
        StaticParent p = new StaticChild();
        p.say();
    }
}

class StaticParent {
    static void say() {
        System.out.println("Hello from Parent.");
    }
}

class StaticChild extends StaticParent {
    static void say() {
        System.out.println("Hello from Child.");
    }
}
