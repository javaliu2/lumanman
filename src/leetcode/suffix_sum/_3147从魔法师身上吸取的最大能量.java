package leetcode.suffix_sum;

public class _3147从魔法师身上吸取的最大能量 {
    private int[] energy;
    private int k;

    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        // 倒序计算，倒序的起点是n-k，不会是n-k-1，因为n-k-1+k=n-1
        // 所以是从n-k到n-1，每次逆序计算，步长k，累加遍历过程中的元素（后缀和），
        // 这个值表示，从该元素为起点到终点，间隔为k的所有元素的和，即吸取的能量
        // 卧槽，还得是思路。我这笨脑袋是不会想到这么巧妙的idea的
        int start = n - k, ans = Integer.MIN_VALUE;
        while (start <= n - 1) {
            int i = start, suffixSum = 0;
            while (i >= 0) {
                suffixSum += energy[i];
                ans = Math.max(ans, suffixSum);
                i -= k;
            }
            start++;
        }
        return ans;
    }

    /**
     * 这个思路不对，他是选择所有的能量，而不存在选或者不选
     * f(i): 以i为起点，所能吸取的最大能量
     */
    private int f(int i) {
        if (i >= energy.length) {
            return Integer.MIN_VALUE;
        }
        // 选择当前能量，f(i+k)+energy[i]
        // 不选择当前能量，f(i+1)
        int choose = f(i + 1);
        int un_choose = f(i + k);
        if (choose == Integer.MIN_VALUE) {
            if (un_choose == Integer.MIN_VALUE) {
                return energy[i];
            } else {
                return un_choose + energy[i];
            }
        }
        return Math.max(un_choose + energy[i], choose);
    }
}
