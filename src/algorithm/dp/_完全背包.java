package algorithm.dp;

/**
 *  capacity：背包容量
 *  w[i]：第 i 个物品的体积（重量）
 *  v[i]：第 i 个物品的价值
 * 【问题描述】：不同于01背包，这里每种物品数量无限，可以重复选取
 * 【返回】：所选物品体积不超过 capacity 的前提下，所能得到的最大价值和
 * 【迭代式】：dfs(i, c) = max{dfs(i - 1, c), dfs(i, c - w[i]) + v[i]}
 *                           不选             选，但是子问题的物品种类还是i种
 */
public class _完全背包 {

}
