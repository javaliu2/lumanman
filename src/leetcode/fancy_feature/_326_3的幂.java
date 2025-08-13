package leetcode.fancy_feature;

import org.junit.Test;

public class _326_3的幂 {
    /**
     * 针对n==2147483647==Integer.MAX_VALUE,
     * 乘法溢出，m变为负数，所以死循环了
     */
    public boolean isPowerOfThree_v1(int n) {
        // int m = 1;  // version 1, num overflow
        long m = 1L;
        while (m < n) {
            m *= 3;
        }
        return m == n;
    }

    @Test
    public void findMaxNum() {
        long m = 1L;
        while (m <= Integer.MAX_VALUE) {
            m *= 3;
        }
        System.out.println(m / 3);  // int范围内最大的3的次幂的数， 1162261467
    }

    public boolean isPowerOfThree(int n) {
        return n > 0 && 1162261467 % n == 0;
    }
}
