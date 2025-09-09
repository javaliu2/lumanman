package newcoder.exam;

import org.junit.Test;

import java.util.*;

/**
 * 用户输入字符串集合，返回前n个重复次数最高的字符串
 */
public class TreeMapCount {

    public void topNFrequent(List<String> input, List<String> result, int n) {
        // 不能直接使用TreeMap按照value来排序，没有这种用法
//        TreeMap<String, Integer> cnt = new TreeMap<>();
        // 正确思路，使用hashmap进行统计，然后使用大根堆查询前n个字符串
        Map<String, Integer> cnt = new HashMap<>();
        for (String s : input) {
            cnt.put(s, cnt.getOrDefault(s, 0) + 1);
        }
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> {
           return b.getValue().compareTo(a.getValue());
        });
        pq.addAll(cnt.entrySet());
        for (int i = 0; i < n && !pq.isEmpty(); ++i) {
            result.add(pq.poll().getKey());
        }
    }
    @Test
    public void test() {
        List<String> input = Arrays.asList(
                "apple", "banana", "apple", "orange", "banana", "apple", "pear", "orange", "banana"
        );
        List<String> result = new ArrayList<>();
        topNFrequent(input, result, 9);
        System.out.println(result); // [apple, banana]
    }
}
