package T20180905;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Merge two sorted (ascending) lists of interval and return it as a new sorted list. The new sorted list should be made by splicing together the intervals of the two lists and sorted in ascending order.
 * <p>
 * The intervals in the given list do not overlap.
 * The intervals in different lists may overlap.
 * <p>
 * Example
 * Given list1 = [(1,2),(3,4)] and list2 = [(2,3),(5,6)], return [(1,4),(5,6)].
 */
public class MergeTwoSortedIntervalLists {

    /**
     * @param list1: one of the given list
     * @param list2: another list
     * @return: the new sorted list of interval
     */
    public List<Interval> mergeTwoInterval(List<Interval> list1, List<Interval> list2) {
        // write your code here
//        List<Interval> result = new ArrayList<>(((list1.size() + list2.size() >> 1) + 2));
        return addInterval(list1, list2);
    }

    private List<Interval> addInterval(List<Interval> list1, List<Interval> list2) {
        if (list1.isEmpty()) {
            return list2;
        }
        if (list2.isEmpty()) {
            return list1;
        }

        List<Interval> result = new ArrayList<>(((list1.size() + list2.size() >> 1) + 2));
        int index = 0;
        int index1 = 0;
        int index2 = 0;
        Interval curr1 = get(list1, index1++);
        Interval curr2 = get(list2, index2++);
        while (curr1 != null && curr2 != null) {
            if (intersection(curr1, curr2)) {//处理交集
                if (curr1.end > curr2.end) {
                    curr1 = union(curr1, curr2);
                    curr2 = get(list2, index2++);
                } else {
                    curr2 = union(curr1, curr2);
                    curr1 = get(list1, index1++);
                }
            } else {
                if (curr1.start > curr2.start) {
                    result.add(curr2);
                    curr2 = get(list2, index2++);
                } else {
                    result.add(curr1);
                    curr1 = get(list1, index1++);
                }
            }
        }
        while (curr1 != null) {
            result.add(curr1);
            curr1 = get(list1, index1++);
        }
        while (curr2 != null) {
            result.add(curr2);
            curr2 = get(list2, index2++);
        }
        return result;
    }

    private boolean intersection(Interval a, Interval b) {
        return !(a.start > b.end || a.end < b.start);
    }

    private Interval union(Interval a, Interval b) {
        return new Interval(min(a.start, b.start), max(a.end, b.end));
    }

    private int min(int a, int b) {
        return a > b ? b : a;
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private Interval get(List<Interval> list, int index) {
        try {
            return list.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public static class Interval {
        int start, end;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        List<Interval> list1 = new ArrayList<>(2);
        list1.add(new Interval(1, 2));
        list1.add(new Interval(3, 4));
        List<Interval> list2 = new ArrayList<>(2);
        list2.add(new Interval(2, 3));
        list2.add(new Interval(5, 6));
        List<Interval> result = new MergeTwoSortedIntervalLists().mergeTwoInterval(list1, list2);
        for (Interval interval : result) {
            System.out.printf("{%d,%d}%n", interval.start, interval.end);
        }
    }

}
