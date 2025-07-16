package javaguide.base;

import org.junit.Test;

import java.util.HashMap;

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
        HashMap h = new HashMap();
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
}
