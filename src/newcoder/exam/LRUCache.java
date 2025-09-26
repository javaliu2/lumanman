package newcoder.exam;
import java.util.*;
/**
 * 实现一个LRU（least recently used），最近最少使用
 * 1、需求分析
 * 1）get(K key): 根据key获取value，并且将<key, value>加入队列尾部
 * 2）put(K key, V value): 放置<key, value>, 如果key已存在，那么更新value。另外，将<key, value>置于队列尾部
 * 3）size(): 获取LRUCache中元素的个数
 * 4）remove(K key): 删除键为key的键值对
 * 2、设计
 * 使用一个map和一个linkedlist存储键值对，假设key唯一确定一个键值对，
 * map<key, index of pair<key, value> in linkedlist>
 * linkedlist存储键值对
 */
public class LRUCache {
}

/**
 * 失败的实践，需要维护map中的映射
 */
class MyLRUCache {

    public static class Pair<K, V> {
        private K key;
        private V value;
        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
    public static class LRUCache<K, V> {
        private Map<K, Integer> map;
        private LinkedList<Pair<K, V>> list;
        private int capacity;
        private int size;
        public LRUCache(int capacity) {
            map = new HashMap<>();
            list = new LinkedList<>();
            this.capacity = capacity;
            this.size = 0;
        }

        /**
         * 判断当前元素个数 是否 大于等于容量，如果是的话，将list第一个元素移除，同时删除map中对应的映射，
         * 元素个数减1，再进行下面的逻辑处理。
         *
         * 如果map中有key,那么取出<key, value>在list中的索引，删除键值对
         * 将新的键值对加入list尾部，更新map中该键值对的索引
         *
         * 没有的话，将键值对加入list尾部，map中新增key和索引的映射，元素个数加1
         * @param key
         * @param value
         */
        public void put(K key, V value) {
            if (size >= capacity) {
                K key1 = list.get(0).getKey();
                map.remove(key1);
                list.removeFirst();
                // 这里有问题，如果删除了第一个元素，那么map中的映射关系就错位了
                size--;
                // 所以在这里，主动更新map中的映射关系
                for (int i = 0; i < list.size(); ++i) {
                    map.put(list.get(i).getKey(), i);
                }
            }
            Integer idx = map.get(key);
            if (idx == null) {
                size++;
            } else {
                list.remove(idx.intValue());  // 这里remove，那么又出现索引错位的现象
            }
            Pair<K, V> pair = new Pair<>(key, value);
            list.add(pair);
            int t = list.size() - 1;
            map.put(key, t);
        }

        /**
         * 获取键为key的value，有的话返回value，并且将键值对置于list尾部，更新map中的映射
         * @param key
         * @return
         */
        public V get(K key) {
            Integer idx = map.get(key);
            if (idx == null) {
                return null;
            }
            Pair<K, V> pair = list.get(idx);
            V res = pair.getValue();
            list.remove(idx.intValue());
            list.add(pair);
            map.put(key, list.size() - 1);
            return res;
        }
        public void remove(K key) {
            Integer idx = map.get(key);
            if (idx == null) {
                return;
            }
            list.remove(idx.intValue());
            size--;
            map.remove(key);
        }
        public int getSize() {
            return size;
        }
        public void show() {
            for (Pair<K, V> pair : list) {
                System.out.println(pair.getKey() + ": " + pair.getValue());
            }
        }
    }

    public static class LRUCache2<K, V> {
        private LinkedList<Pair<K, V>> list;
        private int capacity;
        public LRUCache2(int capacity) {
            list = new LinkedList<>();
            this.capacity = capacity;
        }

        /**

         * @param key
         * @param value
         */
        public void put(K key, V value) {
            int idx = -1;
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).getKey().equals(key)) {
                    idx = i;
                    break;
                }
            }
            if (idx != -1) {
                list.remove(idx);
                list.add(new Pair<>(key, value));
                return;
            }
            if (list.size() > capacity) {
                list.removeFirst();
            }
            list.add(new Pair<>(key, value));
        }

        /**
         * @param key
         * @return
         */
        public V get(K key) {
            int idx = -1;
            Pair<K, V> pair = null;
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).getKey().equals(key)) {
                    idx = i;
                    break;
                }
            }
            if (idx != -1) {
                pair = list.get(idx);
                list.remove(idx);
            } else {
                return null;
            }
            list.add(pair);
            return pair.getValue();
        }
        public void remove(K key) {
            int idx = -1;
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).getKey().equals(key)) {
                    idx = i;
                    break;
                }
            }
            if (idx != -1) {
                list.remove(idx);
            }
        }
        public int getSize() {
            return list.size();
        }
        public void show() {
            for (Pair<K, V> pair : list) {
                System.out.println(pair.getKey() + ": " + pair.getValue());
            }
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        LRUCache2<String, String> cache = new LRUCache2<>(3);
        cache.put("name", "xs");
        cache.put("age", "25");
        String ss = cache.get("name");
        cache.show();
        cache.put("motto", "so just do it");
        cache.put("name", "12");
        cache.put("motto", "current is future");
        cache.put("motto", "current is future2");
        cache.put("motto", "current is future3");
        String age = cache.get("age");
        int size = cache.getSize();
        System.out.println(size);
        cache.show();
    }
}