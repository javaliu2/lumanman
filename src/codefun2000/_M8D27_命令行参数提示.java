package codefun2000;

import org.junit.Test;

import java.util.*;

public class _M8D27_命令行参数提示 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int min_dist = sc.nextInt();
        int sub_command_num = sc.nextInt();
        sc.nextLine();
        List<String> commands = new ArrayList<>();
        for (int i = 0; i < sub_command_num; ++i) {
            commands.add(sc.nextLine());
        }
        String user_command = sc.nextLine();
        // 1、用户输入是否出现在子命令列表
        for (String sub_command : commands) {
            if (sub_command.equals(user_command)) {
                System.out.println(user_command);
                return;
            }
        }
        // 2、计算满足最小距离min_dist的子命令
        List<String[]> vaild_commands = new ArrayList<>();
        for (String sub_command : commands) {
            int dist = getDist_Gbro(sub_command, user_command);
            if (dist <= min_dist) {
                String[] vaild_command = new String[]{java.lang.String.valueOf(dist), sub_command};
                vaild_commands.add(vaild_command);
            }
        }
        vaild_commands.sort((a, b) -> {
            int aa = Integer.parseInt(a[0]), bb = Integer.parseInt(b[0]);
            if (aa == bb) {
                return a[1].compareTo(b[1]);  // 整数的字符串形式表达的比较，10会排在2前面，与目的不符
            }
            return aa - bb;
        });
        if (vaild_commands.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < vaild_commands.size(); ++i) {
                if (i + 1 == vaild_commands.size()) {
                    System.out.println(vaild_commands.get(i)[1]);
                } else {
                    System.out.print(vaild_commands.get(i)[1] + " ");
                }
            }
        }
        sc.close();
    }

    static int getDist(String target_command, String user_command) {
        // dp[i][j]: 字符串s1中前i个字符转变为字符串s2中前j个字符所需最少操作次数
        int n = target_command.length(), m = user_command.length();
        int[][] dp = new int[m][n];
        // 1、初始化
        for (int j = 0; j < n; ++j) {
            if (j == 0) {
                if (user_command.charAt(0) == target_command.charAt(j)) {
                    dp[0][j] = 0;
                } else {
                    dp[0][j] = 1;  // 替换操作
                }
            } else {
                dp[0][j] = dp[0][j - 1] + 1;  // 只能是插入操作
            }
        }
        for (int i = 0; i < m; ++i) {
            if (i == 0) {
                if (user_command.charAt(i) == target_command.charAt(0)) {
                    dp[i][0] = 0;
                } else {
                    dp[i][0] = 1;  // 替换操作
                }
            } else {
                dp[i][0] = dp[i - 1][0] + 1;  // 只能是删除操作, 不完全正确。
                // 针对aaprint -> print, 当i==2时，s[i]==t[0]，那么dp[i][0]=dp[i-1][0]而不需要加一，
                // 所以正确做法还是声明[m+1][n+1]的数组，初始化的时候包括s和t为空串的情况，这种情况下，才是直接加上一个
            }
        }
        // 2、计算
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                if (user_command.charAt(i) == target_command.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1], // 替换
                            Math.min(dp[i - 1][j], // 删除
                                    dp[i][j - 1]) // 插入
                    ) + 1;
                }
            }
        }
        for (int[] arr : dp) {
            System.out.println(Arrays.toString(arr));
        }
        return dp[m - 1][n - 1];
    }

    static int getDist_Gbro(String target_command, String user_command) {
        int n = target_command.length(), m = user_command.length();
        int[][] dp = new int[m + 1][n + 1];

        // 初始化
        for (int i = 0; i <= m; ++i) dp[i][0] = i;
        for (int j = 0; j <= n; ++j) dp[0][j] = j;

        // 动态规划
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (user_command.charAt(i - 1) == target_command.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])
                    ) + 1;
                }
            }
        }
//        for (int[] arr : dp) {
//            System.out.println(Arrays.toString(arr));
//        }
        return dp[m][n];
    }

    @Test
    public void testGetDist() {
        String s1 = "output", s2 = "print";
        int dist = getDist(s1, s2);
        int distGbro = getDist_Gbro(s1, s2);
        System.out.println(dist);
        System.out.println(distGbro);
    }
}
