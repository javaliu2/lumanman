package leetcode.array;

public class _3442奇偶频次间的最大差值I {
    public int maxDifference(String s) {
        // 奇数最大，偶数最小
        int[] cnts = new int[26];
        for (char ch : s.toCharArray()) {
            int idx = (int) (ch - 'a');
            cnts[idx]++;
        }
        int odd_max = Integer.MIN_VALUE, even_min = Integer.MAX_VALUE;
        for (int cnt : cnts) {
            if (cnt == 0) {
                continue;
            }
            if (cnt % 2 == 0) {
                even_min = Math.min(even_min, cnt);
            } else {
                odd_max = Math.max(odd_max, cnt);
            }
        }
        return odd_max - even_min;
    }
}
