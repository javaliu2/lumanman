package newcoder.exam.meituan;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class M8D9_1小美的字符串 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();  // 字符串长度
        int m = sc.nextInt();  // 操作次数
        sc.nextLine();  // 读取换行
        String s = sc.nextLine();
        // System.out.println(s);
        List<Operation> operations = new ArrayList<>();
        while (m-- > 0) {
            int op = sc.nextInt();
            char letter1 = sc.next().charAt(0);
            char letter2 = sc.next().charAt(0);
            Operation operation = new Operation(op, letter1, letter2);
            operations.add(operation);
        }
        // System.out.println(operations);
        String res = fun(s, operations);
        System.out.println(res);
    }

    static String fun(String s, List<Operation> operations) {
        char[] chs = s.toCharArray();
        for (Operation operation : operations) {
            if (operation.op == 1) {
                // 将符合要求的字母转换为大写
                for (int i = 0; i < chs.length; ++i) {
                    char ch = chs[i];
                    if (ch >= operation.letter1 && ch <= operation.letter2) {
                        char upperChar = Character.toUpperCase(ch);
                        chs[i] = upperChar;
                    }
                }
            } else {
                // 将符合要求的字母转换为小写
                for (int i = 0; i < chs.length; ++i) {
                    char ch = chs[i];
                    if (ch >= operation.letter1 && ch <= operation.letter2) {
                        char lowerChar = Character.toLowerCase(ch);
                        chs[i] = lowerChar;
                    }
                }
            }
        }
//        return chs.toString();  // ERROR, 因为toString返回的是对象的表示，而不是将chs转化为字符串
        return String.valueOf(chs);
    }
}

class Operation {
    int op;
    char letter1;
    char letter2;

    Operation(int op, char letter1, char letter2) {
        this.op = op;
        this.letter1 = letter1;
        this.letter2 = letter2;
    }
}
