package T20180904;

import java.util.HashMap;
import java.util.Map;

/**
 * LFU (Least Frequently Used) is a famous cache eviction algorithm.
 * <p>
 * For a cache with capacity k, if the cache is full and need to evict a key in it, the key with the lease frequently used will be kicked out.
 * <p>
 * Implement set and get method for LFU cache.
 */
public class LFUCache {

    private int gIndex = 0;

    class Node {
        int val;
        int times;
        int index;//just like timestamp


        Node(int val) {
            this.val = val;
            this.times = 0;
            this.index = gIndex++;
        }

        void incr() {
            this.times++;
            this.index = gIndex++;
        }


        boolean lessThan(Node other) {
            return times < other.times || (times == other.times && index < other.index);
        }
    }

    private Map<Integer, Node> nodes;
    private Node leastNode;
    private int leastNodeKey;
    private int capacity;

    /*
     * @param capacity: An integer
     */
    public LFUCache(int capacity) {
        // do intialization if necessary
        this.capacity = capacity;
        nodes = new HashMap<>(capacity);
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        // write your code here
        Node oldNode;
        if ((oldNode = getNode(key)) != null) {
            oldNode.val = value;
            return;
        }

        if (nodes.size() < capacity) {
            Node newNode = new Node(value);
            if (leastNode == null || newNode.lessThan(leastNode)) {
                leastNode = newNode;
                leastNodeKey = key;
            }
            nodes.put(key, newNode);
            return;
        }

        if (leastNode != null) {
            nodes.remove(leastNodeKey);
            leastNode = new Node(value);
            leastNodeKey = key;
            nodes.put(key, leastNode);
            findLeast();
            return;
        }

        //never...
        System.out.println("haha");


    }


    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        Node node = nodes.get(key);
        if (node == null) {
            return -1;
        } else {
            node.incr();
            if (key == leastNodeKey) {//update leastNode
                findLeast();
            }
            return node.val;
        }
        // write your code here
    }

    /*
     * @param key: An integer
     * @return: return the node of the key if it is present otherwise null
     */
    public Node getNode(int key) {
        Node node = nodes.get(key);
        if (node == null) {
            return null;
        } else {
            node.incr();
            if (key == leastNodeKey) {//update leastNode
                findLeast();
            }
            return node;
        }
    }

    private void findLeast() {
        Node node;
        for (int k : nodes.keySet())
            if ((node = nodes.get(k)).lessThan(leastNode)) {
                leastNodeKey = k;
                leastNode = node;
            }
    }

    public static void main(String[] args) {
        LFUCache lfuCache = new LFUCache(3);
        lfuCache.set(1, 10);
        lfuCache.set(2, 20);
        lfuCache.set(3, 30);
        System.out.println(lfuCache.get(1));
        lfuCache.set(4, 40);
        System.out.println(lfuCache.get(4));
        System.out.println(lfuCache.get(3));
        System.out.println(lfuCache.get(2));
        System.out.println(lfuCache.get(1));
        lfuCache.set(5, 50);
        System.out.println(lfuCache.get(1));
        System.out.println(lfuCache.get(2));
        System.out.println(lfuCache.get(3));
        System.out.println(lfuCache.get(4));
        System.out.println(lfuCache.get(5));
    }
}