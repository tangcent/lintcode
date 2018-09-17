package T20180916;

/**
 * Description
 * Give n personal friends list, tell you user, find the person that user is most likely to know. (He and the user have the most common friends and he is not a friend of user)
 * <p>
 * n <= 500.
 * The relationship between friends is mutual. (if B appears on a's buddy list, a will appear on B's friends list).
 * Each person's friend relationship does not exceed m, m <= 3000.
 * If there are two people who share the same number of friends as user, the smaller number is considered the most likely person to know.
 * If user and all strangers have no common friends, return -1.
 * <p>
 * Example
 * Given list = [[1,2,3],[0,4],[0,4],[0,4],[1,2,3]], user = 0, return 4.
 * <p>
 * Explanation:
 * 0 and 4 are not friends, and they have 3 common friends. So 4 is the 0 most likely to know.
 * Given list = [[1,2,3,5],[0,4,5],[0,4,5],[0,5],[1,2],[0,1,2,3]], user = 0, return 4.
 * <p>
 * Explanation:
 * Although 5 and 0 have 3 common friends, 4 and 0 only have 2 common friends, but 5 is a 0's friend, so 4 is the 0 most likely to know.
 */
public class RecommendFriendsIV {


    /**
     * @param friends: people's friends
     * @param user:    the user's id
     * @return: the person who most likely to know
     */
    public int recommendFriends(int[][] friends, int user) {
        // Write your code here

        int[] myFriends = friends[user];
        IntSet myFriendSet = new IntSet();

        myFriendSet.add(user);

        for (int myFriend : myFriends) {
            myFriendSet.add(myFriend);
        }


        int max = 0;
        int likely = -1;
        int count = 0;
        for (int i = 0; i < friends.length; i++) {
            if (myFriendSet.contain(i)) {
                continue;
            }
            int[] friendsOfI = friends[i];
            if (friendsOfI.length < max) {
                continue;
            }
            count = 0;
            for (int fi : friendsOfI) {
                if (myFriendSet.contain(fi)) {
                    ++count;
                }
            }
            if (count < max) {
                continue;
            }

            if (count == max) {
                if (i < likely) {
                    likely = i;
                }
            } else {
                max = count;
                likely = i;
            }
        }


        return likely;
    }

//    private static class IntSet {
//
//        long set1;//0-63
//        long set2;//64-127
//        long set3;//128-191
//        long set4;//192-255
//        long set5;//256-319
//        long set6;//320-383
//        long set7;//384-447
//        long set8;//448-511
//        //        long[] sets = new long[8];
//        public static final int setMask = 0b1111000000;
//        public static final int indexMask = 0b0000111111;
//
//        private void add(int val) {
//            switch (val & setMask) {
//                case 0:
//                    set1 = set1 | (1L << val);
//                    break;
//                case 0x40:
//                    set2 = set2 | (1L << (val & indexMask));
//                    break;
//                case 0x80:
//                    set3 = set3 | (1L << (val & indexMask));
//                    break;
//                case 0xc0:
//                    set4 = set4 | (1L << (val & indexMask));
//                    break;
//                case 0x100:
//                    set5 = set5 | (1L << (val & indexMask));
//                    break;
//                case 0x140:
//                    set6 = set6 | (1L << (val & indexMask));
//                    break;
//                case 0x180:
//                    set7 = set7 | (1L << (val & indexMask));
//                    break;
//                case 0x1c0:
//                    set8 = set8 | (1L << (val & indexMask));
//                    break;
//            }
//        }
//
//        private boolean tryAddFailed(int val) {
//            switch (val & setMask) {
//                case 0:
//                    return set1 == (set1 = set1 | (1L << val));
//                case 0x40:
//                    return set2 == (set2 = set2 | (1L << (val & indexMask)));
//                case 0x80:
//                    return set3 == (set3 = set3 | (1L << (val & indexMask)));
//                case 0xc0:
//                    return set4 == (set4 = set4 | (1L << (val & indexMask)));
//                case 0x100:
//                    return set5 == (set5 = set5 | (1L << (val & indexMask)));
//                case 0x140:
//                    return set6 == (set6 = set6 | (1L << (val & indexMask)));
//                case 0x180:
//                    return set7 == (set7 = set7 | (1L << (val & indexMask)));
//                case 0x1c0:
//                    return set8 == (set8 = set8 | (1L << (val & indexMask)));
//            }
//            return true;
//        }
//
//        private boolean contain(int val) {
//
//            switch (val & setMask) {
//                case 0:
//                    return (set1 & (1L << val)) != 0;
//                case 0x40:
//                    return (set2 & 1L << (val & indexMask)) != 0;
//                case 0x80:
//                    return (set3 & (1L << (val & indexMask))) != 0;
//                case 0xc0:
//                    return (set4 & (1L << (val & indexMask))) != 0;
//                case 0x100:
//                    return (set5 & (1L << (val & indexMask))) != 0;
//                case 0x140:
//                    return (set6 & (1L << (val & indexMask))) != 0;
//                case 0x180:
//                    return (set7 & (1L << (val & indexMask))) != 0;
//                case 0x1c0:
//                    return (set8 & (1L << (val & indexMask))) != 0;
//            }
//            return true;
//        }
//
//    }

    private static class IntSet {

        int[] sets = new int[16];
        public static final int setMask = 0x3e0;
        public static final int indexMask = 0x1f;

        private void add(int val) {
            int set = (val & setMask) >> 5;
            sets[set] = sets[set] | (1 << (val & indexMask));
        }

        private boolean tryAddFailed(int val) {
            int set = (val & setMask) >> 5;
            int newValue = sets[set] | (1 << (val & indexMask));
            if (sets[set] == newValue) {
                return true;
            } else {
                sets[set] = newValue;
                return false;
            }
        }

        private boolean contain(int val) {
            int set = (val & setMask) >> 5;
            return (sets[set] & (1 << (val & indexMask))) != 0;
        }

    }

    public static void main(String[] args) {
        IntSet intSet = new IntSet();
        for (int i = 0; i < 500; i++) {
            if (intSet.contain(i)) {
                System.out.println("aerr" + i);
            }
            intSet.add(i);
            if (!intSet.contain(i)) {
                System.out.println("berr" + i);
            }
        }
    }
}
