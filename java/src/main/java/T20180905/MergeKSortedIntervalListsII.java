package T20180905;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description
 * Merge K sorted interval lists into one sorted interval list. You need to merge overlapping intervals too.
 * <p>
 * <p>
 * Example
 * Given
 * <p>
 * [
 * [(1,3),(4,7),(6,8)],
 * [(1,2),(9,10)]
 * ]
 * Return
 * <p>
 * [(1,3),(4,8),(9,10)]
 *
 * @sweettom@foxmail.com
 */
public class MergeKSortedIntervalListsII {

    /**
     * @param intervals: the given k sorted interval lists
     * @return: the new sorted interval list
     */
    public List<Interval> mergeKSortedIntervalLists(List<List<Interval>> intervals) {

        if (intervals.size() < 3) {
            return doMergeKSortedIntervalLists(intervals);
        }
        int size = nextSize(intervals.size());

        List<List<Interval>> nextStep = new ArrayList<>(size);
        int index = 0;
        for (List<Interval> interval : intervals) {
            init(interval);
            set(nextStep, index, addInterval(get(nextStep, index), interval));
            index = (index + 1) & size;
        }
        return mergeKSortedIntervalLists(nextStep);
    }

    private int nextSize(int x) {
        x = x >> 1;
        if (x > 15) {
            return 15;
        }
        if (x > 7) {
            return 7;
        }
        if (x > 3) {
            return 3;
        }
        return 1;
    }

    public List<Interval> doMergeKSortedIntervalLists(List<List<Interval>> intervals) {
        List<Interval> result = null;
        for (List<Interval> interval : intervals) {
            init(interval);
            result = addInterval(result, interval);
        }
        return result;
    }

    private List<Interval> addInterval(List<Interval> list1, List<Interval> list2) {
        if (list1 == null || list1.isEmpty()) {
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

    private <T> T get(List<T> list, int index) {
        try {
            return list.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    private <T> void set(List<T> list, int index, T t) {
        try {
            list.set(index, t);
        } catch (Exception e) {
            if (list.size() == index) {
                list.add(t);
            }
        }
    }

    private void init(List<Interval> list) {
        try {
            final Iterator<Interval> iterator = list.iterator();
            Interval pre = iterator.next();
            Interval next;
            while (iterator.hasNext()) {
                next = iterator.next();
                if (intersection(pre, next)) {
                    pre.start = min(pre.start, next.start);
                    pre.end = max(pre.end, next.end);
                    iterator.remove();
                } else {
                    pre = next;
                }
            }
        } catch (Exception ignored) {
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
        List<List<Interval>> lists = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            lists.add(new ArrayList<>());
        }

        new MergeKSortedIntervalListsII().mergeKSortedIntervalLists(lists);
    }
}
