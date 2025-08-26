package leetcode.array;

import java.util.ArrayList;
import java.util.List;

public class _3000对角线最长的矩形的面积 {
    public int areaOfMaxDiagonal(int[][] dimensions) {
        List<double[]> nums = new ArrayList<>();
        for (int[] dimension : dimensions) {
            int length = dimension[0], width = dimension[1];
            double len = Math.sqrt(length * length + width * width);  // 需要用double，否则有数据精度丢失的问题
            int area = length * width;
            nums.add(new double[]{len, area});
        }
        nums.sort((a, b) -> {
            if (a[0] < b[0]) {
                return 1;
            } else if (a[0] > b[0]) {
                return -1;
            } else {
                return Double.compare(b[1], a[1]);
            }
        });
        return (int) nums.get(0)[1];
    }
}
