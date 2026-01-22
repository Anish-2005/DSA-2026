//3507. Minimum Pair Removal to Sort Array I


//Given an array nums, you can perform the following operation any number of times:

//Select the adjacent pair with the minimum sum in nums. If multiple such pairs exist,
//  choose the leftmost one.
///Replace the pair with their sum.
//Return the minimum number of operations needed to make the array non-decreasing.

//An array is said to be non-decreasing if each element is greater than or equal to its
//  previous element (if it exists).

import java.util.*;

class Solution {
    public int minimumPairRemoval(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int x : nums) list.add(x);

        int ops = 0;

        while (!isNonDecreasing(list)) {
            int minSum = Integer.MAX_VALUE;
            int idx = 0;

            for (int i = 0; i < list.size() - 1; i++) {
                int sum = list.get(i) + list.get(i + 1);
                if (sum < minSum) {
                    minSum = sum;
                    idx = i;
                }
            }

            list.set(idx, minSum);
            list.remove(idx + 1);
            ops++;
        }

        return ops;
    }

    private boolean isNonDecreasing(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(i - 1)) return false;
        }
        return true;
    }
}
