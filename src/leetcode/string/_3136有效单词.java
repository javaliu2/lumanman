package leetcode.string;

public class _3136有效单词 {
    public static boolean isValid(String word) {
        if (word.length() < 3) {
            return false;
        }
        boolean num_check = false, yuan_char_check = false, fu_char_check = false;
        for (char ch : word.toCharArray()) {
            if (ch == '@' || ch == '#' || ch == '$') {
                return false;
            }
            int num_right = (int) '9';
            int char_num = (int)ch;
            // 不用考虑数字存在与否，因为题目说不必包含所有这类字符，但是必须有一个元音字符和一个辅音字符
            if (char_num > num_right) {
               if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
                        || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U') {
                    yuan_char_check = true;
               } else {
                    fu_char_check = true;
               }
            }
        }
        return yuan_char_check && fu_char_check;
    }

    public static void main(String[] args) {
        String word = "AhI";
        boolean valid = isValid(word);
        System.out.println(valid);
        StringBuffer sb;
        StringBuilder sb2;
    }
}
