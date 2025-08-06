package newcoder.base;

import org.junit.Test;

import java.util.HashMap;

public class String相关 {
    @Test
    public void testStringEquals() {
        String s = new String("ab");
        char[] chs = {'a', 'b'};
        boolean equals = s.equals(chs);
        System.out.println(equals);
    }
}
