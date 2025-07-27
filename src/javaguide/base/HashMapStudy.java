package javaguide.base;

import org.junit.Test;
import org.openjdk.jol.vm.VM;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

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

    @Test
    public void testNull(){
        // 1、可以存放null key，但是只能有一个
        // 因为null key的hash值为0，所以第二次put null key的时候会把第一次的null key值替换掉
        HashMap<String, String> map = new HashMap<>();
        map.put(null, null);
        String s = map.get(null);
        System.out.println(s);  // null
        map.put(null, "xs");
        s = map.get(null);
        System.out.println(s);  // xs
        // 2、可以存放null value，可以有多个
        // 因为key，value是存放于Node节点中，所以两者均可以为null，hash(key)会作为数组索引，所以null key只能有一个
        // 而null value可以有多个
        map.put("xs", "current is future.");
        map.put("cr", "随缘, 随缘.");
        map.put("lf", null);
        map.put("yr", null);
    }

    @Test
    public void testConcurrentCauseProblem() throws InterruptedException {
        Map<String, LongAdder> map;
         map = new HashMap<>();  // 非线程安全
//        map = new ConcurrentHashMap<>(); // 替换为线程安全版本

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executor.submit(() -> {
                String key = "key" + (finalI % 100);
//                map.put(key, map.getOrDefault(key, 0) + 1);  // 非原子操作，存在累加丢失问题
                map.computeIfAbsent(key, k -> new LongAdder()).increment();
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Map size: " + map.size());
        System.out.println("Sample: " + map.entrySet().stream().limit(10).toList());
        // hashmap版本，统计所有value之和是否为1000？ 结果: 991, 987, 1000
        long sum = map.values().stream().mapToLong(LongAdder::sum).sum();
        System.out.println("sum: " + sum);  // 979,995,996
        // concurrentHashmap版本，sum为998
        // 原因是map.put(key, map.getOrDefault(key, 0) + 1);不是原子操作，就算是后者也有可能出现累加丢失的问题
        // 使用LongAdder之后，hashmap三次结果分别是979,995,996，出现了累加丢失的问题
        // 而concurrentHashmap三次结果均为1000，满足expectation
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
