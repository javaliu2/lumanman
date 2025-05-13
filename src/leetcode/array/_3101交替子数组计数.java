package leetcode.array;

public class _3101交替子数组计数 {
    /* my solution*/
    static long countAlternatingSubarrays_my(int[] nums, int len) {
        // brute force
        long res = 0, cnt;
        for (int i = 0; i < len; i++) {
            cnt = 1;
            for (int j = i + 1; j < len; j++) {
                if (nums[j] != nums[j - 1]) {
                    cnt++;
                } else {
                    break;
                }
            }
            res += cnt;
        }
        return res;
    }

    /* 灵神 */
    static long countAlternatingSubarrays(int[] nums, int len) {
        long ans = 0, cnt = 0;
        for (int i = 0; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                cnt = 1;
            } else {
                cnt++;
            }
            ans += cnt;
        }
        return ans;

    }

    public static void main(String[] args) {
        int[] p = {0, 1, 1, 1};
        int len = 4;
        long res = countAlternatingSubarrays(p, len);
        System.out.println(res);
    }
}
