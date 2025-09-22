package codefun2000;

import java.util.*;

public class _2297 {
    // 用于存储布尔表达式的列表
    static List<String> myA;
    // 用于存储输入的布尔表达式
    static String[] express;
    // 用于存储字段名及其对应值的映射
    static Map<String, String> kV = new HashMap<>();
    // 表达式和数据的数量
    static int n = 0, m = 0;
    // 标记是否处理了括号
    static boolean flag = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt(); // 读取布尔表达式数量
        m = sc.nextInt(); // 读取替换数据数量
        sc.nextLine(); // 读取换行符
        express = new String[n]; // 初始化布尔表达式数组
        String temp; // 临时变量存储输入
        String[] tempS; // 临时数组用于存储替换数据

        // 数据初次加工：读取布尔表达式并进行格式化处理
        for (int i = 0; i < n; i++) {
            temp = sc.nextLine()
                    .replaceAll("'+", " ") // 去除多余的单引号
                    .replaceAll("\\(", "( ") // 在左括号后加空格
                    .replaceAll("\\)", " )") // 在右括号前加空格
                    .replaceAll("=", " = ") // 在等号两边加空格
                    .trim(); // 去除首尾空格
            express[i] = temp; // 存储格式化后的表达式
        }

        // 读取替换数据并存入映射
        for (int i = 0; i < m; i++) {
            tempS = sc.nextLine().split(" "); // 读取字段名和对应值
            kV.put(tempS[0], tempS[1]); // 将字段名和值存入映射
        }

        // 遍历每个布尔表达式进行计算
        for (String s : express) {
            flag = false; // 重置标记
            myA = new ArrayList<>(Arrays.asList(s.split("\\s+"))); // 将表达式按空格拆分为列表
            quEqual(); // 处理等号，替换字段值
            // 处理 AND 和 OR 运算，直到只剩一个结果
            while (myA.size() > 1) {
                if (flag) {
                    quAndOR(0, myA.size()); // 如果处理过括号，则直接处理 AND 和 OR
                } else {
                    quKAndOR(); // 处理括号
                }
            }

            // 根据结果输出网络健康状态
            if (myA.get(0).equals("1")) {
                System.out.println(0); // 健康
            } else {
                System.out.println(1); // 不健康
            }
        }
    }

    // 处理括号内的 AND 和 OR 运算
    private static void quKAndOR() {
        String temp; // 临时变量
        int z = 0, you = myA.size() + 1; // 左右括号的位置
        for (int i = myA.size() - 1; i >= 0; i--) {
            temp = myA.get(i);
            if (temp.equals(")")) {
                you = Math.min(you, i); // 右括号的位置
            }
            if (temp.equals("(")) {
                z = i; // 左括号的位置
                break;
            }
        }
        if (you == myA.size() + 1) {
            flag = true; // 如果没有括号，标记为 true
            you = myA.size(); // 设置右边界为当前大小
        } else {
            myA.remove(you); // 移除右括号
            myA.remove(z); // 移除左括号
            you--; // 更新右边界
        }
        quAndOR(z, you); // 处理括号内的运算
    }

    // 处理 AND 和 OR 运算
    private static void quAndOR(int z, int you) {
        String temp1, op, temp2; // 临时变量
        // 处理 AND 运算
        for (int i = z + 1; i < you - 1; i++) {
            op = myA.get(i);
            if (op.equals("AND")) {
                temp1 = myA.get(i - 1); // 左操作数
                temp2 = myA.get(i + 1); // 右操作数

                myA.remove(i + 1); // 移除右操作数
                myA.remove(i); // 移除 AND 运算符
                // 根据操作数计算结果
                if (temp1.equals("0") || temp2.equals("0")) {
                    op = "0"; // 只要有一个为 0，结果为 0
                } else {
                    op = "1"; // 都为 1，结果为 1
                }
                myA.set(i - 1, op); // 将结果放回到列表中
                i--; // 调整索引
                you -= 2; // 更新右边界
            }
        }
        // 处理 OR 运算
        for (int i = z + 1; i < you - 1; i++) {
            op = myA.get(i);
            if (op.equals("OR")) {
                temp1 = myA.get(i - 1);
                temp2 = myA.get(i + 1);

                myA.remove(i + 1);
                myA.remove(i);
                // 根据操作数计算结果
                if (temp1.equals("1") || temp2.equals("1")) {
                    op = "1"; // 只要有一个为 1，结果为 1
                } else {
                    op = "0"; // 都为 0，结果为 0
                }
                myA.set(i - 1, op);
                i--;
                you -= 2; // 更新右边界
            }
        }
    }

    // 判断是否为合法的变量名
    private static boolean zi(String temp) {
        if (temp.equals("=") || temp.equals("AND") || temp.equals("OR") || temp.equals("(") || temp.equals(")")) {
            return false; // 这些符号不算变量名
        }
        return true; // 合法变量名
    }

    // 替换字段值并去除等号
    private static void quEqual() {
        String temp1, op, temp2; // 临时变量
        // 进行替换操作
        for (int i = 0; i < myA.size(); i++) {
            temp1 = myA.get(i);
            if (kV.containsKey(temp1)) {
                myA.set(i, kV.get(temp1)); // 替换字段名为对应值
            }
        }
        // 去除等号并计算结果
        for (int j = 0; j < myA.size() - 2; j++) {
            temp1 = myA.get(j);
            op = myA.get(j + 1);
            temp2 = myA.get(j + 2);

            if (op.equals("=") && zi(temp1) && zi(temp2)) {
                // 如果是条件1 = 条件2
                myA.remove(j + 2); // 移除右操作数
                myA.remove(j + 1); // 移除等号
                // 根据条件比较结果
                if (temp1.equals(temp2)) {
                    myA.set(j, "1"); // 相等返回 1
                } else {
                    myA.set(j, "0"); // 不相等返回 0
                }
            }
        }
    }
}
