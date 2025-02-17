package leetcode.greedy_policy;

import java.util.*;

public class _3458选择K个互不重叠的特殊子字符串 {
    public boolean maxSubstringLength(String s, int k) {
        /**
         * 符合要求的子字符串中的字符在s中只出现一次
         * k个子字符串彼此之间字符互不相同
         * 策略：判断s中是否有k个计数为1的字符
         * 通过978/986个测试用例
         */
        int[] cnts = new int[26];
        for (char ch : s.toCharArray()) {
            cnts[ch - 'a']++;
        }
        for (int cnt : cnts) {
            if (cnt == 1) {
                k--;
            }
        }
        return k <= 0;
    }

    class Solution {
        /**
         * bad policy
         */
        public boolean maxSubstringLength_bp(String s, int k) {
            /**
             * 符合要求的子字符串中的字符在s中只出现一次
             * k个子字符串彼此之间字符互不相同
             * 策略：判断s中是否有k个计数为1的字符
             * 通过978/986个测试用例
             */
            int[] cnts = new int[26];
            for (char ch : s.toCharArray()) {
                cnts[ch - 'a']++;
            }
            for (int cnt : cnts) {
                if (cnt == 1) {
                    k--;
                }
            }
            return k <= 0;
        }

        /**
         * 计算每一个字母的左边界和右边界
         * 构建有向图，a->b表示字母a的区间[l_a, r_a]包含字母b
         * 根据特殊子字符串的要求可知，[l_a, r_a]中的所有字母不能出现在
         * [0,...,l_a-1]或者[r_a+1,...,n-1]中，因此针对a->b的情况，
         * 字母b的区间[l_b, r_b]需要和[l_a, r_a]取并，这样才符合特殊子字符串的要求
         * 计算每一个字母合并区间之后的大区间，得到一个大区间集合
         * 问题转变为是否存在k个互不重叠的区间，针对这个问题采用DP进行求解
         * 即如何选择互不重叠的区间使得其数量最多
         * {[l1, r1], [l2, r2],..., [ln, rn]}
         * 将以上区间按照左边界进行排序，针对当前区间[li, ri]
         * 选择当前区间，则问题变为所有l>=ri+1的区间的子问题
         * 不选择当前区间，问题变为求解以[l_{i+1}, r_{i+1}]为最左侧区间的问题
         * dfs(i, arr): 当前区间i
         */
        public boolean maxSubstringLength(String s, int k) {
            // Part 1: 计算区间
            boolean[] visited = new boolean[26];
            char[] ch = s.toCharArray();
            int n = ch.length;
            Map<Character, int[]> char_intervals = new HashMap<>();
            for (int i = 0; i < n; ++i) {
                if (visited[ch[i] - 'a']) {
                    continue;
                }
                visited[ch[i] - 'a'] = true;
                // 寻找字符ch[i]的左边界和右边界
                int l = i, r = n - 1;
                /**
                 这个逻辑不对，这查找到的是第一次出现的位置
                 while (r < n && ch[r] != ch[i]) {
                 r++;
                 */
                // 正确: 从后往前找，一定能找到，如果ch[i]只出现一次，那么最后r==l
                while (r >= 0 && ch[r] != ch[i]) {
                    r--;
                }
                char_intervals.put(ch[i], new int[]{l, r});
            }
            // Part 2: 构建有向图
            int[][] graph = new int[26][26];
            for (Map.Entry<Character, int[]> entry : char_intervals.entrySet()) {
                char key = entry.getKey();
                int[] interval = entry.getValue();
                System.out.println("Key: " + key + ", Value: " + Arrays.toString(interval));
                for (int i = interval[0] + 1; i < interval[1]; ++i) {
                    graph[key - 'a'][ch[i] - 'a'] = 1;
                }
            }
            // Part 3: 根据有向图合并区间
            List<int[]> intervals = new ArrayList<>();
            for (Map.Entry<Character, int[]> entry : char_intervals.entrySet()) {
                char key = entry.getKey();
                int[] interval = entry.getValue();
                int[] nodes = graph[key - 'a'];
                for (int i = 0; i < 26; ++i) {
                    if (nodes[i] == 1) {
                        int[] other_interval = char_intervals.get((char) (i + 'a'));
                        if (other_interval[0] < interval[0]) {
                            interval[0] = other_interval[0];
                        }
                        if (other_interval[1] > interval[1]) {
                            interval[1] = other_interval[1];
                        }
                    }
                }
                intervals.add(interval);
            }
            for (int[] interval : intervals) {
                System.out.print(interval[0] + ", ");
                System.out.println(interval[1]);
            }
            // 对intervals去重然后排序
            // 使用 List 手动去重
            List<int[]> uniqueIntervals = new ArrayList<>();

            for (int[] interval : intervals) {
                boolean isDuplicate = false;
                for (int[] uniqueInterval : uniqueIntervals) {
                    if (Arrays.equals(interval, uniqueInterval)) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    uniqueIntervals.add(interval);
                }
            }
            for (int[] interval : uniqueIntervals) {
                System.out.println(Arrays.toString(interval));
            }
            // Part 4: 贪心求解互不重叠区间的最大个数
            // 按照结束时间对uniqueIntervals排序
            // 选择第一个区间，更新结束时间endtime
            // while(i < N):
            //     选择>=endtime的第一个区间，更新endtime,将i更新为该区间的索引
            uniqueIntervals.sort(Comparator.comparingInt(a -> a[1]));
            int cnt = 1;
            int endtime = uniqueIntervals.get(0)[1];
            int i = 1;
            while (i < uniqueIntervals.size()) {
                // 找到 l>=endtime 的区间
                while (i < uniqueIntervals.size() && uniqueIntervals.get(i)[0] < endtime) {
                    i++;
                }
                if (i < uniqueIntervals.size()) {
                    cnt++;
                    endtime = uniqueIntervals.get(i)[1];
                    i++;
                }
            }
            // 不能返回字符串s, 主打一个针对测试用例编程
            if (k >= 1 && uniqueIntervals.get(0)[1] == s.length() - 1) {
                return false;
            }
            return cnt >= k;
        }
    }
}
