package newcoder.exam;

import java.util.Scanner;

/**
 * 2.
 * 多多的字符变换
 * 多多君最近在研究字符串之间的变换，可以对字符串进行若干次变换操作:
 * 1) 交换任意两个相邻的字符，代价为0。
 * 2) 将任意一个字符a修改成字符b，代价为 |a - b|（绝对值）。
 * 现在有两个长度相同的字符串X和Y，多多君想知道，如果要将X和Y变成两个一样的字符串，需要的最少的代价之和是多少。
 */
public class _2_多多的字符变换 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        char[] ch = in.nextLine().toCharArray();
        char[] ch2 = in.nextLine().toCharArray();
        // 计算 ch 和 ch2 中相等的字符的个数
        // 相等的字符一定可以通过“交换”操作对齐
        int[] cnt = new int[26];
        int[] cnt2 = new int[26];
        fun_cnt_char(ch, cnt);
        fun_cnt_char(ch2, cnt2);
        for (int i = 0; i < 26; ++i) {
            if (cnt[i] > 0 && cnt2[i] > 0) {
                int min = Math.min(cnt[i], cnt2[i]);
                cnt[i] -= min;
                cnt2[i] -= min;
            }
        }
        // for (int i = 0; i < 26; ++i) {
        //     if (cnt[i] > 0) {
        //         System.out.println((char)(i+'a'));
        //     }
        //     if (cnt2[i] > 0) {
        //         System.out.println((char)(i+'a'));
        //     }
        // }
        // 剩下的字符就只能通过“修改”操作来对齐了
        int cost = 0;
        for (int i = 0; i < 26; ++i) {
            if (cnt[i] > 0) {
                // 寻找cnt2中下标最小的进行匹配，要把cnt[i]消耗完
                for (int j = 0; j < 26; ++j) {
                    if (cnt2[j] > 0) {
                        int min = Math.min(cnt[i], cnt2[j]);
                        cnt[i] -= min;
                        cnt2[j] -= min;
                        cost += min * Math.abs(i - j);
                    }
                    if (cnt[i] == 0) {
                        break;
                    }
                }
            }
        }
        System.out.println(cost);
    }

    static void fun_cnt_char(char[] ch, int[] cnt) {
        for (char c : ch) {
            cnt[c - 'a']++;
        }
    }
}
