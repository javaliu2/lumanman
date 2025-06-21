package newcoder.exam;

import java.util.Scanner;

public class _1_十进制IP地址转化为Hex字符串 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String address = in.nextLine();
            // System.out.print(address);
            String[] add = address.split("\\.");  // 注意需要使用\\
            // for (String c : add) {
            //     System.out.print(c + " ");
            // }
            int length = add.length;
            if (length != 4) {
                System.out.println("X");
                continue;
            }
            int[] nums = new int[length];
            int i;
            for (i = 0; i < length; ++i) {
                // 处理不是数字的情况（没有写这个的时候，通过率75%）
                try {
                    int num = Integer.parseInt(add[i]);
                    if (num < 0 || num > 255) {
                        System.out.println("X");
                        break;
                    }
                    nums[i] = num;
                } catch (NumberFormatException e) {
                    System.out.println("X");
                    break;
                }
            }
            if (i < length) {
                continue;
            }
            for (i = 0; i < length; ++i) {
                int num = nums[i];
                String res = decimal2Hex(num);
                if (i + 1 < length) {
                    System.out.print(res);
                } else {
                    System.out.println(res);
                }
            }
        }
    }

    static String decimal2Hex(int num) {
        final int m = 16;
        int remainder;
        char[] mapping = new char[16];
        mapping[0] = '0';
        mapping[1] = '1';
        mapping[2] = '2';
        mapping[3] = '3';
        mapping[4] = '4';
        mapping[5] = '5';
        mapping[6] = '6';
        mapping[7] = '7';
        mapping[8] = '8';
        mapping[9] = '9';
        mapping[10] = 'A';
        mapping[11] = 'B';
        mapping[12] = 'C';
        mapping[13] = 'D';
        mapping[14] = 'E';
        mapping[15] = 'F';
        StringBuffer sb = new StringBuffer();
        while (num != 0) {
            remainder = num % m;
            sb.append(mapping[remainder]);
            num /= m;
        }
        // 题目要求两位
        if (sb.length() == 1) {
            sb.append('0');
        } else if (sb.length() == 0) {  // 如果num就是0的话，需要append两个0
            sb.append('0');
            sb.append('0');
        }
        return sb.reverse().toString();
    }
}
