package javaguide.designMode;

/**
 * 【抽象工厂模式】
 * 关注点：一族产品
 * 定义：为创建一组相关或相互依赖的对象提供一个接口，而无需指定他们的具体类
 * 特点：一个工厂类可以生产多个产品，并且这些产品通常是相关联的（属于同一个产品族）
 * 优点：保证同一个产品族的兼容性
 */
public class AbstractFactoryMethod {
    public static void main(String[] args) {
        AbstractFactory factory = new HuaweiFactory();
        Phone2 phone = factory.createPhone();
        Computer2 computer = factory.createComputer();
        phone.call();
        computer.compute();
    }
}
// 产品族：手机 & 电脑
interface Phone2 {
    void call();
}
interface Computer2 {
    void compute();
}
// 华为系列
class HuaweiPhone implements Phone2 {
    @Override
    public void call() {
        System.out.println("华为手机打电话");
    }
}
class HuaweiComputer implements Computer2 {
    @Override
    public void compute() {
        System.out.println("华为电脑计算");
    }
}
// 小米系列
class XiaomiPhone implements Phone2 {
    public void call() {
        System.out.println("小米手机打电话");
    }
}
class XiaomiComputer implements Computer2 {
    public void compute() {
        System.out.println("小米电脑运算");
    }
}

// 抽象工厂：一族产品
interface AbstractFactory {
    Phone2 createPhone();
    Computer2 createComputer();
}
class HuaweiFactory implements AbstractFactory {

    @Override
    public Phone2 createPhone() {
        return new HuaweiPhone();
    }

    @Override
    public Computer2 createComputer() {
        return new HuaweiComputer();
    }
}
class XiaomiFactory implements AbstractFactory {
    public Phone2 createPhone() {
        return new XiaomiPhone();
    }
    public Computer2 createComputer() {
        return new XiaomiComputer();
    }
}
