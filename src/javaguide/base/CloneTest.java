package javaguide.base;

public class CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Address address = new Address("邯郸");
        Address address2 = address.clone();
        System.out.println(address2 == address);  // false
        // 浅拷贝，引用类型的属性只是拷贝其值，而不是duplicate对象实体
        // 不论引用类型还是基本类型，其实都是拷贝的值，基本类型不涉及对象，因此其值就是对象本身
        // 而引用类型，其值只是对象的内存地址，因此拷贝的结果就是两个对象的引用类型属性指向同一个对象
        System.out.println(address2.getName() == address.getName());  // true
        final char[] ref = new char[10];
        ref[0] = 'a';  // 可以修改引用的对象
        // Cannot assign a value to final variable 'ref'
//        ref = new char[10];  // 不可以修改引用本身，即指向新的对象
    }
}
class Address implements Cloneable {
    private String name;
    Address() {

    }
    Address(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 浅拷贝
     */
    @Override
    protected Address clone() throws CloneNotSupportedException {
        return (Address)super.clone();  // Object类默认的clone方法实现，浅拷贝
    }
    /**
     * 深拷贝
     */
//    @Override
//    protected Address clone() throws CloneNotSupportedException {
//        Address address =  (Address)super.clone();
//        address.name = new String(this.name);
//        return address;
//    }
}
