package T20180904;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and set.
 * <p>
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
 */
public class LRUCache {

    class Node {
        public int key;
        public int val;
        public Node prev;
        public Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private int capacity;
    private Node head, tail;
    private Map<Integer, Node> nodeIndexes;

    /*
     * @param capacity: An integer
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.nodeIndexes = new HashMap<>(capacity);
        head = null;
        tail = null;
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        Node node = getNode(key);
        if (node == null) {
            return -1;
        } else {
            return node.val;
        }
        // write your code here
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public Node getNode(int key) {
        Node node = nodeIndexes.get(key);
        if (node == null) {
            return null;
        } else {
            reset(node);
            return node;
        }
        // write your code here
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        Node oldNode = getNode(key);
        if (oldNode != null) {
            oldNode.val = value;
            return;
        }

        if (nodeIndexes.size() == capacity) {
            removeHead();
        }

        Node newNode = new Node(key, value);
        nodeIndexes.put(key, newNode);
        insert(newNode);
    }

    private void insert(Node newNode) {
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    private void removeHead() {
        nodeIndexes.remove(head.key);
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
    }

    private void reset(Node node) {
        if (node == tail) {//needn't move if the node is tail
            return;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else {
            head = node.next;
            head.prev = null;
        }

        tail.next = node;
        node.prev = tail;
        tail = node;
        tail.next = null;
    }

    public static void main(String[] args) {

        LRUCache lruCache = new LRUCache(1);

        lruCache.set(2, 1);
        System.out.println(lruCache.get(2));
        lruCache.set(3, 2);
        System.out.println(lruCache.get(2));
        System.out.println(lruCache.get(3));
    }

}