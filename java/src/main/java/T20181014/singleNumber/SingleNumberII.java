package T20181014.singleNumber;

/**
 * Description
 * Given 2*n + 1 numbers, every numbers occurs twice except one, find it.
 * <p>
 * Example
 * Given [1,2,2,1,3,4,3], return 4
 * <p>
 * Challenge
 * One-pass, constant extra space.
 */
public class SingleNumberII {

    /**
     * @param A: An integer array
     * @return: An integer
     */
    public int singleNumber(int[] A) {
        int m = 0;
        for (int i : A) {
            m ^= i;
        }
        return m;
    }
}
