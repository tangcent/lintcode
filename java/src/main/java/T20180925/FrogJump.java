package T20180925;

import java.util.Stack;

/**
 * Description
 * A frog is crossing a river. The river is divided into x units and at each unit there may or may not exist a stone. The frog can jump on a stone, but it must not jump into the water.
 * <p>
 * Given a list of stones' positions (in units) in sorted ascending order, determine if the frog is able to cross the river by landing on the last stone. Initially, the frog is on the first stone and assume the first jump must be 1 unit.
 * <p>
 * If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units. Note that the frog can only jump in the forward direction.
 * <p>
 * The number of stones is ≥ 2 and is < 1100.
 * Each stone's position will be a non-negative integer < 2^31.
 * The first stone's position is always 0.
 * <p>
 * Example
 * Given stones = [0,1,3,5,6,8,12,17]
 * <p>
 * There are a total of 8 stones.
 * The first stone at the 0th unit, second stone at the 1st unit,
 * third stone at the 3rd unit, and so on...
 * The last stone at the 17th unit.
 * <p>
 * Return true. The frog can jump to the last stone by jumping
 * 1 unit to the 2nd stone, then 2 units to the 3rd stone, then
 * 2 units to the 4th stone, then 3 units to the 6th stone,
 * 4 units to the 7th stone, and 5 units to the 8th stone.
 * <p>
 * Given stones = `[0,1,2,3,4,8,9,11]`
 * <p>
 * Return false. There is no way to jump to the last stone as
 * the gap between the 5th and 6th stone is too large.
 */
public class FrogJump {

    /**
     * @param stones: a list of stones' positions in sorted ascending order
     * @return: true if the frog is able to cross the river or false
     */
    public boolean canCross(int[] stones) {
        // write your code here

        int dest = stones.length - 1;
        if (dest == 0) {
            return true;
        }
        if (dest == 1) {
            return stones[1] == 1;
        }
        int index = 1;
        int k = 1;
        Stack<Long> available = new Stack<>();
        while (index != dest) {
            int currStone = stones[index];
            int min = currStone + k - 1;
            int max = min + 2;
            for (; index < dest; ) {
                int nextStone = stones[++index];
                if (nextStone > max << 1) {
                    if (nextStone > stones[index - 1] << 1) {
                        return false;
                    }
                }
                if (nextStone > max) {
                    break;
                }
                if (nextStone >= min) {
                    if (index == dest) {
                        return true;
                    }
                    available.push((((long) index << 32) | (nextStone - currStone)));
                }
            }
            if (available.isEmpty()) {
                return false;
            }
            Long c = available.pop();
            index = (int) (c >> 32);
            k = (int) (c & 0xffffffff);
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new FrogJump().canCross(new int[]{0, 1, 5}));
        int[] arr = new int[1000];
        for (int i = 0; i < 1000; i++) {
            arr[i] = i;
        }
        arr[999] = 9999999;
        System.out.println(new FrogJump().canCross(arr));

    }

}
