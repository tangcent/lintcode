package T20161225;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by TomNg on 2016/12/26.
 */
public class WordDictionary {

    private Node root = new Node(' ', -1);

    // Adds a word into the data structure.
    public void addWord(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            node = node.addChar(word.charAt(i));
        }
        node.setEnd();
    }

    // Returns if the word is in the data structure. A word could
    // contain the dot character '.' to represent any one letter.
    public boolean search(String word) {
        int len = word.length() - 1;
        if (len == -1) {
            return false;
        }
        Stack<Node> nodeStack = new Stack<>();
        nodeStack.push(root);
        while (!nodeStack.isEmpty()) {
            Node next = nodeStack.pop();
            if (next.hasNext()) {
                for (Node node : next.getNext()) {
                    if (node.is(word.charAt(node.index))) {
                        if (node.index == len) {
                            if (node.isEnd()) {
                                return true;
                            } else {
                                continue;
                            }
                        }
                        nodeStack.push(node);
                    }
                }
            }
        }
        return false;
    }

    private class Node {
        char ch;
        int index;
        List<Node> next;
        boolean end = false;

        public Node() {
        }

        public Node(char ch, int index) {
            this.ch = ch;
            this.index = index;
        }

        public boolean is(char ch) {
            return ch == '.' || this.ch == ch;
        }

        public int getIndex() {
            return index;
        }

        public boolean hasNext() {
            return next != null;
        }

        public List<Node> getNext() {
            return next;
        }

        private Node addChar(char c) {
            if (next == null) {
                next = new ArrayList<>();
                Node node = new Node(c, index + 1);
                next.add(node);
                return node;
            }

            for (Node node : next) {
                if (node.ch == c) {
                    return node;
                }
            }

            Node node = new Node(c, index + 1);
            next.add(node);
            return node;
        }

        private void setEnd() {
            end = true;
        }

        private boolean isEnd() {
            return end;
        }
    }


    public static void main(String args[]) {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("ran");
        wordDictionary.addWord("rune");
        wordDictionary.addWord("runs");
        wordDictionary.addWord("add");
        wordDictionary.addWord("hehe");
        wordDictionary.addWord("pool");
        wordDictionary.addWord("haha");
        search(wordDictionary, "r.n");
    }

    private static void search(WordDictionary wordDictionary, String word) {
        System.out.print(word + "-->" + wordDictionary.search(word) + "\n");
    }
}
