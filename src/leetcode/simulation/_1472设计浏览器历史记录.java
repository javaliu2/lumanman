package leetcode.simulation;

import java.util.ArrayList;
import java.util.List;

public class _1472设计浏览器历史记录 {

}

class BrowserHistory {
    /**
     * 使用arraylist进行页面的存储
     */
    int ptr = -1;  // 当前的页面
    List<String> pages;

    public BrowserHistory(String homepage) {
        pages = new ArrayList<>();
        pages.add(homepage);
        ptr = 0;
    }

    /**
     * 错误记录:
     * 针对测试用例:
     * ["BrowserHistory","visit","forward","forward","visit","visit","back","visit","visit"],
     * [["jrbilt.com"],["uiza.com"],[3],[3],["fveyl.com"],["hyhqfqf.com"],[3],["cccs.com"],["bivz.com"]]
     * visit日志输出:
     * jrbilt.com, uiza.com,
     * jrbilt.com, uiza.com, fveyl.com,
     * jrbilt.com, uiza.com, fveyl.com, hyhqfqf.com,
     * jrbilt.com, fveyl.com, cccs.com, # 可以很明显发现这里不对，原因是for循环中i < pages.size()，右侧是变的值随着remove元素之后
     * # i==1，删除uiza.com，[i+1,size-1]左移，元素列表为[jrbilt.com, fveyl.com, hyhqfqf.com]，这时size==3
     * # i==2，删除hyhqfqf.com，size=>2，这就结束了导致fveyl.com被跳过，没有删除
     * jrbilt.com, fveyl.com, cccs.com, bivz.com,
     */
    public void visit(String url) {
        // 删除[ptr+1, pages.size()-1]的页面
        /** wrong code, reason: not familiar with api of arraylist
         for(int i = ptr + 1; i < pages.size(); ++i) {
         pages.remove(i);
         }
         */
        /**
         * remove():
         * Removes the element at the specified position in this list (optional operation).
         * Shifts any subsequent elements to the left (subtracts one from their indices).
         * Returns the element that was removed from the list.
         */
        for (int i = pages.size() - 1; i > ptr; --i) {
            pages.remove(i);
        }
        // 设置url指向的页面
        pages.add(url);
        ptr = pages.size() - 1;
        for (String ele : pages) {
            System.out.print(ele + ", ");
        }
        System.out.println();
    }

    public String back(int steps) {
        // 最多能退回的页面数量就是ptr的值
        int num = Math.min(steps, ptr);
        ptr -= num;
        return pages.get(ptr);
    }

    public String forward(int steps) {
        int num = pages.size() - ptr - 1;
        num = Math.min(num, steps);
        ptr += num;
        return pages.get(ptr);
    }
}

/**
 * Your BrowserHistory object will be instantiated and called as such:
 * BrowserHistory obj = new BrowserHistory(homepage);
 * obj.visit(url);
 * String param_2 = obj.back(steps);
 * String param_3 = obj.forward(steps);
 */
