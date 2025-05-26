package javaguide.base;

/**
 * Java中执行优先级：静态代码块 > 普通代码块 > 构造方法
 * 静态代码块只执行一次，在类被加载时，一般用于类的初始化，比如加载配置文件、初始化静态变量等
 * 普通代码块在每次创建对象时都会被执行，用于初始化实例属性，抽取构造函数公共逻辑（不需要显式调用，隐式调用）
 */
public class CodeBlockWithConstructorTest {
    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        testClass = new TestClass();
        /**
         * output:
         * static code block
         * normal code block
         * constructor method
         * normal code block
         * constructor method
         */
    }
}
class TestClass {
    static {
        System.out.println("static code block");
    }
    {
        System.out.println("normal code block");
    }
    TestClass() {
        System.out.println("constructor method");
    }
}