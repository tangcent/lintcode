package T20180921;

import java.util.*;

/**
 * Description
 * Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.
 * <p>
 * Return all such possible sentences.
 * <p>
 * Example
 * Gieve s = lintcode,
 * dict = ["de", "ding", "co", "code", "lint"].
 * <p>
 * A solution is ["lint code", "lint co de"].
 */
public class WordBreakII {

    /*
     * @param s: A string
     * @param wordDict: A set of words.
     * @return: All possible sentences.
     */
    public List<String> wordBreak(String s, Set<String> wordDict) {
        // write your code here
        if (s == null || s.length() == 0) {
            ArrayList<String> list = new ArrayList<>();
            list.add(s);
            return list;
        }
        QuicklyDictNode root = new QuicklyDictNode();
        initDict(root, wordDict);
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

    private List<String> doWordBreak(String s, QuicklyDictNode dict) {
        // write your code here
        List<String> result = new ArrayList<>();

        Stack<Index> rest = new Stack<>();
        rest.add(new Index(0));
        char[] chars = s.toCharArray();
        int max = chars.length - 1;
        while (!rest.isEmpty()) {
            Index index = rest.pop();
            DictNode n = dict;
            int i;
            for (i = index.index; i < chars.length; i++) {
                n = n.getNext(chars[i]);
                if (n == null) {
                    break;
                }
                if (n.isEnd()) {
                    if (i == max) {
                        result.add(index.extract(s));
                    } else {
                        rest.push(index.next(i + 1));
                    }

                }
            }
        }
        return result;
    }

    private class Index {
        private int index;

        private Index pre;

        public Index(int index) {
            this.index = index;
        }

        public Index(int index, Index pre) {
            this.index = index;
            this.pre = pre;
        }

        public Index next(int index) {
            return new Index(index, this);
        }

        public String extract(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(str, index, str.length());
            Index index = this;
            while (index.pre != null) {
                sb.insert(0, ' ');
                sb.insert(0, str, index.pre.index, index.index);
                index = index.pre;
            }
            return sb.toString();
        }
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

        List<String> strings = new WordBreakII().wordBreak("lintcode",
                new HashSet<>(Arrays.asList("lint", "code", "co", "de")));
        for (String str : strings) {
            System.out.println(str);
        }
    }
}
