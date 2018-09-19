package T20180919;

/**
 * Description
 * We are given two arrays num[0..k-1] and rem[0..k-1]. In num[0..k-1], every pair is coprime (gcd for every pair is 1. We need to find minimum positive number x such that:
 * <p>
 * x % num[0]    =  rem[0],
 * x % num[1]    =  rem[1],
 * .......................
 * x % num[k-1]  =  rem[k-1]
 * <p>
 * Example
 * Given  nums = [3, 4, 5], rems = [2, 3, 1], return 11
 * 11 is the smallest number such that:
 * - When we divide it by 3, we get remainder 2.
 * - When we divide it by 4, we get remainder 3.
 * - When we divide it by 5, we get remainder 1.
 * 0
 */
public class ChineseRemainderTheorem {


    /**
     * @param num: the given array
     * @param rem: another given array
     * @return: The minimum positive number of conditions to meet the conditions
     */
    public int remainderTheorem(int[] num, int[] rem) {
        // write your code here
        int step = num[0];
        int min = step + rem[0];
        for (int i = 1; i < num.length; i++) {
            int cStep = num[i];
            int cMin = rem[i] + cStep;
            while (min != cMin) {
                if (min < cMin) {
                    min += step;
                } else {
                    cMin += cStep;
                }
            }
            step *= cStep;
        }
        return min;
    }

    public static void main(String[] args) {
        System.out.println(new ChineseRemainderTheorem().remainderTheorem(
                new int[]{3, 4, 5}, new int[]{2, 3, 1}
        ));
    }


}
