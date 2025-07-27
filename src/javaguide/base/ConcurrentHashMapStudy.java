package javaguide.base;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class ConcurrentHashMapStudy {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    }
    @Test
    public void testLongAdder() {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<String, LongAdder>();
        freqs.computeIfAbsent("xs", k -> new LongAdder()).increment();
        LongAdder xs = freqs.get("xs");
        System.out.println(xs);
    }
}
