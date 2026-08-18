/* 3471. Find the Largest Almost Missing Integer
Solved
Easy
Topics
premium lock icon
Companies
Hint
You are given an integer array nums and an integer k.

An integer x is almost missing from nums if x appears in exactly one subarray of size k within nums.

Return the largest almost missing integer from nums. If no such integer exists, return -1.

A subarray is a contiguous sequence of elements within an array.
 

Example 1:

Input: nums = [3,9,2,1,7], k = 3 

Output: 7 */

class Solution {
    public int largestInteger(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();

        // Every possible starting position of a subarray of size k
        for (int i = 0; i <= nums.length - k; i++) {

            // Avoid counting the same number twice
            // inside the same subarray
            Set<Integer> seen = new HashSet<>();

            for (int j = i; j < i + k; j++) {
                if (seen.add(nums[j])) {
                    count.put(nums[j], count.getOrDefault(nums[j], 0) + 1);
                }
            }
        }

        int ans = -1;

        for (int x : count.keySet()) {
            if (count.get(x) == 1) {
                ans = Math.max(ans, x);
            }
        }

        return ans;
    }
}