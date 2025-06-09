package leetcode.fancy_feature;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class _440字典序的第K小数字 {
    // ovetime, passed 29/69
    public int findKthNumber_bf(int n, int k) {
        // brute force
        Set<String> set = new TreeSet<>();
        for (int num = 1; num <= n; ++num) {
            String num_str = "" + num;
            set.add(num_str);
        }
        Iterator<String> iter = set.iterator();
        int cnt = 1;
        String ele = null;
        while (iter.hasNext()) {
            ele = iter.next();
            if (k == cnt) {
                break;
            }
            cnt++;
        }
        if (ele == null) {
            return 0;
        }
        return Integer.parseInt(ele);
    }

    /**
     * @author: LeetCode Official
     * @param n
     * @param k
     * @return
     */
    public int findKthNumber(int n, int k) {
        int curr = 1;
        k--;
        while (k > 0) {
            int steps = getSteps(curr, n);
            if (steps <= k) {
                k -= steps;
                curr++;
            } else {
                curr = curr * 10;
                k--;
            }
        }
        return curr;
    }

    public int getSteps(int curr, long n) {
        int steps = 0;
        long first = curr;
        long last = curr;
        while (first <= n) {
            steps += Math.min(last, n) - first + 1;
            first = first * 10;
            last = last * 10 + 9;
        }
        return steps;
    }
}
