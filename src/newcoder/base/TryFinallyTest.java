package newcoder.base;

/**
 * output:
 * finally
 * Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 4 out of bounds for length 4
 * 	at newcoder.base.TryFinallyTest.main(TryFinallyTest.java:7)
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
}
