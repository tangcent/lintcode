package T20180923;


import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Given a list of integers and find the smallest prime number that doesn't appear in this list.
 * <p>
 * Example
 * Given a list [2,3,5,7,11,13,17,23,29]
 * return 19
 */
public class FirstMissingPrimeNumber {


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
            for (int j = i << 1; j < max; j += i) {
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
     * @param nums: an array of integer
     * @return: the first missing prime number
     */
    public int firstMissingPrime(int[] nums) {
        // write your code here
        int i = 0;
        int prime;
        for (; i < nums.length; i++) {
            prime = primes[i];
            if (nums[i] != prime) {
                return prime;
            }
        }
        return primes[i];
    }

}
