package T20170104;

/**
 * Created by TomNg on 2017/1/4.
 */
public class CountOneInBinary {
    /**
     * @param num: an integer
     * @return: an integer, the number of ones in num
     */
    public int countOnes(int num) {
        if (num < 0) {
            return 32 - countOnes(-num - 1);
        }
        // write your code here
        int n = 0;
        while (num != 0) {
            if ((num & 1) != 0) {
                ++n;
            }
            num = num >> 1;
        }
        return n;
    }


    public static void main(String args[]) {
        System.out.print(new CountOneInBinary().countOnes(-123));
    }
}
