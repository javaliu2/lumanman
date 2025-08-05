package newcoder.exam;

/**
 * 给定一个数组，数组元素表示水管长度，可以任意切割水管，
 * 求切割后水管的数量至少为k时的最大长度
 */
public class _满足条件的最大长度 {


    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param nums int整型一维数组 数组中的每个元素表示每根水管的长度
     * @param k    int整型 需要切割出的水管数量
     * @return int整型
     */
    public int maxLength(int[] nums, int k) {
        // binary find ans
        int ans = 1, cnt = 0;
        while (true) {
            cnt = 0;
            for (int i = 0; i < nums.length; ++i) {
                cnt += nums[i] / ans;
            }
            if (cnt > k) {
                ans++;
            } else {
                if (cnt < k) {
                    ans--;
                }
                break;
            }
        }
        return ans;
    }
}
