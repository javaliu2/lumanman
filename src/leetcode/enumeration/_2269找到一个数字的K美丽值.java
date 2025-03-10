package leetcode.enumeration;

public class _2269找到一个数字的K美丽值 {
    public int divisorSubstrings(int num, int k) {
        /**
         * 枚举逐一判断
         */
        char[] chs = Integer.toString(num).toCharArray();
        int ans = 0;
        // 使用滑动窗口计算k范围内的值
        int value = 0, base = 1;
        for (int j = k - 1; j >= 0; --j) {
            value += base * (int) (chs[j] - '0');
            base *= 10;
        }
        base /= 10;
        for (int i = 0; i + k < chs.length; ++i) {
            if (value != 0 && num % value == 0) {
                ans++;
            }
            // 去头加尾
//            value -= base * (int) (chs[i] - '0');  // 这里又错了，这计算的是字符对应的ASCII值
//            value += (int) (chs[i + k] - '0');  // 其实也不用这样写，直接chs[i]就可
            // 忽略i+1位置的数，现在变成第一个数，因此它的权变为base
            value -= base * Character.getNumericValue(chs[i]);
            if (k >= 2) {
                value *= 10;
            }
            value += Character.getNumericValue(chs[i + k]);
        }
        // 缝缝缝缝补补，阿西吧
        if (value != 0 && num % value == 0) {
            ans++;
        }
        return ans;
    }

    public static void main(String[] args) {
        _2269找到一个数字的K美丽值 obj = new _2269找到一个数字的K美丽值();
        int num = 240, k = 2;
        obj.divisorSubstrings(num, k);
        int a = 10 % 0;  // 抛出异常ArithmeticException
    }
}
