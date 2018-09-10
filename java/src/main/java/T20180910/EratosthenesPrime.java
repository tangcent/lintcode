package T20180910;

import java.util.ArrayList;
import java.util.List;

public class EratosthenesPrime {

    void findPrime(int max) {
        int count = 0;
        boolean[] nums = new boolean[max];
        for (int i = 2; i < max; i++) {
            if (nums[i])
                continue;
            count++;
            System.out.print(i + ",");
            for (int j = i * 2; j < max; j += i) {
                nums[j] = true;
            }
        }
        System.out.println("\ncount:" + count);
    }

    int[] findPrimeToArray(int max) {
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
        System.arraycopy(primes.toArray(), 0, primeArr, 0, primeArr.length);
        return primeArr;
    }

    public static void main(String[] args) {
        new EratosthenesPrime().findPrime(100000);
    }

}
