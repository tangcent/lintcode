package T20180924;


import java.util.Stack;

/**
 * Description
 * Implement regular expression matching with support for '.' and '*'.
 * <p>
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 * <p>
 * <p>
 * The function prototype should be:
 * <p>
 * bool isMatch(string s, string p)
 * <p>
 * Example
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "a*") → true
 * isMatch("aa", ".*") → true
 * isMatch("ab", ".*") → true
 * isMatch("aab", "c*a*b") → true
 */
public class RegularExpressionMatching {

    /**
     * @param s: A string
     * @param p: A string includes "." and "*"
     * @return: A boolean
     */
    public boolean isMatch(String s, String p) {
        // write your code here
        Pattern[] patterns;
        try {
            patterns = parsePattern(p);
        } catch (Exception e) {
            return false;
        }

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

        Stack<Pattern> patternStack = new Stack<>();

        int index = str.length() - 1;
        while (index > -1) {

            char ch = str.charAt(index);
            switch (ch) {
                case '.':
                    patternStack.add(new DotPattern());
                    --index;
                    break;
                case '*':
                    char pre = str.charAt(index - 1);
                    if (pre == '.') {
                        patternStack.add(new DotAsteriskPattern());
                    } else if (pre == '*') {
                        throw new IllegalArgumentException();
                    } else {
                        patternStack.add(new CharAsteriskPattern(pre));
                    }
                    index -= 2;
                    break;
                default://c
                    int preIndex = index;
                    do {
                        if (--preIndex == -1) {
                            patternStack.add(new CharsPattern(getChars(str, 0, index)));
                            break;
                        }
                    }
                    while (notDotAndAsterisk(str.charAt(preIndex)));

                    if (preIndex == -1) {
                        index = -1;
                        break;
                    }

                    if (str.charAt(preIndex) == '.') {
                        patternStack.add(new CharsPattern(getChars(str, preIndex + 1, index)));
                        patternStack.add(new DotPattern());
                        index = preIndex - 1;
                    } else {//*cccc
                        pre = str.charAt(preIndex - 1);
                        if (pre == '.') {//.*ccccc
                            patternStack.add(new DotAsteriskAndCharsPattern(getChars(str, preIndex + 1, index)));
                        } else {
                            patternStack.add(new CharAsteriskAndCharsPattern(pre, getChars(str, preIndex + 1, index)));
                        }
                        index = preIndex - 2;
                    }

                    break;
            }

        }

        Pattern[] patterns = new Pattern[patternStack.size()];
        for (int i = 0; i < patterns.length; i++) {
            patterns[i] = patternStack.pop();

        }
        return patterns;
    }

    private boolean notDotAndAsterisk(char ch) {
        return ch != '.' && ch != '*';
    }

    private char[] getChars(String str, int srcBegin, int srcEnd) {
        ++srcEnd;
        char[] chars = new char[srcEnd - srcBegin];
        str.getChars(srcBegin, srcEnd, chars, 0);
        return chars;
    }

    private static interface Pattern {
        abstract int match(char[] chars, int start);

        abstract int nextMatch(char[] chars);

        abstract boolean matchRest(char[] chars, int start);

        int minLength();

    }

    //.
    private static class DotPattern implements Pattern {

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
            return ".";
        }
    }

    //.*
    private static class DotAsteriskPattern implements Pattern {

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

    //c*
    private static class CharAsteriskPattern implements Pattern {

        private int matched;
        private char ch;

        public CharAsteriskPattern(char ch) {
            this.ch = ch;
        }

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
            if (chars[matched] != ch) {
                return -1;
            }
            ++matched;
            if (matched >= chars.length) {
                return -1;
            }
            return matched;
        }

        @Override
        public boolean matchRest(char[] chars, int start) {
            while (start < chars.length) {
                if (chars[start++] != ch) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int minLength() {
            return 0;
        }

        @Override
        public String toString() {
            return ch + "*";
        }
    }

    //ccccc
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

    //.*cccc
    private static class DotAsteriskAndCharsPattern implements Pattern {

        private int matched;

        char[] nextChars;

        public DotAsteriskAndCharsPattern(char[] chars) {
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
            return ".*" + new String(nextChars);
        }
    }

    //c*dddd
    private static class CharAsteriskAndCharsPattern implements Pattern {

        private int matched;
        private char ch;
        private char[] nextChars;

        public CharAsteriskAndCharsPattern(char ch, char[] nextChars) {
            this.ch = ch;
            this.nextChars = nextChars;
        }

        @Override
        public int match(char[] chars, int start) {

            matched = start;

            int last = chars.length - this.nextChars.length;

            do {
                if (isEqual(chars, matched, nextChars)) {
                    return matched + nextChars.length;
                }
                if (chars[matched++] != ch) {
                    return -1;
                }
            } while (matched < last);

            return -1;
        }

        @Override
        public int nextMatch(char[] chars) {
            ++matched;
            int last = chars.length - this.nextChars.length;

            do {
                if (isEqual(chars, matched, nextChars)) {
                    return matched;
                }
                if (chars[matched++] != ch) {
                    return -1;
                }
            } while (matched < last);

            return -1;
        }

        @Override
        public boolean matchRest(char[] chars, int start) {
            int end = chars.length - nextChars.length;
            if (end < start) {
                return false;
            }
            if (!isEqual(chars, end, nextChars)) {
                return false;
            }
            while (start < end) {
                if (chars[start++] != ch) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int minLength() {
            return nextChars.length;
        }

        @Override
        public String toString() {
            return ch + "*" + new String(nextChars);
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
        System.out.println(new RegularExpressionMatching().isMatch("abcde", "......."));
    }
}
