package leetcode.binary_tree;

public class _208实现Trie_前缀树 {

}
class Trie {
    private Node root;
    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        char[] chs = word.toCharArray();
        Node p = root;
        for (int i = 0; i < chs.length; ++i) {
            int idx = chs[i] - 'a';
            if (p.children[idx] == null) {
                Node child = new Node();
                p.children[idx] = child;
                p = child;
            } else {
                p = p.children[idx];
            }
        }
        p.isEnd = true;
    }
    // believe in myself.
    public boolean search(String word) {
        char[] chs = word.toCharArray();
        Node p = root;
        for (int i = 0; i < chs.length; ++i) {
            int idx = chs[i] - 'a';
            if (p.children[idx] == null) {
                return false;
            } else {
                p = p.children[idx];
            }
        }
        return p.isEnd;  // 也有可能是某个字符串的前缀，所以需要判断是否是完整的字符串
    }

    public boolean startsWith(String prefix) {
        char[] chs = prefix.toCharArray();
        Node p = root;
        for (int i = 0; i < chs.length; ++i) {
            int idx = chs[i] - 'a';
            if (p.children[idx] == null) {
                return false;
            } else {
                p = p.children[idx];
            }
        }
        return true;
    }
}
class Node {
    boolean isEnd;
    Node[] children;
    Node() {
        isEnd = false;
        children = new Node[26];
    }
}
/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
