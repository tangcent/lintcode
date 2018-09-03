package T20161228;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MaximumGap {
    /**
     * @param nums: an array of integers
     * @return: the maximum difference
     */
    public int maximumGap(int[] nums) {

        if (nums.length < 2)
            return 0;

        int min = nums[0];
        int max = nums[1];

        //distinct
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
            if (num > max) {
                max = num;
            } else if (num < min) {
                min = num;
            }
        }

        if (numSet.size() < 2)
            return 0;

        GapHold gapHold = new GapHold(min, max);
        numSet.remove(min);
        numSet.remove(max);

        Iterator<Integer> iterator = numSet.iterator();
        while (iterator != null) {
            iterator = tryInsert(gapHold, iterator, numSet);
        }
        return gapHold.gap.gap;
    }

    Iterator<Integer> tryInsert(GapHold gapHold, Iterator<Integer> iterator, Set<Integer> set) {
        while (iterator.hasNext()) {
            if (gapHold.tryInsert(iterator.next())) {
                iterator.remove();
                return iterator;
            }
        }
        Iterator<Integer> newIterator = set.iterator();
        while (newIterator.hasNext() && newIterator != iterator) {
            if (gapHold.tryInsert(newIterator.next())) {
                newIterator.remove();
                return newIterator;
            }
        }
        return null;
    }

    private class GapHold {
        Node root;
        Node gap;
        Node secondGap;
        Node first;
        Node last;
        int mid;

        GapHold(int min, int max) {
            root = new Node();
            root.addNext(min).addNext(max);
            gap = root.next;
            first = gap;
            last = gap.next;
        }

        boolean tryInsert(int x) {
            if (x > gap.value && x < gap.next.value) {
                insert(x);
                return true;
            }
            return false;
        }

        int minClose(int x) {
            if (x >= gap.value || x <= gap.next.value) {
                return -1;
            } else {
                if (x > mid) {
                    return x - mid;
                } else {
                    return mid - x;
                }
            }
        }

        //insert x,and keep asc
        void insert(int x) {
            // first:insert
            Node nNode = gap.addNext(x);
            if (secondGap != null) {
                if (gap.gap > secondGap.gap) {
                    if (gap.gap > nNode.gap) {
                        if (nNode.gap > secondGap.gap) {
                            //gap>n>se
                            secondGap = nNode;
                        }
                        //else gap>se>n
                    } else {
                        //n>gap>se
                        secondGap = gap;
                        gap = nNode;
                    }
                } else if (nNode.gap > secondGap.gap) {
                    //n>se>gap
                    gap = nNode;
                } else {
                    //secondGap promote max
                    gap = secondGap;
                    secondGap = null;//lose second
                }
                return;
            }

            //update gap info
            Node p = first;
            while (p != null) {
                if (p.gap > gap.gap) {
                    secondGap = gap;
                    gap = p;
                }
                p = p.next;
            }
        }
    }

    private class Node {
        int value;
        int gap;//gap between this and next
        Node next;

        public Node() {
            this.value = -1;
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
            this.gap = next == null ? 0 : next.value - value;
        }

        public Node addNext(int value) {
            Node node = new Node(value, this.next);
            if (this.value != -1) {
                this.gap = value - this.value;
            }
            this.next = node;
            return node;
        }
    }

    public static void main(String args[]) {
        System.out.print(new MaximumGap().maximumGap(new int[]{486670389, 90154617, 91261541, 267081012, 215056376, 44169242, 437397295, 347054985, 307142570, 111435310, 69250184, 147631363, 369824585, 335519683, 173177886, 464562049, 355822836, 499392064, 265802803, 200139249, 23954220, 371221415, 222560164, 228221337, 467366531, 1287504, 429871813, 201841560, 419093946, 294155968, 97482320, 218628189, 425372173, 134007602, 226416485, 29495916, 134773464, 443377155, 215091286, 506271848, 173814172, 359889977, 12757162, 159943134, 336439501, 310209658, 82721130, 339807658, 73604786, 171088623, 122306156, 83394489, 76172745, 383841821, 36480165, 210664341, 345104697, 308123221, 237063466, 409175303, 425038165, 286031095, 241889384, 111759704, 361420530, 89431595, 345156590, 12626129, 320536446, 400665736, 13384901, 244611532, 426637801, 207584693, 534954965, 519431890, 363028506, 344960782, 255608218, 149603297, 230394218, 516479631, 359415501, 345863641, 244365261, 279053371, 39719560, 145741987, 341148516, 439038666, 504122987, 25255895, 324267761, 195352333, 420172369, 258657184, 428275255, 250681631, 369988546, 513933666, 357027987, 529610308, 86604818, 325645488}));
        System.out.print("\n\n");
        System.out.print(new MaximumGap().maximumGap(new int[]{1}));
    }

}
