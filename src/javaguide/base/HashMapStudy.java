package javaguide.base;

import org.junit.Test;
import org.openjdk.jol.vm.VM;

import java.util.HashMap;
import java.util.Objects;

public class HashMapStudy {
    int x = 10;
    final class Inner {
        int x = 20;

        void print() {
            System.out.println(x);             // 输出 20（Inner 的 x）
            System.out.println(HashMapStudy.this.x);  // 输出 10（Outer 的 x）
        }
    }

    public static void main(String[] args) {
        HashMap<String, String> h = new HashMap<>();
        h.put("name", "xs");
        Object o;
        String s;
    }

    /**
     * static final int hash(Object key) {
     * int h;
     * return key == null ? 0 : (h = key.hashCode()) ^ h >>> 16;
     * }
     * ^优先级低于>>>，故是先无符号右移16位，然后相异或
     */
    @Test
    public void hashStudy() {
        int h = (1 << 17) + (1 << 16); //  0000,0000_0000,0001_1000,0000_0000,0000
        int r = h ^ h >>> 16; // h >>> 16: 0000,0000_0000,0000_0000,0000_0000,0011
        System.out.println(r); // h ^ h2:  0000,0000_0000,0001_1000,0000_0000,0011
    }

    @Test
    public void accessOutterField() {
        HashMapStudy.Inner inner = new HashMapStudy.Inner();
        inner.print();
    }

    /**
     * static final int tableSizeFor(int cap) {
     *         int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
     *         return n < 0 ? 1 : (n >= 1073741824 ? 1073741824 : n + 1);
     *    }
     */
    @Test
    public void tableSize() {
        int cap = 28;
        // -1的二进制表示： 11111111,11111111,11111111,11111111
        String binaryString = Integer.toBinaryString(-1);
        System.out.println("-1's binary presentation: " + binaryString);
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);  // 计算数字 i 前导0的个数
        System.out.println("n: " + Integer.toBinaryString(n));
        int nn = n < 0 ? 1 : (n >= 1073741824 ? 1073741824 : n + 1);  // 1073741824 = 2^30
        System.out.println(nn);
    }
    @Test
    public void hashCodeAndReferenceAddress() {
        String s1 = new String("abc");
        String s2 = new String("abc");

        System.out.println(s1 == s2);                   // false，引用不同
        System.out.println(s1.equals(s2));              // true
        System.out.println(s1.hashCode());              // 96354，因为字符串内容一样
        System.out.println(s2.hashCode());              // 96354，因为字符串内容一样
    }

    /**
     * 两个对象的equals相等，那么他们的hashCode一定相等，反之不一定，因为存在hash冲突
     */
    @Test
    public void hashCodeAndEquals() {
        Person p = new Person("xs", 25);
        Person p2 = new Person("cr", 25);
        System.out.println(p);  // 默认实现: 全类名@hex(hashCode)
        System.out.println(p2);
        System.out.println(p == p2); // 比较的是对象的引用地址，注意不是hashCode，因为hashCode随着实现不同而不同
        // 存在hashCode值一样，但是==比较为false的情况
        // hashCode主要用于hash容器中快速定位而设计
        System.out.println(Integer.toHexString(p.hashCode()));  // 默认实现: 返回对象在内存中的地址的哈希值
//        System.out.println(Integer.toHexString(p2.hashCode()));

        Object obj = new Object();
        long address = VM.current().addressOf(obj);
        System.out.println("内存地址: 0x" + Long.toHexString(address));  // 内存地址: 0x7164131c8
    }

    @Test
    public void testhashCodeAndEquals2() {
        /**
         * 表述清楚，文字表述真的是很关键。对于世间法和出世间法都很关键。
         */
        // 1) a.hashCode()==b.hashCode()，equals(a,b)可能为true或者false，因为存在hash冲突
        // 反之，equals(a,b)为false，两者的hashCode可能相等也可能不相等
        String a = "Aa";
        String b = "BB";
        System.out.println(a.hashCode()); // 2112
        System.out.println(b.hashCode()); // 2112
        System.out.println(a.equals(b));  // false
        // 2) equals(a,b)==true，那么a.hashCode一定等于b.hashCode
        // 下面是2)的逆否命题
        // a.hashCode()!=b.hashCode()，equals(a,b)一定为false
    }
}
class Person {
    private String name;
    private Integer age;
    Person() {

    }
    Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int hashCode() {
        System.out.println(this.name.hashCode());
        System.out.println(this.age.hashCode());
        return Objects.hash(name, age);
    }

    @Override
    public boolean equals(Object obj) {
        // 自反
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person p2 = (Person) obj;
        return Objects.equals(this.name, p2.name) && Objects.equals(this.age, p2.age);
    }
}
