package leetcode.greedy_policy;

import java.util.Arrays;

/**
 * 思路见: https://leetcode.cn/problems/eat-pizzas/solutions/3076629/tan-xin-pythonjavacgo-by-endlesscheng-fpjx/
 * 主打一个贪心
 */
class _3457吃披萨 {
    /**
     * @author 灵神
     * @param pizzas
     * @return
     */
    public long maxWeight(int[] pizzas) {
        Arrays.sort(pizzas);
        int n = pizzas.length;
        int days = n / 4;
        int odd = (days + 1) / 2;
        long ans = 0;
        for (int i = 0; i < odd; i++) {
            ans += pizzas[n - 1 - i];
        }
        for (int i = 0; i < days / 2; i++) {
            ans += pizzas[n - 2 - odd - i * 2];
        }
        return ans;
    }
}
