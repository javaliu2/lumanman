package codefun2000;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class _P2358 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        int x = sc.nextInt();
        int y = sc.nextInt();
        int[] goods = new int[m];
        int[] cars = new int[n];
        for (int i = 0; i < m; ++i) {
            goods[i] = sc.nextInt();
        }
        for (int i = 0; i < n; ++i) {
            cars[i] = sc.nextInt();
        }
        int res = fun(goods, cars, x, y);
        System.out.println(res);
        sc.close();
    }

    /**
     * AC11, TIMEOUT 5
     * @param goods
     * @param cars
     * @param num
     * @param up_weight
     * @return
     */
    static int fun(int[] goods, int[] cars, int num, int up_weight) {
        int cnt = 0;
        boolean[] goods_visited = new boolean[goods.length];
        boolean[] cars_visited = new boolean[goods.length];
        // 不增重之前，叉车可以运载的货物数量
        for (int i = 0; i < cars.length; ++i) {
            int car = cars[i];
            for (int j = 0; j < goods.length; ++j) {
                if (!goods_visited[j] && car >= goods[j]) {
                    goods_visited[j] = true;
                    cars_visited[i] = true;
                    cnt++;
                    break;
                }
            }
        }
        // 增重
        for (int i = 0; i < cars.length; ++i) {
            if (cars_visited[i]) {
                continue;
            }
            int car = cars[i];
            int new_car = car + up_weight;
            for (int j = 0; j < goods.length; ++j) {
                if (!goods_visited[j] && new_car >= goods[j] && num > 0) {
                    goods_visited[j] = true;
                    cars_visited[i] = true;
                    cnt++;
                    num--;
                    break;
                }
            }
        }
        return cnt;
    }
}
class Tazige {
    static int m; // 货物的数量
    static int n; // 卡车的数量
    static int x; // 拖斗的数量
    static int y; // 每个拖斗的载重量
    static int[] weights; // 每个货物的重量
    static int[] loads; // 每个卡车的载重量

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // 创建输入扫描器
        m = sc.nextInt(); // 输入货物数量
        n = sc.nextInt(); // 输入卡车数量
        x = sc.nextInt(); // 输入拖斗数量
        y = sc.nextInt(); // 输入每个拖斗的载重
        weights = new int[m]; // 初始化货物重量数组
        for (int i = 0; i < m; i++) weights[i] = sc.nextInt(); // 输入每个货物的重量
        loads = new int[n]; // 初始化卡车载重量数组
        for (int i = 0; i < n; i++) loads[i] = sc.nextInt(); // 输入每个卡车的载重
        sc.close(); // 关闭扫描器

        // 将货物按重量升序排序
        Arrays.sort(weights);
        // 将卡车按载重量升序排序
        Arrays.sort(loads);

        // 使用贪心算法和二分查找
        int left = 0, right = m; // 设置二分查找的范围
        while(left < right) {
            int mid = (left + right + 1) >> 1; // 计算中间值
            if(check(mid)) { // 检查是否能运输mid个集装箱
                left = mid; // 可以运输，尝试增加集装箱数量
            } else {
                right = mid - 1; // 不能运输，减少集装箱数量
            }
        }
        System.out.println(left); // 输出最大可运输的集装箱数量
    }

    // 检查在当前设定的集装箱数量下是否能运输
    public static boolean check(int mid) {
        if (mid > n) return false; // 如果货物数量大于卡车数量，返回false

        // TreeMap自动排序（默认按key升序排序），存储卡车载重量及其数量
        TreeMap<Integer, Integer> map = new TreeMap<>();
        // 将可以使用的卡车载重存入map
        for (int i = n - mid; i < n; i++)
            map.compute(loads[i], (k, v) -> (v == null ? 1 : v + 1)); // 更新卡车数量

        // 从重到轻遍历货物
        int t = x; // 当前可用的拖斗数量
        for (int i = mid - 1; i >= 0; i--) {
            int weight = weights[i]; // 当前货物重量
            Map.Entry<Integer, Integer> en = map.lastEntry(); // 获取map中载重量最大的卡车
            if (en.getKey() >= weight) { // 如果卡车可以运输该货物
                map.compute(en.getKey(), (k, v) -> (v <= 1 ? null : v - 1)); // 更新卡车数量
            } else if (t > 0 && (en = map.ceilingEntry(weight - y)) != null) { // 否则，尝试使用拖斗增强
                t--; // 使用一个拖斗
                map.compute(en.getKey(), (k, v) -> (v <= 1 ? null : v - 1)); // 更新卡车数量
            } else {
                return false; // 如果都不满足，返回false
            }
        }
        return true; // 能够运输，返回true
    }
}



