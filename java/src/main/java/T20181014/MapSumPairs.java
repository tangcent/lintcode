package T20181014;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Implement a MapSum class with insert, and sum methods.
 * <p>
 * For the method insert, you'll be given a pair of (string, integer). The string represents the key and the integer represents the value. If the key already existed, then the original key-value pair will be overridden to the new one.
 * <p>
 * For the method sum, you'll be given a string representing the prefix, and you need to return the sum of all the pairs' value whose key starts with the prefix.
 * <p>
 * <p>
 * Example
 * Input: insert("apple", 3), Output: Null
 * Input: sum("ap"), Output: 3
 * Input: insert("app", 2), Output: Null
 * Input: sum("ap"), Output: 5
 * 0
 */
public class MapSumPairs {

    class MapSum {

        WordNode root = new QuicklyWordNode();

        /**
         * Initialize your data structure here.
         */
        public MapSum() {

        }

        public void insert(String key, int val) {
            WordNode node = root;
            for (int i = 0; i < key.length(); i++) {
                char ch = key.charAt(i);
                node = node.createNext(ch);
            }
            node.setVal(val);
        }

        public int sum(String prefix) {
            WordNode node = root;
            for (int i = 0; i < prefix.length(); i++) {
                char ch = prefix.charAt(i);
                node = node.getNext(ch);
                if (node == null) {
                    return 0;
                }
            }
            return node.count();
        }

        private abstract class WordNode {

            public abstract int count();

            public abstract void setVal(int val);

            public abstract int getVal();

            public abstract char getCh();

            public abstract WordNode createNext(char ch);

            public abstract WordNode getNext(char ch);
        }

        private class QuicklyWordNode extends WordNode {
            private BasicWordNode[] next = new BasicWordNode[26];

            @Override
            public int count() {
                int count = 0;
                for (BasicWordNode node : next) {
                    count += node.count();
                }
                return count;
            }

            @Override
            public void setVal(int val) {
            }

            @Override
            public int getVal() {
                return 0;
            }

            @Override
            public char getCh() {
                return 0;
            }

            @Override
            public WordNode createNext(char ch) {
                BasicWordNode node = next[ch - 'a'];
                if (node == null) {
                    node = new BasicWordNode(ch);
                    next[ch - 'a'] = node;
                }
                return node;
            }

            @Override
            public WordNode getNext(char ch) {
                return next[ch - 'a'];
            }
        }

        private class BasicWordNode extends WordNode {
            int val = 0;
            char ch;
            int count = -1;
            List<BasicWordNode> next;

            public BasicWordNode(char ch) {
                this.ch = ch;
            }

            @Override
            public int count() {
                if (count != -1) {
                    return count;
                }

                if (next == null || next.isEmpty()) {
                    return val;
                }
                count = val;
                for (WordNode wordNode : next) {
                    count += wordNode.count();
                }
                return count;
            }

            @Override
            public void setVal(int val) {
                this.count = -1;
                this.val = val;
            }

            @Override
            public int getVal() {
                return this.val;
            }

            @Override
            public char getCh() {
                return this.ch;
            }

            @Override
            public WordNode createNext(char ch) {
                count = -1;
                if (next == null) {
                    this.next = new ArrayList<>();
                } else {
                    for (BasicWordNode wordNode : next) {
                        if (wordNode.ch == ch) {
                            return wordNode;
                        }
                    }
                }
                BasicWordNode wordNode = new BasicWordNode(ch);
                this.next.add(wordNode);
                return wordNode;
            }

            @Override
            public WordNode getNext(char ch) {
                if (next == null) {
                    return null;
                }

                for (BasicWordNode wordNode : next) {
                    if (wordNode.ch == ch) {
                        return wordNode;
                    }
                }
                return null;
            }
        }
    }

    private void test() {
        MapSum mapSum = new MapSum();
        mapSum.insert("apple", 3);
        System.out.println(mapSum.sum("ap"));
        mapSum.insert("app", 2);
        System.out.println(mapSum.sum("ap"));
        mapSum.insert("applb", 2);
        System.out.println(mapSum.sum("ap"));
    }

    public static void main(String[] args) {
        new MapSumPairs().test();
    }
}