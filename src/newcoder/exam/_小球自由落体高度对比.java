package newcoder.exam;


import java.util.*;

public class _小球自由落体高度对比 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();  // n个小球
        double[] coe = new double[] {7.0 / 8.0, 6.0 / 8.0, 5.0 / 8.0,
                4.0 / 8.0, 3.0 / 8.0, 2.0 / 8.0, 1.0 / 8.0
        };
        List<List<int[]>> ans = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            List<int[]> heights = func(coe[i], 100);
            ans.add(heights);
        }
        System.out.printf("=== %d个球的情况 ===", n);
        StringBuilder sb = new StringBuilder();
        List<int[]> heights_i = ans.get(0);
        // 遍历第一个球经历的高度，判断所有球是否有相同的高度
        for (int j = 0; j < heights_i.size(); ++j) {
            int[] heights_1_j = heights_i.get(j);  // 第1个球的第j次跳跃的（height，cnt）
            int height_1_j = heights_1_j[0];
            List<int[]> equal_heights = new ArrayList<>();
            equal_heights.add(heights_1_j);
            // 判断其他球是否有相同的高度
            for (int other_i = 1; other_i < n; ++other_i) {
                List<int[]> heights_other = ans.get(other_i);
                for (int other_j = 0; other_j < heights_other.size(); ++other_j) {
                    if (heights_other.get(other_j)[0] < height_1_j) {
                        break;
                    }
                    if (heights_other.get(other_j)[0] == height_1_j) {
                        // 将这个球的高度和次数加入
                        equal_heights.add(heights_other.get(other_j));
                    }
                }
            }
            if (equal_heights.size() == n) {
                // 所有球均有的相同高度
                int height = equal_heights.get(0)[0];
                String s = String.format("高度%d米:分别为", height);
                for (int equal_i = 0; equal_i < n; ++equal_i) {
                    int cnt = equal_heights.get(equal_i)[1];
                    s += String.format("(球%d的第%d次)", equal_i + 1, cnt);
                }
                s += "\n";
                sb.insert(0, s.toCharArray(), 0, s.length());
            }
            System.out.println(sb);
        }
    }
    // 需要保存高度和跳到该高度的次数
    // bug record: when height ==7, coe==7/8，那么计算结果还是7，出现了死循环
    static List<int[]> func(double coe, int t) {
        List<int[]> ans = new ArrayList<>();
        int cnt = 0, height = t;
        while (height > 5) {
            if (height < 20) {
                ans.add(new int[] {height, cnt});
            }
            height = (int)Math.ceil(height * coe);
            cnt++;
        }
        return ans;
    }
}
