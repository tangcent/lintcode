package T20180921;

import java.util.*;

/**
 * Description
 * Given a string s and a dictionary of words dict, determine if s can be break into a space-separated sequence of one or more dictionary words.
 * <p>
 * Example
 * Given s = "lintcode", dict = ["lint", "code"].
 * <p>
 * Return true because "lintcode" can be break as "lint code".
 */
public class WordBreak {
    /*
     * @param s: A string
     * @param dict: A dictionary of words dict
     * @return: A boolean
     */
    public boolean wordBreak(String s, Set<String> dict) {
        // write your code here
        if (s == null || s.length() == 0) {
            return true;
        }
        QuicklyDictNode root = new QuicklyDictNode();
        initDict(root, dict);
        return doWordBreak(s, root);
    }

    private void initDict(DictNode root, Set<String> dict) {
        for (String s : dict) {
            insert(root, s);
        }
    }

    private void insert(DictNode root, String s) {
        DictNode node = root;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            node = node.createNext(ch);
        }
        node.canBeEnd();
    }

    private boolean doWordBreak(String s, QuicklyDictNode dict) {
        // write your code here
        if (dict.contains(s)) {
            return true;
        }
        Stack<Integer> rest = new Stack<>();
        Set<Integer> used = new HashSet<>();
        rest.add(0);
        char[] chars = s.toCharArray();
        while (!rest.isEmpty()) {
            int index = rest.pop();
            DictNode n = dict;
            int i;
            for (i = index; i < chars.length; i++) {
                n = n.getNext(chars[i]);
                if (n == null) {
                    break;
                }
                if (n.isEnd()) {
                    if (used.add(i + 1)) {
                        rest.push(i + 1);
                    }
                }
            }
            if (i == s.length() && n.isEnd()) {
                return true;
            }
        }
        return false;
    }

    private abstract class DictNode {

        abstract void canBeEnd();

        abstract boolean isEnd();

        abstract char getCh();

        abstract DictNode createNext(char ch);

        abstract DictNode getNext(char ch);

    }

    private class QuicklyDictNode extends DictNode {

        private char ch;
        private BasicDictNode[] next = new BasicDictNode[26];
        private boolean end;

        @Override
        public void canBeEnd() {
            end = true;
        }

        @Override
        public boolean isEnd() {
            return end;
        }

        @Override
        public char getCh() {
            return 0;
        }

        @Override
        public BasicDictNode createNext(char ch) {
            BasicDictNode node = next[ch - 'a'];
            if (node == null) {
                node = new BasicDictNode(ch);
                next[ch - 'a'] = node;
            }
            return node;
        }

        @Override
        public BasicDictNode getNext(char ch) {
            return next[ch - 'a'];
        }

        public boolean contains(String str) {
            DictNode n = this;
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                n = n.getNext(ch);
                if (n == null) {
                    return false;
                }
            }
            return n.isEnd();
        }
    }

    private class BasicDictNode extends DictNode {
        private char ch;
        private List<BasicDictNode> next;
        private boolean end;

        public BasicDictNode() {
        }

        @Override
        public void canBeEnd() {
            this.end = true;
        }

        @Override
        public boolean isEnd() {
            return this.end;
        }

        public BasicDictNode(char ch) {
            this.ch = ch;
        }

        @Override
        public char getCh() {
            return ch;
        }

        @Override
        public BasicDictNode createNext(char ch) {
            if (next == null) {
                next = new ArrayList<>();
                BasicDictNode node = new BasicDictNode(ch);
                next.add(node);
                return node;
            }
            for (BasicDictNode node : next) {
                if (node.ch == ch) {
                    return node;
                }
            }
            BasicDictNode node = new BasicDictNode(ch);
            next.add(node);
            return node;
        }

        @Override
        public BasicDictNode getNext(char ch) {
            if (next == null) {
                return null;
            }
            for (BasicDictNode node : next) {
                if (node.ch == ch) {
                    return node;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {

        System.out.println(new WordBreak().wordBreak("aaaaaaaa", new HashSet<>(Arrays.asList("aaaa", "aa"))));
    }
}
