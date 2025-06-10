package javaguide.collection;

import java.util.*;

public class SetTest {
    public static void main(String[] args) {
        System.out.println("== HashSet 示例 ==");
        Set<String> hashSet = new HashSet<>();
        hashSet.add("banana");
        hashSet.add("apple");
        hashSet.add("cherry");
        hashSet.add("apple"); // 重复元素不会添加
        System.out.println(hashSet); // 无序，输出顺序不固定

        System.out.println("\n== LinkedHashSet 示例 ==");
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("banana");
        linkedHashSet.add("apple");
        linkedHashSet.add("cherry");
        linkedHashSet.add("apple");
        System.out.println(linkedHashSet); // 保持插入顺序

        System.out.println("\n== TreeSet 示例 ==");
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("banana");
        treeSet.add("apple");
        treeSet.add("cherry");
        treeSet.add("apple");
        System.out.println(treeSet); // 自动排序，按字典顺序

        // TreeSet 不能加 null，否则抛出 NullPointerException
        try {
            treeSet.add(null); // 会抛异常
        } catch (Exception e) {
            System.out.println("TreeSet 不能添加 null：" + e.getMessage());
        }
    }
}
