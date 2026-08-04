/* 3731. Find Missing Elements

You are given an integer array nums consisting of unique integers.

Originally, nums contained every integer within a certain range. However, some integers might have gone missing from the array.

The smallest and largest integers of the original range are still present in nums.

Return a sorted list of all the missing integers in this range. If no integers are missing, return an empty list.

 

Example 1:

Input: nums = [1,4,2,5]

Output: [3]

Explanation:

The smallest integer is 1 and the largest is 5, so the full range should be [1,2,3,4,5]. Among these, only 3 is missing.*/

import java.util.*;

class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        HashSet<Integer> set = new HashSet<>();

        for (int x : nums) {
            min = Math.min(min, x);
            max = Math.max(max, x);
            set.add(x);
        }

        List<Integer> ans = new ArrayList<>();

        for (int x = min; x <= max; x++) {
            if (!set.contains(x)) {
                ans.add(x);
            }
        }

        return ans;
    }
}