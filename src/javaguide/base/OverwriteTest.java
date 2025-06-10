package javaguide.base;

import javaguide.utils.Person;
import javaguide.utils.Student;

import java.io.FileNotFoundException;
import java.io.IOException;

public class OverwriteTest {
    /**
     * 总结:
     * 两同两小一大
     * 方法名称一致，形参一致
     * 返回类型 小于等于 基类的
     * 抛出的异常 小于等于 基类的
     * 访问权限 大于等于 基类的
     */
}
class Parent {
    protected Person meth() throws IOException {
        return new Person();
    }

    Person meth2(){
        return new Person();
    }
}
class Child extends Parent {
    /**
     * 缺省的话，报如下错误
     * 'meth()' in 'javaguide.base.Child' clashes with 'meth()' in 'javaguide.base.Parent';
     * attempting to assign weaker access privileges ('package-private'); was 'protected'
     * 子类重写方法的访问权限应该 [大于等于] 父类方法的
     */
    @Override
    public Student meth() throws FileNotFoundException {
        return new Student();
    }

    /**
     * 'meth()' in 'javaguide.base.Child' clashes with 'meth()' in 'javaguide.base.Parent';
     * overridden method does not throw 'java.lang.Exception'
     * 异常同理，子类重写方法抛出的异常要 [小于等于] 父类方法声明的
     * 如上 meth() 所示
     */
//    @Override
//    Student meth() throws Exception {
//        return new Student();
//    }

    /**
     * 'meth2()' in 'javaguide.base.Child' clashes with 'meth2()' in 'javaguide.base.Parent';
     * attempting to use incompatible return type
     * 这是由于重载方法 meth2 的返回类型 大于 父类对应方法的返回类型
     * [小于等于] 是可以的，如上方法meth所示
     * @return
     */
//    @Override
//    PersonParent meth2() {
//        return new PersonParent();
//    }
}
