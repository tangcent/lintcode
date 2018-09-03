package T20161217;

import java.util.HashMap;
import java.util.Map;

/**
 * Reverse Pairs
 * For an array A, if i < j, and A [i] > A [j], called (A [i], A [j]) is a reverse pair.
 * return total of reverse pairs in A.
 * Example
 * Given A = [2, 4, 1, 3, 5] , (2, 1), (4, 1), (4, 3) are reverse pairs. return 3
 */
public class ReversePairs {
    /**
     * @param A an array
     * @return total of reverse pairs
     */
    public long reversePairs(int[] A) {
        long cnt = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = i + 1; j < A.length; j++) {
                if (A[i] > A[j]) {
                    ++cnt;
                }
            }
        }
        return cnt;
    }

    public static void main(String args[]) {
        System.out.print(new ReversePairs().reversePairs(new int[]{1, 3, 2, 1, 5, 6, 3, 21, 234, 65, 21, 3, 6, 7, 23, 12, 3456, 6, 123, 23, 23, 54}));
    }
}
