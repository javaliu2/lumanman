package leetcode.fancy_feature;

import java.util.ArrayList;
import java.util.List;

public class _2296设计一个文本编辑器 {
}


/**
 * @author 灵神
 * 奇淫技巧
 */
class TextEditor {

    private final StringBuilder left = new StringBuilder(); // 光标左侧字符
    private final StringBuilder right = new StringBuilder(); // 光标右侧字符

    public void addText(String text) {
        left.append(text); // 入栈
    }

    public int deleteText(int k) {
        k = Math.min(k, left.length());
        left.setLength(left.length() - k); // 出栈
        return k;
    }

    public String cursorLeft(int k) {
//        while (k > 0 && !left.isEmpty()) {  // Cannot resolve method 'isEmpty' in 'StringBuilder',可能是JDK版本问题
        while (k > 0 && !(left.length() == 0)) {
            right.append(left.charAt(left.length() - 1)); // 左手倒右手
            left.setLength(left.length() - 1);
            k--;
        }
        return text();
    }
    public String cursorRight(int k) {
        while (k > 0 && !(right.length() == 0)) {
            left.append(right.charAt(right.length() - 1)); // 右手倒左手
            right.setLength(right.length() - 1);
            k--;
        }
        return text();
    }

    private String text() {
        // 光标左边至多 10 个字符
        return left.substring(Math.max(left.length() - 10, 0));
    }
}

class TextEditor2 {
    /**
     * 使用arraylist保存文本
     * 失败，太费劲，条件判断的话。还是需要思路啊，不是死脑筋。
     */
    List<String> texts;
    int ptr = -1, pos = -1;  // 需要建立二级索引，ptr存储指向哪一个文本，pos保存该文本的位置

    public TextEditor2() {
        texts = new ArrayList<>();
    }

    public void addText(String text) {

    }

    public int deleteText(int k) {
        // 计算len
        int len = pos + 1;
        int t = ptr - 1;
        while (t >= 0) {
            len += texts.get(t).length();
            t--;
        }
        if (len == 0) {
            return 0;
        }
        int delete_num = Math.min(len, k), remain_num;
        // 计算删除后光标的位置，即ptr和pos的值，然后更新texts
        remain_num = pos + 1 - delete_num;
        while (remain_num < 0) {
            ptr--;
            pos = texts.get(ptr).length() - 1;
            remain_num = pos + 1 - remain_num;
        }
        return -1;
    }

    public String cursorLeft(int k) {
        return "";
    }

    public String cursorRight(int k) {
        return "";
    }
}

/**
 * Your TextEditor object will be instantiated and called as such:
 * TextEditor obj = new TextEditor();
 * obj.addText(text);
 * int param_2 = obj.deleteText(k);
 * String param_3 = obj.cursorLeft(k);
 * String param_4 = obj.cursorRight(k);
 */