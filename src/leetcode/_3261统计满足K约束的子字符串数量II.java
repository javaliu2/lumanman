package leetcode;

import org.junit.Test;

public class _3261统计满足K约束的子字符串数量II {
    /**
     * My Solution(brute force)
     * 620 / 627 个通过的测试用例
     * @param s
     * @param k
     * @param queries
     * @return
     */
    public long[] countKConstraintSubstrings_my(String s, int k, int[][] queries) {
        int m = queries.length;
        char[] ch = s.toCharArray();
        long[] ans = new long[m];
        for (int idx = 0; idx < m; idx++) {
            int start = queries[idx][0], end = queries[idx][1];
            int t = 0;
            for (int i = start; i <= end; i++) {
                int[] cnt = new int[2];
                for (int j = i; j <= end; j++) {
                    cnt[ch[j] - '0']++;
                    if (cnt[0] > k && cnt[1] > k) {
                        break;
                    }
                    t++;
                }
            }
            ans[idx] = t;
        }
        return ans;
    }


    /**
     * based algorithm：前缀和、滑动窗口、二分查找
     * 我只能说一句牛
     * @author 灵茶山艾府
     */
    public long[] countKConstraintSubstrings(String S, int k, int[][] queries) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] left = new int[n];
        long[] sum = new long[n + 1];
        int[] cnt = new int[2];
        int l = 0;
        for (int i = 0; i < n; i++) {
            cnt[s[i] & 1]++;
            while (cnt[0] > k && cnt[1] > k) {
                cnt[s[l] & 1]--;
                l++;
            }
            left[i] = l; // 记录合法子串右端点 i 对应的最小左端点 l
            // 计算 i-left[i]+1 的前缀和
            sum[i + 1] = sum[i] + i - l + 1;
        }

        long[] ans = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int ql = queries[i][0];
            int qr = queries[i][1];
            // 如果区间内所有数都小于 ql，结果是 j=qr+1
            int j = lowerBound(left, ql - 1, qr + 1, ql);
            ans[i] = sum[qr + 1] - sum[j] + (long) (j - ql + 1) * (j - ql) / 2;
        }
        return ans;
    }

    // 返回在开区间 (left, right) 中的最小的 j，满足 nums[j] >= target
    // 如果没有这样的数，返回 right
    // 原理见 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[] nums, int left, int right, int target) {
        while (left + 1 < right) { // 区间不为空
            // 循环不变量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid; // 范围缩小到 (mid, right)
            } else {
                right = mid; // 范围缩小到 (left, mid)
            }
        }
        return right;
    }

    @Test
    public void test(){
        String s = "0001110011001110";
        int k = 2;
        int[][] queries= {{4, 8}};
        countKConstraintSubstrings(s, k, queries);
    }
}
