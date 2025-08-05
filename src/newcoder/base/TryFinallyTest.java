package newcoder.base;

import org.junit.Test;

/**
 * 1、output:
 * finally
 * Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 4 out of bounds for length 4
 * 	at newcoder.base.TryFinallyTest.main(TryFinallyTest.java:7)
 * 2、valid组合
 * 1）try + catch
 * 2）try + finally，抛出异常的话，会被所在方法接管
 * 3）try + catch + finally
 */
public class TryFinallyTest {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4};
        try {
            System.out.println(arr[4]);
        } finally {
            System.out.println("finally");
        }
    }
    @Test
    public void test() {
        int res = fun(1);
        System.out.println(res);  // 2
    }
    int fun(int x) {
        try {
            ++x;
            return  x;  // 会保存返回值，执行完 finally 后返回
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ++x;
//            return x;  // 会覆盖掉try中return保存的值2，而返回3
        }
        return x;  // 不会执行
    }
}
