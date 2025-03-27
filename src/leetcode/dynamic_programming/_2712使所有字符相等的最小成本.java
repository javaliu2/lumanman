package leetcode.dynamic_programming;

public class _2712使所有字符相等的最小成本 {
    public long minimumCost_(String s) {
        /**
         * 1、题目复述：
         * 给定字符串s,其只包括0、1字符
         * 存在两种操作：
         * 1.1 反转下标[0,..,i]的子串，开销i+1
         * 1.2 反转下标[i,...,n-1]的子串，开销n-i
         * 目标：使得s中所有字符一致的最小开销
         * 2、官方题解-动态规划
         * 2.1、维护两个数组，分别是
         * 1）将前缀全部变为0或者1的最小成本
         * 2）将后缀全部变为0或者1的最小成本
         * 2.2、定义以及转移方程：
         * 1）定义：
         * suf[i][0]表示将[i,...,n-1]字符全部反转为0的最小开销
         * 同理，suf[i][1]表示将[i,...,n-1]字符全部反转为1的最小开销
         * 2）转移方程
         * 如果s[i]==1，那么
         * suf[i][1]=suf[i+1][1]  # s[i]已经是1了，只考虑将剩下子串全部反转为1的开销
         * suf[i][0]=suf[i+1][1]+(n-i)  # 将[i+1,...,n-1]反转为1，因为操作1.2反转的是[i,...,n-1]而不仅仅是s[i]
         * 如果s[i]==0，那么
         * suf[i][1]=suf[i+1][0]+(n-i)  # 同理，将[i+1,...,n-1]反转为0，然后操作1.2将[i,...,n-1]反转为1
         * suf[i][0]=suf[i+1][0]  # 和上面同理
         *
         * pre[i][0]表示将[0,...,i]字符全部反转为0的最小开销
         * pre[i][1]表示将[0,...,i]字符全部反转为1的最小开销
         * 当s[i]==1时，
         * pre[i][0]=pre[i-1][1]+(i+1)  # 将[0,...,i-1]反转为1，操作1）将[0,...,i]反转为0
         * pre[i][1]=pre[i-1][1]
         * 当s[i]==0时，
         * pre[i][0]=pre[i-1][0]
         * pre[i][1]=pre[i-1][0]+(i+1) #
         * 2.3、求解
         */
        return 1L;
    }

    /**
     * @author LeetCode Official
     * 很妙，to be honest
     * @param s
     * @return
     */
    public long minimumCost(String s) {
        int n = s.length();
        long[][] suf = new long[n + 1][2];
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == '1') {
                suf[i][1] = suf[i + 1][1];
                suf[i][0] = suf[i + 1][1] + (n - i);
            } else {
                suf[i][1] = suf[i + 1][0] + (n - i);
                suf[i][0] = suf[i + 1][0];
            }
        }

        long[] pre = new long[2];  // 滚动数组
        long res = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                pre[0] = pre[1] + i + 1;
            } else {
                pre[1] = pre[0] + i + 1;
            }
            res = Math.min(res, Math.min(pre[0] + suf[i + 1][0], pre[1] + suf[i + 1][1]));
        }
        return res;
    }
}
