package javaguide.base;

/**
 * 1、静态代码块
 * 1）触发时机：类第一次加载到内存时执行，而且只执行一次
 * 2）作用范围：给类本身做初始化，而不是某个具体对象
 * 3）常见用途：初始化静态变量；做全局配置；注册JDBC驱动
 * 4）特点：按照书写顺序执行；只执行一次，不会随着对象创建而重复执行
 * 2、实例代码块
 * 1）触发时机：每次new对象时，在构造方法之前执行
 * 2）作用范围：给对象实例做初始化
 * 3）常见用途：多个构造方法里都需要的公共初始代码可以放这里，避免重复写
 * 4）特点：按照书写顺序执行，每次创建对象都会执行一次（不管调用哪个构造方法）
 */
public class CodeBlock {
    public static void main(String[] args) {
        new CodeB();
        System.out.println("---");
        new CodeB();
        InterfaceA a;
    }
}
class CodeA{
    static {
        System.out.println("static CodeA");
    }
    {
        System.out.println("non-static CodeA");
    }
    CodeA(){
        System.out.println("constructor A");
    }
}
class CodeB extends CodeA{
    static {
        System.out.println("static CodeB");
    }
    {
        System.out.println("non-static CodeB");
    }
    {
        System.out.println("non-static CodeB 2");
    }
    CodeB(){
        System.out.println("constructor B");
    }
}