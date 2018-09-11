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
public class KthSmallestInLexicographicalOrder {

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

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
                c = c * 10;
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
            int length = 1;
            int level = numSize(i);

            int count = 1;
            while (level < fullLevel) {
                left = left * 10;
                right = right * 10 + 9;
                length = length * 10;
                count += length;
                level++;
            }
            if (level < maxLevel) {
                if (left > maxLeft || right > maxRight) {
                    return count;
                }
                left = left * 10;
                right = right * 10 + 9;
                if (right > max) {
                    if (left > max) {
                        return count;
                    } else {
                        length = max - left + 1;
                        count += length;
                    }
                } else {
                    count += length * 10;
                }
            }
            return count;
        }
    }

    public static void main(String[] args) {
//        System.out.println(new KthSmallestInLexicographicalOrder().findKthNumber(200234234, 1833));
        System.out.println(new KthSmallestInLexicographicalOrder().findKthNumber(1000000000, 354646416));
//        System.out.println(new KthSmallestInLexicographicalOrder().findKthNumber(13, 2));
    }
}
