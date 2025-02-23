package algorithm.list;

/**
 * Java实现简单跳表
 * 跳表为了解决单链表查找需要O(n)时间复杂度而提出，
 * 其具有层(level)的结构，所谓层就是选取原始链表中的一些元素作为该层链表以减少比较查找的次数
 * 采取用空间换取时间的策略，插入、删除元素均需要维护每一层的后继节点
 * 失败，整不出来。太难了。
 */
public class SkipList {
    private int maxLevel;
    private SkipNode head;
    public SkipList() {
        // 设置头节点
        maxLevel = 32;
        head = new SkipNode();
        head.nexts = new SkipNode[maxLevel];
    }

    public boolean search(int target) {
        return true;
    }

    public void add(int num) {

    }

    public boolean erase(int num) {
        return true;
    }
}

/**
 * 先实现data类型是int的版本
 */
class SkipNode {
    int data;
    SkipNode[] nexts;
}
