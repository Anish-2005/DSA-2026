/*3741. Minimum Distance Between Three Equal Elements II

You are given an integer array nums.

A tuple (i, j, k) of 3 distinct indices is good if nums[i] == nums[j] == nums[k].

The distance of a good tuple is abs(i - j) + abs(j - k) + abs(k - i), where abs(x)
 denotes the absolute value of x.

Return an integer denoting the minimum possible distance of a good tuple. If no
 good tuples exist, return -1.

 

Example 1:

Input: nums = [1,2,1,1,3]

Output: 6

Explanation:

The minimum distance is achieved by the good tuple (0, 2, 3).

(0, 2, 3) is a good tuple because nums[0] == nums[2] == nums[3] == 1. 
Its distance is abs(0 - 2) + abs(2 - 3) + abs(3 - 0) = 2 + 1 + 3 = 6. */

import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        int ans = Integer.MAX_VALUE;

        for (List<Integer> list : map.values()) {
            if (list.size() < 3) continue;
            for (int i = 0; i + 2 < list.size(); i++) {
                int a = list.get(i);
                int b = list.get(i + 1);
                int c = list.get(i + 2);
                int d = Math.abs(a - b) + Math.abs(b - c) + Math.abs(c - a);
                ans = Math.min(ans, d);
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}