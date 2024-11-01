package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class _2850 {
    public int minimumMoves(int[][] grid) {
        List<int[]> more = new ArrayList<int[]>();
        List<int[]> less = new ArrayList<int[]>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (grid[i][j] > 1) {
                    for (int k = 2; k <= grid[i][j]; ++k) {
                        more.add(new int[]{i, j});
                    }
                } else if (grid[i][j] == 0) {
                    less.add(new int[]{i, j});
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        do {
            int steps = 0;
            for (int i = 0; i < more.size(); i++) {
                steps += Math.abs(less.get(i)[0] - more.get(i)[0]) + Math.abs(less.get(i)[1] - more.get(i)[1]);
            }
            ans = Math.min(ans, steps);
            for (int[] ele : more)
                System.out.print(Arrays.toString(ele));
            System.out.println();
        } while (nextPermutation(more));
        return ans;
    }

    public boolean nextPermutation(List<int[]> more) {
        int p = -1;
        for (int i = 0; i < more.size() - 1; i++) {
            if (isLess(more.get(i), more.get(i + 1))) {
                p = i;
            }
        }
        if (p == -1) {
            return false;
        }
        int q = -1;
        for (int j = p + 1; j < more.size(); j++) {
            if (isLess(more.get(p), more.get(j))) {
                q = j;
            }
        }
        Collections.swap(more, p, q);
        int i = p + 1, j = more.size() - 1;
        while (i < j) {
            Collections.swap(more, i, j);
            i++;
            j--;
        }
        return true;
    }

    public boolean isLess(int[] pair1, int[] pair2) {
        return pair1[0] < pair2[0] || (pair1[0] == pair2[0] && pair1[1] < pair2[1]);
    }


    public static void main(String[] args) {
        _2850 obj = new _2850();
        int[][] grid = {{1,3,0},{1,0,0},{1,0,3}};
//        System.out.println(grid.toString());
        int res = obj.minimumMoves(grid);
        System.out.println(res);
    }
}



