package T20180912;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Initially on a notepad only one character 'A' is present. You can perform two operations on this notepad for each step:
 * <p>
 * Copy All: You can copy all the characters present on the notepad (partial copy is not allowed).
 * Paste: You can paste the characters which are copied last time.
 * Given a number n. You have to get exactly n 'A' on the notepad by performing the minimum number of steps permitted. Output the minimum number of steps to get n 'A'.
 * The n will be in the range [1, 1000].
 * <p>
 * Example
 * Input: 3
 * Output: 3
 * Explanation:
 * Intitally, we have one character 'A'.
 * In step 1, we use Copy All operation.
 * In step 2, we use Paste operation to get 'AA'.
 * In step 3, we use Paste operation to get 'AAA'.
 */
public class TwoKeysKeyboard {

    static int[] primes;

    static {
        primes = findPrimeToArray(1000);
    }

    static int[] findPrimeToArray(int max) {
        List<Integer> primes = new ArrayList<>();
        boolean[] nums = new boolean[max];
        for (int i = 2; i < max; i++) {
            if (nums[i])
                continue;
            primes.add(i);
            for (int j = i * 2; j < max; j += i) {
                nums[j] = true;
            }
        }
        int[] primeArr = new int[primes.size()];
//        System.arraycopy(primes.toArray(), 0, primeArr, 0, primeArr.length);
        int size = primes.size() - 1;
        for (int i = 0; i < primeArr.length; i++) {
            primeArr[i] = primes.get(i);
        }
        return primeArr;
    }

    /**
     * @param n : the number
     */
    public boolean isPrime(int n) {
        int start = 0;
        int end = primes.length;
        for (; ; ) {
            int middle = (start + end) >> 1;
            int r = primes[middle];
            if (r == n) {
                return true;
            } else if (r > n) {
                if (end == middle) {
                    return false;
                }
                end = middle;
            } else {
                if (start == middle) {
                    return false;
                }
                start = middle;
            }
        }
    }

    /**
     * @param n: The number of 'A'
     * @return: the minimum number of steps to get n 'A'
     */
    public int minSteps(int n) {
        if (isPrime(n)) {
            return n;
        }

        int count = 0;
        //divided 2 quickly
        while ((n & 1) == 0) {
            n = n >> 1;
            count += 2;
        }

        int prime = 3;
        int i = 1;
        while (n > 1) {
            if (n % prime == 0) {
                count += prime;
                n = n / prime;
                continue;
            }
            prime = primes[++i];
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(new TwoKeysKeyboard().minSteps(1000));
    }

}
