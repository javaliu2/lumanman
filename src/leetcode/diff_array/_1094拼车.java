package leetcode.diff_array;

import java.util.TreeMap;

public class _1094拼车 {
    public boolean carPooling_array_impl(int[][] trips, int capacity) {
        int[] diff = new int[1001];
        for (int[] trip : trips) {
            int new_passengers = trip[0];
            int from = trip[1];
            int to = trip[2];
            diff[from] += new_passengers;
            diff[to] -= new_passengers;
        }
        int sum = 0;
        for (int num : diff) {
            sum += num;
            if (sum > capacity) {
                return false;
            }
        }
        return true;
    }

    public boolean carPooling(int[][] trips, int capacity) {
        TreeMap<Integer, Integer> d = new TreeMap<>();
        for (int[] t : trips) {
            int num = t[0], from = t[1], to = t[2];
            // 形参：key, value, remappingFunc
            // 如果key不存在或者key对应的value为null, 将<key, value>放置到map中
            // 否则，使用remappingFunc计算传入的value和旧value的结果，作为新的value
            d.merge(from, num, Integer::sum);
            d.merge(to, -num, Integer::sum);
        }
        // d按照key升序
        int s = 0;
        for (int v : d.values()) {
            s += v;
            if (s > capacity) {
                return false;
            }
        }
        return true;
    }
}
