package T20180919;

/**
 * Description
 * Give n non-negative integers, find the sum of maximum OR sum, minimum OR sum, maximum AND sum, minimum AND sum.
 * <p>
 * maximum OR sum: In n numbers, take a number of numbers(cannot take nothing), The largest number after the OR operation.
 * minimum OR sum: In n numbers, take a number of numbers(cannot take nothing), The smallest number after the OR operation.
 * maximum AND sum: In n numbers, take a number of numbers(cannot take nothing), The largest number after the AND operation.
 * minimum AND sum: In n numbers, take a number of numbers(cannot take nothing), The smallest number after the AND operation.
 * 1 <= n <= 1000000，0 <= nums[i] <= 2^32 - 1.
 * Example
 * Give n = 3, nums = [1, 2, 3], return 7.
 * <p>
 * Explanation:
 * maximum OR sum: 3, minimum OR sum: 1, maximum AND sum: 3, minimum AND sum: 0.
 * result: 3 + 1 + 3 + 0 = 7.
 * Give n = 3, nums = [0, 0, 1], return 2.
 * <p>
 * Explanation:
 * maximum OR sum: 1, minimum OR sum: 0, maximum AND sum: 1, minimum AND sum: 0.
 * result: 1 + 0 + 1 + 0 = 2.
 * Give n = 5, nums = [12313, 156, 4564, 212, 12], return 25090.
 * <p>
 * Explanation:
 * maximum OR sum: 12765, minimum OR sum: 12, maximum AND sum: 12313, minimum AND sum: 0.
 * result: 12765 + 12 + 12313 = 25090
 * Give n = 3, nums = [111111, 333333, 555555], return 1588322.
 * <p>
 * Explanation:
 * maximum OR sum: 917047, minimum OR sum: 111111, maximum AND sum: 555555, minimum AND sum: 4609.
 * result: 917047+ 111111+ 555555+ 4609 = 1588322.
 */
public class ANDAndORII {

    /**
     * @param n:
     * @param nums:
     * @return: return the sum of maximum OR sum, minimum OR sum, maximum AND sum, minimum AND sum.
     */
    public long getSum(int n, int[] nums) {
        // write your code here

        if (n < 100) {
            int maxOrSum = nums[0];
            int minOrSum = maxOrSum;
            int maxAndSum = maxOrSum;
            int minAndSum = maxOrSum;
            for (int i = 1; i < nums.length; i++) {
                int num = nums[i];
                maxOrSum |= num;
                minAndSum &= num;
                if (num < minOrSum) {
                    minOrSum = num;
                } else if (num > maxAndSum) {
                    maxAndSum = num;
                }
            }
            return maxOrSum + minOrSum + maxAndSum + minAndSum;
        } else {
            Contain contain = new Contain(n, nums);
            return contain.computer();
        }
    }

    private interface Status {
        void computer(Contain contain, int length);

        Status checkStatus(Contain contain);
    }

    private static AllStatus allStatus = new AllStatus();
    private static OrStatus orStatus = new OrStatus();
    private static AndStatus andStatus = new AndStatus();
    private static OnlyStatus onlyStatus = new OnlyStatus();

    private static class AllStatus implements Status {

        @Override
        public void computer(Contain contain, int length) {
            contain.computerAll(length);
        }

        @Override
        public Status checkStatus(Contain contain) {
            if (contain.maxOrSum == 0x7fffffff) {
                return contain.minAndSum == 0 ? onlyStatus : andStatus;
            } else {
                return contain.minAndSum == 0 ? orStatus : allStatus;
            }
        }
    }

    private static class OrStatus implements Status {
        @Override
        public void computer(Contain contain, int length) {
            contain.computerWithOutA(length);
        }

        @Override
        public Status checkStatus(Contain contain) {
            return contain.maxOrSum == 0x7fffffff ? onlyStatus : orStatus;
        }
    }

    private static class AndStatus implements Status {
        @Override
        public void computer(Contain contain, int length) {
            contain.computerWithOutO(length);
        }

        @Override
        public Status checkStatus(Contain contain) {
            return contain.minAndSum == 0 ? onlyStatus : andStatus;
        }
    }

    private static class OnlyStatus implements Status {
        @Override
        public void computer(Contain contain, int length) {
            contain.computerWithOutAO(length);
        }

        @Override
        public Status checkStatus(Contain contain) {
            return onlyStatus;
        }
    }

    private static class Contain {
        int n;
        int[] nums;


        int maxOrSum;
        int minOrSum;
        int maxAndSum;
        int minAndSum;
        int index;

        Status status = allStatus;

        public Contain(int n, int[] nums) {
            this.n = n;
            this.nums = nums;
            index = 1;
            maxOrSum = this.nums[0];
            minOrSum = maxOrSum;
            maxAndSum = maxOrSum;
            minAndSum = maxOrSum;
        }

        private long computer() {
            try {
                int length = nums.length >> 2;
                for (int i = 0; i < 4; i++) {
                    status.computer(this, length);
                    status = status.checkStatus(this);
                }
                status.computer(this, n - (length << 2) - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return count();
        }

        private void computerAll(int length) {
            for (int i = 0; i < length; i++) {
                int num = nums[index++];
                maxOrSum |= num;
                minAndSum &= num;
                if (num < minOrSum) {
                    minOrSum = num;
                } else if (num > maxAndSum) {
                    maxAndSum = num;
                }
            }
        }

        private void computerWithOutA(int length) {
            for (int i = 0; i < length; i++) {
                int num = nums[index++];
                maxOrSum |= num;
                if (num < minOrSum) {
                    minOrSum = num;
                } else if (num > maxAndSum) {
                    maxAndSum = num;
                }
            }
        }

        private void computerWithOutO(int length) {
            for (int i = 0; i < length; i++) {
                int num = nums[index++];
                minAndSum &= num;
                if (num < minOrSum) {
                    minOrSum = num;
                } else if (num > maxAndSum) {
                    maxAndSum = num;
                }
            }
        }

        private void computerWithOutAO(int length) {
            for (int i = 0; i < length; i++) {
                int num = nums[index++];
                if (num < minOrSum) {
                    minOrSum = num;
                } else if (num > maxAndSum) {
                    maxAndSum = num;
                }
            }
        }

        private long count() {
            return maxOrSum + minOrSum + maxAndSum + minAndSum;
        }
    }

    public static void main(String[] args) {
        System.out.println(
                new ANDAndORII().getSum(101, new int[]{
                        1, 2, 3, 111111, 333333, 555555, 123213123, 124254, 4567, 7465, 568, 354, 1234, 432, 346, 476, 5678, 324, 23, 1324, 35, 4765, 465, 123, 123123, 1, 2, 3, 111111, 333333, 555555, 123213123, 124254, 4567, 7465, 568, 354, 1234, 432, 346, 476, 5678, 324, 23, 1324, 35, 4765, 465, 123, 123123, 1, 2, 3, 111111, 333333, 555555, 123213123, 124254, 4567, 7465, 568, 354, 1234, 432, 346, 476, 5678, 324, 23, 1324, 35, 4765, 465, 123, 123123, 1, 2, 3, 111111, 333333, 555555, 123213123, 124254, 4567, 7465, 568, 354, 1234, 432, 346, 476, 5678, 324, 23, 1324, 35, 4765, 465, 123, 123123, 21123123
                }));
    }
}
