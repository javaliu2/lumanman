package javaguide.designMode;

/**
 * 【工厂模式】
 * 关注点：一个产品
 * 定义：将对象的创建过程延迟到子类中，由子类决定实例化哪一个类
 * 特点：一个工厂类负责生产某一类产品
 * 优点：解耦对象的创建和使用
 *
 */
public class FactoryMethod {
}
interface Product {
    void show();
}
class Phone implements Product {
    @Override
    public void show() {
        System.out.println("生产手机");
    }
}
class Computer implements Product {
    @Override
    public void show() {
        System.out.println("生产电脑");
    }
}
interface Factory {
    Product createProduct();
}
class PhoneFactory implements Factory {
    @Override
    public Product createProduct() {
        return new Phone();
    }
}

class ComputerFactory implements Factory {
    @Override
    public Product createProduct() {
        return new Computer();
    }
}