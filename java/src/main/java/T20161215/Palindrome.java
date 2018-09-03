package T20161215;

import java.util.*;

/**
 * Created by TomNg on 2016/12/15.
 */
public class Palindrome {
    /**
     * @param s: A string
     * @return: A list of lists of string
     */
    public List<List<String>> partition(String s) {
        // write your code here
        char[] chars = s.toCharArray();
        List<int[]> pals = findAll(chars);
        Node root = build(chars, pals);
        return getAll(chars, root);
    }

    private Node build(char[] chars, List<int[]> pals) {
        Node root = new Node(null, null);
        for (int[] pal : pals) {
            add(root, pal);
        }
        return root;
    }

    private List<List<String>> getAll(char[] chars, Node root) {
        Map<Long, String> cache = new HashMap<>();
        List<List<String>> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (current.hasNext()) {
                for (Node node : current.getNext()) {
                    stack.push(node);
                }
            }
            result.add(getString(chars, current, cache));
        }
        return result;
    }

    private void add(Node root, int[] pals) {
        Stack<Node> stack = new Stack<>();
        for (Node node : root.getNext()) {
            stack.push(node);
        }
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            for (Node node : current.getNext()) {
                stack.push(node);
            }
            if (current.pal[1] < pals[0]) {
                current.next.add(new Node(pals, current));
            }
        }
        root.next.add(new Node(pals, root));
    }

    private List<String> getString(char[] chars, Node leaf, Map<Long, String> cache) {
        List<String> list = new ArrayList<>(chars.length >> 1);
        int cIndex = chars.length - 1;
        Node p = leaf;
        while (p != null && p.pal != null) {
            for (; cIndex > p.pal[1]; --cIndex) {
                list.add(0, getString(chars, cIndex, cIndex, cache));
            }
            list.add(0, getString(chars, p.pal[0], p.pal[1], cache));
            cIndex = p.pal[0] - 1;
            p = p.parent;
        }
        for (; cIndex > -1; --cIndex) {
            list.add(0, getString(chars, cIndex, cIndex, cache));
        }
        return list;
    }

    private String getString(char[] chars, int start, int end, Map<Long, String> cache) {
        long key = add(start, end);
        String s = cache.get(key);
        if (s == null) {
            s = new String(chars, start, end - start + 1);
            cache.put(key, s);
        }
        return s;
    }

    private static long add(int a, int b) {
        Long result = (long) a;
        result = result << 32;
        result = result | b;
        return result;
    }

    private static class Node {
        private List<Node> next;
        private Node parent;
        private int[] pal;

        public Node(int[] pal, Node parent) {
            this.pal = pal;
            this.parent = parent;
        }

        public List<Node> getNext() {
            if (next == null)
                this.next = new ArrayList<>(4);
            return next;
        }

        public boolean hasNext() {
            return next != null && !next.isEmpty();
        }
    }

    private List<int[]> findAll(char[] chars) {
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[j] == c && is(chars, i + 1, j - 1)) {
                    result.add(new int[]{i, j});
                }
            }
        }
        return result;
    }

    private boolean is(char[] chars, int start, int end) {
        while (start < end) {
            if (chars[start++] != chars[end--])
                return false;
        }
        return true;
    }

    public static void main(String args[]) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 2; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }

//        List<List<String>> result = new Palindrome().partition(buf.toString());
        List<List<String>> result = new Palindrome().partition("cbbbcc");
        for (List<String> strings : result) {
            System.out.print(strings.stream().reduce((a, b) -> a + "," + b).get());
            System.out.print("\n");
        }
    }
}
