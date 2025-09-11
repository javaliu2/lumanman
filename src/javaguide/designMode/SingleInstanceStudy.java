package javaguide.designMode;

/**
 * 单例模式：保证一个类在整个系统中只有一个实例，并且提供一个全局访问点
 * 1、两种实现，分别是饿汉模式（eager initialization）:
 * 类一加载就创建单例对象。优点：实现简单（因为类加载时由JVM保证只加载一次）
 * 缺点：可能浪费资源，如果实例很大，但是程序一直没用到
 * 2、懒汉模式（lazy initialization）:
 * 用到该实例时再去创建
 */
public class SingleInstanceStudy {
}
class SingletonEager {
    // 1、类加载时就创建实例
    private static final SingletonEager instance = new SingletonEager();
    // 2、构造方法私有化
    private SingletonEager() {

    }
    // 3、提供全局访问点
    public static SingletonEager getInstance() {
        return instance;
    }
}
class SingletonLazy {
    private static volatile SingletonLazy instance;  // volatile关键字禁止指令重排序
    private SingletonLazy(){ }  // 构造函数私有化

    public static SingletonLazy getInstance() {
        if (instance == null) {
          synchronized (SingletonLazy.class) {  // 类锁
              if (instance == null) {
                  instance = new SingletonLazy();
              }
          }
        }
        return instance;
    }
}
