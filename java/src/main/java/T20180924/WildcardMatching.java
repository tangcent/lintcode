package T20180924;

import java.util.Stack;

/**
 * Description
 * Implement wildcard pattern matching with support for '?' and '*'.
 * <p>
 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).
 * The matching should cover the entire input string (not partial).
 * <p>
 * Example
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "*") → true
 * isMatch("aa", "a*") → true
 * isMatch("ab", "?*") → true
 * isMatch("aab", "c*a*b") → false
 */
public class WildcardMatching {

    /**
     * @param s: A string
     * @param p: A string includes "?" and "*"
     * @return: is Match?
     */
    public boolean isMatch(String s, String p) {
        // write your code here
        Pattern[] patterns = parsePattern(p);

        if (patterns.length == 1) {
            return patterns[0].matchRest(s.toCharArray(), 0);
        }

        int m = 0;
        int[] minLength = new int[patterns.length];
        for (int i = patterns.length - 1; i >= 0; i--) {
            minLength[i] = m;
            Pattern pattern = patterns[i];
            m += pattern.minLength();
        }


        int pIndex = 0;
        int index = 0;
        Pattern pattern = patterns[0];
        char[] chars = s.toCharArray();

        int lastPattern = patterns.length - 1;
        boolean isNew = true;
        for (; ; ) {
            if (isNew) {
                index = pattern.match(chars, index);
            } else {
                index = pattern.nextMatch(chars);
            }

            if (index == -1) {
                //匹配失败，回退
                if (pIndex == 0) {
                    return false;
                }
                pattern = patterns[--pIndex];
                isNew = false;
            } else {
                if (index + minLength[pIndex] > chars.length) {
                    return false;
                }
                //匹配成功，下一个
                pattern = patterns[++pIndex];
                if (pIndex == lastPattern) {
                    if (pattern.matchRest(chars, index)) {
                        return true;
                    } else {
                        pattern = patterns[--pIndex];
                        isNew = false;
                    }
                } else {
                    isNew = true;
                }
            }
        }
    }

    private Pattern[] parsePattern(String str) {

        Stack<Pattern> patterns = new Stack<>();
        int start = -1;
        int i = 0;
        Pattern nextPattern = null;
        Pattern prePattern = null;
        char pre = 0;
        for (; i < str.length(); i++) {
            char ch = str.charAt(i);
            switch (ch) {
                case '?':
                    nextPattern = new QuestionPattern();
                    break;
                case '*':
                    if (pre == '*')
                        continue;
                    nextPattern = new AsteriskPattern();
                    break;
                default:
                    if (start == -1) {
                        start = i;
                    }
                    break;
            }
            pre = ch;
            if (nextPattern != null) {

                if (start != -1) {
                    char[] chars = new char[i - start];
                    str.getChars(start, i, chars, 0);
                    if (prePattern instanceof AsteriskPattern) {
                        patterns.pop();
                        patterns.add(new AsteriskAndCharsPattern(chars));
                    } else {
                        patterns.add(new CharsPattern(chars));
                    }
                    start = -1;
                }

                prePattern = nextPattern;
                patterns.add(nextPattern);
                nextPattern = null;
            }
        }
        if (start != -1) {
            char[] chars = new char[i - start];
            str.getChars(start, i, chars, 0);
            if (prePattern instanceof AsteriskPattern) {
                patterns.pop();
                patterns.add(new AsteriskAndCharsPattern(chars));
            } else {
                patterns.add(new CharsPattern(chars));
            }
        }

        return patterns.toArray(new Pattern[patterns.size()]);
    }

    private static interface Pattern {
        abstract int match(char[] chars, int start);

        abstract int nextMatch(char[] chars);

        abstract boolean matchRest(char[] chars, int start);

        int minLength();

    }

    private static class QuestionPattern implements Pattern {

        @Override
        public int match(char[] chars, int start) {
            return start + 1;
        }

        @Override
        public int nextMatch(char[] chars) {
            return -1;
        }

        @Override
        public boolean matchRest(char[] chars, int start) {
            return chars.length == start + 1;
        }

        @Override
        public int minLength() {
            return 1;
        }

        @Override
        public String toString() {
            return "?";
        }
    }

    private static class AsteriskPattern implements Pattern {

        private int matched;

        @Override
        public int match(char[] chars, int start) {
            matched = start;
            if (matched >= chars.length) {
                return -1;
            }
            return matched;
        }

        @Override
        public int nextMatch(char[] chars) {
            ++matched;
            if (matched >= chars.length) {
                return -1;
            }
            return matched;
        }

        @Override
        public boolean matchRest(char[] chars, int start) {
            return true;
        }

        @Override
        public int minLength() {
            return 0;
        }

        @Override
        public String toString() {
            return "*";
        }
    }

    private static class CharsPattern implements Pattern {
        char[] chars;

        public CharsPattern(char[] chars) {
            this.chars = chars;
        }

        @Override
        public int match(char[] chars, int start) {
            if (chars.length < this.chars.length + start) {
                return -1;
            }
            if (isEqual(chars, start, this.chars)) {
                return start + this.chars.length;
            } else {
                return -1;
            }
        }

        @Override
        public int nextMatch(char[] chars) {
            return -1;
        }

        @Override
        public boolean matchRest(char[] chars, int start) {
            if (chars.length != this.chars.length + start) {
                return false;
            }
            return isEqual(chars, start, this.chars);
        }

        @Override
        public int minLength() {
            return chars.length;
        }

        @Override
        public String toString() {
            return new String(chars);
        }
    }

    private static class AsteriskAndCharsPattern implements Pattern {

        private int matched;

        char[] nextChars;

        public AsteriskAndCharsPattern(char[] chars) {
            this.nextChars = chars;
        }

        @Override
        public int match(char[] chars, int start) {
            matched = start;

            int last = chars.length - this.nextChars.length;
            while (matched < last && !isEqual(chars, matched, nextChars)) {
                ++matched;
            }

            if (matched >= last) {
                return -1;
            }
            return matched + nextChars.length;
        }

        @Override
        public int nextMatch(char[] chars) {
            ++matched;
            int last = chars.length - this.nextChars.length;
            while (matched < last && !isEqual(chars, matched, nextChars)) {
                ++matched;
            }

            if (matched >= last) {
                return -1;
            }
            return matched + nextChars.length;
        }

        @Override
        public boolean matchRest(char[] chars, int start) {
            int end = chars.length - nextChars.length;
            if (end < start) {
                return false;
            }
            return isEqual(chars, end, nextChars);
        }

        @Override
        public int minLength() {
            return nextChars.length;
        }

        @Override
        public String toString() {
            return "*" + new String(nextChars);
        }
    }

    private static boolean isEqual(char[] chars, int start, char[] sub) {
        if (start + sub.length > chars.length) {
            return false;
        }
        for (int i = 0; i < sub.length; i++) {
            char c = sub[i];
            if (c != chars[start + i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new WildcardMatching().isMatch("a",
                "a*"));
    }
}
