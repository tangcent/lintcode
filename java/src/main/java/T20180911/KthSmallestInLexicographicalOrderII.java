package T20180911;

/**
 * Description
 * Given integers n and k, find the lexicographically k-th smallest integer in the range from 1 to n.
 * <p>
 * 1 ≤ k ≤ n ≤ 1e9.
 * <p>
 * Example
 * Input:
 * n: 13 k: 2
 * <p>
 * Output:
 * 10
 * <p>
 * Explanation:
 * The lexicographical order is [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9], so the second smallest number is 10.
 */
public class KthSmallestInLexicographicalOrderII {

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};
    final static int[] tenTable = {1, 10, 100, 1000, 10000, 100000, 1000000,
            10000000, 100000000, 1000000000};
    final static int[] lenTable = {1, 11, 111, 1111, 11111, 111111, 1111111,
            11111111, 111111111, 1111111111};

    // Requires positive x
    static int numSize(int x) {
        for (int i = 0; ; i++)
            if (x <= sizeTable[i])
                return i + 1;
    }

    /**
     * @param n: a integer
     * @param k: a integer
     * @return: return a integer
     */
    public int findKthNumber(int n, int k) {
        // write your code here
        int c = 1;
        int rest = k - 1;
        Root root = new Root(n);
        while (rest > 0) {
            int length = root.lengthOf(c);
            System.out.println(length);
            if (rest < length) {
                rest -= 1;
                c = m10(c);
            } else {
                rest -= length;
                c = c + 1;
            }
        }
        return c;
    }

    static int maxLeft = (Integer.MAX_VALUE) / 10;
    static int maxRight = (Integer.MAX_VALUE - 9) / 10;

    private class Root {
        int max;//length of numbers
        int maxLevel;//max level of tree
        int fullLevel;

        public Root(int max) {
            this.max = max;
            maxLevel = numSize(max);
            fullLevel = maxLevel - 1;
        }

        private int lengthOf(int i) {
            int left = i;
            int right = i;
            int level = numSize(i);

            int count = 1;
            int length = 1;
            if (level < fullLevel) {
                int diff = fullLevel - level;
                length = p10(diff);
                left = left * length;
                right = (right + 1) * length - 1;
                count = lenOf(diff);
            }
            if (level < maxLevel) {
                if (left > maxLeft || right > maxRight) {
                    return count;
                }
                left = m10(left);
                right = m10(right) + 9;
                if (right > max) {
                    if (left > max) {
                        return count;
                    } else {
                        count += max - left + 1;
                    }
                } else {
                    count += m10(length);
                }
            }
            return count;
        }
    }

    private int m10(int i) {
        return (i << 3) + (i << 1);
    }

    private int p10(int i) {
        return tenTable[i];
    }

    private int lenOf(int i) {
        return lenTable[i];
    }

    public static void main(String[] args) {
//        System.out.println(new KthSmallestInLexicographicalOrder().findKthNumber(200234234, 1833));
        System.out.println(new KthSmallestInLexicographicalOrderII().findKthNumber(1000000000, 354646416));
//        System.out.println(new KthSmallestInLexicographicalOrder().findKthNumber(13, 2));
//        System.out.println(new KthSmallestInLexicographicalOrderII().findKthNumber(200, 18));
    }
}
