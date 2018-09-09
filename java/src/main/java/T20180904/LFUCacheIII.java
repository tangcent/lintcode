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
public class LFUCacheIII {

    class Node {
        int key;
        int val;
        Node prev;
        Node next;
        TimedNode timedNode;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    class TimedNode {
        int times;
        Node head;
        Node tail;
        TimedNode next;

        public TimedNode(int times) {
            this.times = times;
        }
    }

    private Map<Integer, Node> nodeIndexes;//快速索引
    private int capacity;
    private TimedNode head;

    /*
     * @param capacity: An integer
     */
    public LFUCacheIII(int capacity) {
        // do intialization if necessary
        this.capacity = capacity;
        nodeIndexes = new HashMap<>(capacity);
    }

    private Node findRemovedNode() {
        if (head.head != null) {
            return head.head;
        } else {
            TimedNode next = head.next;
            while (next != null) {
                if (next.head != null) {
                    return next.head;
                }
                next = next.next;
            }
        }
        //never arrive here
        return null;
    }

    private void insert(Node newNode) {
        insert(1, newNode);
    }

    private void reset(Node node) {
        remove(node);
        insert(node.timedNode.times + 1, node);
    }

    private void remove(Node removedNode) {
        if (removedNode.prev == null) {//removedNode is head
            removedNode.timedNode.head = removedNode.next;
            if (removedNode.next == null) {
                removedNode.timedNode.tail = null;
            } else {
                removedNode.next.prev = null;
            }
            //todo:remove the empty TimedNode
        } else if (removedNode.next == null) {//removedNode is tail
            removedNode.prev.next = null;
            removedNode.timedNode.tail = removedNode.prev;
        } else {
            removedNode.prev.next = removedNode.next;
            removedNode.next.prev = removedNode.prev;
        }
        removedNode.prev = null;
        removedNode.next = null;
    }

    private void insert(int times, Node newNode) {
        if (head == null) {
            TimedNode newTimedNode = new TimedNode(times);
            head = newTimedNode;
            insert(newNode, newTimedNode);
            return;
        } else {
            TimedNode next = head;
            TimedNode pre = null;
            while (next != null) {
                if (next.times == times) {
                    insert(newNode, next);
                    return;
                } else if (next.times > times) {
                    if (pre == null) {
                        TimedNode newTimedNode = new TimedNode(times);
                        newTimedNode.next = head;
                        head = newTimedNode;
                        insert(newNode, newTimedNode);
                        return;
                    } else {
                        TimedNode newTimedNode = new TimedNode(times);
                        newTimedNode.next = pre.next;
                        pre.next = newTimedNode;
                        insert(newNode, newTimedNode);
                        return;
                    }
                }
                pre = next;
                next = next.next;
            }
            TimedNode newTimedNode = new TimedNode(times);
            pre.next = newTimedNode;
            insert(newNode, newTimedNode);
        }

    }

    private void insert(Node node, TimedNode timedNode) {
        node.timedNode = timedNode;
        if (timedNode.head == null) {
            timedNode.head = node;
            timedNode.tail = node;
        } else {
            timedNode.tail.next = node;
            node.prev = timedNode.tail;
            timedNode.tail = node;
        }
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        // write your code here
        Node oldNode = getNode(key);
        if (oldNode != null) {
            oldNode.val = value;
            return;
        }

        if (nodeIndexes.size() == capacity) {
            Node removedNode = findRemovedNode();
            remove(removedNode);
            nodeIndexes.remove(removedNode.key);
        }

        Node newNode = new Node(key, value);
        nodeIndexes.put(key, newNode);
        insert(newNode);
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
     * @return: return the node of the key if it is present otherwise null
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

    public static void main(String[] args) {

        LFUCacheIII lfuCache = new LFUCacheIII(3);

        lfuCache.set(2, 2);
        lfuCache.set(1, 1);
        System.out.println(lfuCache.get(2));
        System.out.println(lfuCache.get(1));
        System.out.println(lfuCache.get(2));
        lfuCache.set(3, 3);
        lfuCache.set(4, 4);
        System.out.println(lfuCache.get(3));
        System.out.println(lfuCache.get(2));
        System.out.println(lfuCache.get(1));
        System.out.println(lfuCache.get(4));
    }
}