package leetcode.simulation;

import java.util.ArrayList;
import java.util.List;

public class _1656设计有序流 {

}

class OrderedStream {
    /**
     * 使用数组int[n]记录第i位是否有值，通过Map<int,String>记录key(索引)对应的值;
     * 或者直接使用String[]存储value
     */
    private int ptr;
    private int n;
    private String[] arr;

    public OrderedStream(int n) {
        this.n = n;
        ptr = 1;
        arr = new String[n + 1];
    }

    public List<String> insert(int idKey, String value) {
        List<String> res = new ArrayList<>();
        // 先存value
        arr[idKey] = value;
        // 然后判断ptr?=idKey
        if (idKey == ptr) {
            // 返回从索引ptr连续的值列表
            while (ptr <= n && arr[ptr] != null) {
                res.add(arr[ptr]);
                ptr++;  // 更新ptr
            }
        }
        // 返回空列表
        return res;
    }
}

/**
 * Your OrderedStream object will be instantiated and called as such:
 * OrderedStream obj = new OrderedStream(n);
 * List<String> param_1 = obj.insert(idKey,value);
 */