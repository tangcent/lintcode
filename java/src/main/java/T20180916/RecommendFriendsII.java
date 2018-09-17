package T20180916;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public class RecommendFriendsII {


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

        Map<Integer, Counter> commonFriends = new HashMap<>();

        for (int myFriend : myFriends) {
            myFriendSet.add(myFriend);
            commonFriends.remove(myFriend);
            for (int i : friends[myFriend]) {
                if (myFriendSet.contain(i))
                    continue;
                commonFriends.computeIfAbsent(i, x -> new Counter()).incr();
            }
        }


        int max = 0;
        int likely = -1;
        for (Integer person : commonFriends.keySet()) {
            int commonFriendCount = commonFriends.get(person).get();
            if (commonFriendCount > max) {
                max = commonFriendCount;
                likely = person;
            }
        }
        return likely;
    }

    private class IntSet {

        long smallSet;//0-63
        long middleSet;//64-127
        Set<Integer> others = new HashSet<>();

        private void add(int val) {
            if (val < 0) {
                others.add(val);
            } else if (val < 64) {
                smallSet = smallSet | (1L << val);
            } else if (val < 128) {
                middleSet = middleSet | (1L << (val - 64));
            } else {
                others.add(val);
            }
        }

        private boolean contain(int val) {
            if (val < 0) {
                return others.contains(val);
            } else if (val < 64) {
                return (smallSet & (1L << val)) != 0;
            } else if (val < 128) {
                return (middleSet & (1L << (val - 64))) != 0;
            } else {
                return others.contains(val);
            }
        }

    }

    private class Counter {
        int counter;

        public void incr() {
            ++counter;
        }

        public int get() {
            return counter;
        }
    }

}
