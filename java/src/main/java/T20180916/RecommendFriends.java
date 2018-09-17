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
public class RecommendFriends {


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

        int[] commonFriends = new int[friends.length];

        for (int myFriend : myFriends) {
            myFriendSet.add(myFriend);
            for (int i : friends[myFriend]) {
                if (myFriendSet.contain(i))
                    continue;
                commonFriends[i]++;
            }
        }


        int max = 0;
        int likely = -1;
        for (int i = 0; i < commonFriends.length; i++) {
            if (myFriendSet.contain(i))
                continue;

            int commonFriend = commonFriends[i];
            if (commonFriend > max) {
                max = commonFriend;
                likely = i;
            }
        }
        return likely;
    }

    private class IntSet {

        long set1;//0-63
        long set2;//64-127
        long set3;//128-191
        long set4;//192-255
        long set5;//256-319
        long set6;//320-383
        long set7;//384-447
        long set8;//448-511

        private void add(int val) {
            if (val < 0x40) {
                set1 = set1 | (1L << val);
            } else if (val < 0x80) {
                set2 = set2 | (1L << (val - 0x40));
            } else if (val < 0xc0) {
                set3 = set3 | (1L << (val - 0x80));
            } else if (val < 0x100) {
                set4 = set4 | (1L << (val - 0xc0));
            } else if (val < 0x140) {
                set5 = set5 | (1L << (val - 0x100));
            } else if (val < 0x180) {
                set6 = set6 | (1L << (val - 0x140));
            } else if (val < 0x1c0) {
                set7 = set7 | (1L << (val - 0x180));
            } else {
                set8 = set8 | (1L << (val - 0x1c0));
            }
        }

        private boolean contain(int val) {
            if (val < 0x40) {
                return (set1 & (1L << val)) != 0;
            } else if (val < 0x80) {
                return (set2 & (1L << (val - 0x40))) != 0;
            } else if (val < 0xc0) {
                return (set3 & (1L << (val - 0x80))) != 0;
            } else if (val < 0x100) {
                return (set4 & (1L << (val - 0xc0))) != 0;
            } else if (val < 0x140) {
                return (set5 & (1L << (val - 0x100))) != 0;
            } else if (val < 0x180) {
                return (set6 & (1L << (val - 0x140))) != 0;
            } else if (val < 0x1c0) {
                return (set7 & (1L << (val - 0x180))) != 0;
            } else {
                return (set8 & (1L << (val - 0x1c0))) != 0;
            }
        }

    }
}
