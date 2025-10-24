package codefun2000;
import java.util.*;
public class PDD多多的演唱会灯光方案 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int r = sc.nextInt();
            sc.nextLine();
            String lightSequence = sc.nextLine();
            int res = func(lightSequence, r);
            System.out.println(res);
        }
        sc.close();
    }

    private static int func(String s, int r) {
        // 寻找以i为起点的满足条件的最短连续序列
        // 找到之后，扩充序列长度，新增的字符只能是{D,M,T,A,O}之一
        int n = s.length();
        char[] target_char = new char[]{'D', 'M', 'T', 'A', 'O'};
        int ans = 0;
        for (int i = 0; i + 5 <= n; ++i) {
            int cnt = 0;  // 统计以i为起点的满足条件的连续序列的个数
            int[] cnts = new int[26];
            int help_cnt = 0;  // 统计辅助色灯光的数量(重复的也算)
            boolean find_vaild_sequence = false;
            int j;
            for (j = i; j < n; ++j) {
                char current_char = s.charAt(j);
                if (current_char == 'D' || current_char == 'M' || current_char == 'T' ||
                        current_char == 'A' || current_char == 'O') {
                    cnts[current_char - 'A']++;
                } else {
                    cnts[current_char - 'A']++;
                    help_cnt++;
                }
                boolean target_all_appear = true;
                for (char t : target_char) {
                    if (cnts[t - 'A'] < 1) {
                        target_all_appear = false;
                        break;
                    }
                }
                if (target_all_appear && help_cnt == r) {
                    cnt = 1;
                    find_vaild_sequence = true;
                    break;
                }
            }
            if (find_vaild_sequence) {
                // 扩充，新的字符只能是{D,M,T,A,O}之一
                j++;
                while (j < n) {
                    if (s.charAt(j) == 'D' || s.charAt(j) == 'M' || s.charAt(j) == 'T' || s.charAt(j) == 'A' || s.charAt(j) == 'O') {
                        cnt++;
                    } else {
                        break;
                    }
                    j++;
                }
            }
            ans += cnt;
        }
        return ans;
    }
}
