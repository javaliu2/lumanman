package newcoder.exam;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 输入n，判断是否存在满足如下图形规律的图形，n为图形中元素的个数
 * 打印满足如下图形规律的
 * 01
 * 02 06
 * 03 04 05
 *
 * 01
 * 02 03
 *
 * 01
 * 02 09
 * 03 10 08
 * 04 05 06 07
 */
public class 打印合法的下三角图形 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        // 存储所有边长的图形中元素个数
        int cnt = 0;  // 元素个数
        Map<Integer, Integer> map = new HashMap<>();  // key是元素个数，值是边长
        for (int edge = 1; cnt <= 60; ++edge) {
            cnt = fun(edge);
            map.put(cnt, edge);
        }
        if (map.containsKey(n)) {
            System.out.println("yes");
            render(map.get(n));
        } else {
            System.out.println("no");
        }
    }

    static int fun(int edge) {
        if (edge <= 0) {
            return 0;
        }
        return edge * 2 - 1 + fun(edge - 2);
    }

    static void render(int edge) {
        int[][] graph = new int[edge][edge];
        int start_i = 0, start_j = 0;
        int num = 1;
        while (true) {
            // down
            int i, j;
            for (i = start_i; i < start_i + edge; ++i) {
                graph[i][start_j] = num++;
            }
            // right
            i = start_i + edge - 1;
            for (j = start_j + 1; j < start_j + edge; ++j) {
                graph[i][j] = num++;
            }
            // left-up
            j = start_j + edge - 1;
            int left_up_cnt = edge - 2;
            while (left_up_cnt-- > 0) {
                i--;
                j--;
                graph[i][j] = num++;
            }
            if (i + 1 < start_i + edge) {
                start_i = i + 1;
                start_j = j;
                edge -= 3;
            } else {
                break;
            }
        }
        // for (int[] arr : graph) {
        //     System.out.println(Arrays.toString(arr));
        // }
        for (int i = 0; i < graph.length; ++i) {
            for (int j = 0; j < graph.length; ++j) {
                int n = graph[i][j];
                if (n != 0) {
                    System.out.printf("%02d ", n);
                } else {
                    System.out.println();
                    break;
                }
            }
        }
    }
}
