package leetcode.prefix_sum;

public class _1534统计好三元组 {
    public int countGoodTriplets_bf(int[] arr, int a, int b, int c) {
        /**
         * 暴力枚举判断
         * 【暴力美学】
         */
        int n = arr.length, ans = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = j + 1; k < n; ++k) {
                    if (Math.abs(arr[i] - arr[j]) > a || Math.abs(arr[j] - arr[k]) > b || Math.abs(arr[i] - arr[k]) > c) {
                        continue;
                    }
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * @author Leetcode Official
     * 条件转变。讲解见https://leetcode.cn/problems/count-good-triplets/solutions/371340/tong-ji-hao-san-yuan-zu-by-leetcode-solution/?envType=daily-question&envId=2025-04-14
     * sum[x]: <=x的元素个数
     * 由于移动j之前更新sum，因此进行下一轮判断的时候，sum存储的是i<j时的元素统计
     * sum数组下标表示l或者r，值表示<=该下标的元素有多少个，不用考虑i<j条件，因为更新sum的时候已经将该条件满足
     * 真是很巧妙，我还是个菜鸟。
     */
    public int countGoodTriplets(int[] arr, int a, int b, int c) {
        int ans = 0, n = arr.length;
        int[] sum = new int[1001];
        for (int j = 0; j < n; ++j) {
            for (int k = j + 1; k < n; ++k) {
                if (Math.abs(arr[j] - arr[k]) <= b) {
                    int lj = arr[j] - a, rj = arr[j] + a;
                    int lk = arr[k] - c, rk = arr[k] + c;
                    int l = Math.max(0, Math.max(lj, lk)), r = Math.min(1000, Math.min(rj, rk));
                    if (l <= r) {
                        if (l == 0) {
                            ans += sum[r];
                        } else {
                            ans += sum[r] - sum[l - 1];
                        }
                    }
                }
            }
            // 比如arr[j]==3，那么就是说sum[3, 4, ..., 1000]均++，表示这些下标值满足大于3，因此其值++
            for (int k = arr[j]; k <= 1000; ++k) {
                ++sum[k];
            }
        }
        return ans;
    }
}
