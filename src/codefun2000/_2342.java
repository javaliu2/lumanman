package codefun2000;

import java.util.Scanner;

public class _2342 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        char[] chs = s.toCharArray();
        int score = 0;
        for (int i = 0; i < chs.length; ++i) {
            char ch = chs[i];
            if (ch == 'r') {
                score += 1;
            } else if (ch == 'g') {
                score += 2;
            } else {
                score += 3;
            }
            // 判断i之前是否有连续的相同球
            int j;
            for (j = i - 1; j >= 0; --j) {
                if (chs[j] != ch) {
                    break;
                }
            }
            int cnt = i - j - 1;
            score += cnt;
        }
        System.out.println(score);
        sc.close();
    }
}
