package T20180910;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Given the prime number n, output the number of prime numbers
 * <p>
 * n <= 100000
 * The prime number is defined as a natural number greater than 1, and there are no other factors except 1 and it itself.
 * <p>
 * Example
 * Given n = 3, return 2.
 * <p>
 * explanation:
 * [2,3,5], 3 is the second prime number.
 * Given n = 11, return 5.
 * <p>
 * explanation:
 * [2,3,5,7,11], 11 is the fifth prime number.
 */
public class KthPrimeNumber {

    static int[] primes;

    static {
        primes = findPrimeToArray(100000);
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
        for (int i = 0; i < primeArr.length; i++) {
            primeArr[i] = primes.get(i);
        }
        return primeArr;
    }


    /**
     * @param n: the number
     * @return: the rank of the number
     */
    public int kthPrime(int n) {
        int start = 0;
        int end = primes.length;
        for (; ; ) {
            int middle = (start + end) >> 1;
            int r = primes[middle];
            if (r == n) {
                return middle;
            } else if (r > n) {
                if (end == middle) {
                    return middle + 1;
                }
                end = middle;
            } else {
                if (start == middle) {
                    return middle + 1;
                }
                start = middle;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new KthPrimeNumber().kthPrime(383));
    }

}
