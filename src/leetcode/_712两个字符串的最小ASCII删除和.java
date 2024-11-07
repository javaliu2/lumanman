package leetcode;

import org.junit.Test;

import java.util.Arrays;

public class _712两个字符串的最小ASCII删除和 {
    @Test
    public void test(){
        int a = (int)'a';
        System.out.println(a);
    }
    String s;
    String t;
    int[][] memo;

    int dfs(int i, int j) {
        if (i < 0) {
            // [0,...,j]的ASCII和
            int sum = 0;
            for (int k = 0; k <= j; k++) {
                sum += (int)t.charAt(k);
            }
            return sum;
        }
        if (j < 0) {
            int sum = 0;
            for (int k = 0; k <= i; k++) {
                sum += (int)s.charAt(k);
            }
            return sum;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        if (s.charAt(i) == t.charAt(j)) {
            int res = dfs(i - 1, j - 1);
            memo[i][j] = res;
            return res;
        }
        int res = Math.min(dfs(i, j - 1) + (int)t.charAt(j), dfs(i - 1, j) + (int)s.charAt(i));
        memo[i][j] = res;
        return res;
    }

    public int minimumDeleteSum(String word1, String word2) {
        this.s = word1;
        this.t = word2;
        int m = s.length(), n = t.length();
        memo = new int[m][n];
        for (int[] arr : memo) {
            Arrays.fill(arr, -1);
        }
        return dfs(m - 1, n - 1);
    }
}
