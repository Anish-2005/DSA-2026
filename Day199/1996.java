/* 2996. Smallest Missing Integer Greater Than Sequential Prefix Sum
Easy
Topics
premium lock icon
Companies
Hint
You are given a 0-indexed array of integers nums.

A prefix nums[0..i] is sequential if, for all 1 <= j <= i, nums[j] = nums[j - 1] + 1. In particular, the prefix consisting only of nums[0] is sequential.

Return the smallest integer x missing from nums such that x is greater than or equal to the sum of the longest sequential prefix.

 

Example 1:

Input: nums = [1,2,3,2,5]
Output: 6
Explanation: The longest sequential prefix of nums is [1,2,3] with a sum of 6. 6 is not in the array, therefore 6 is the smallest missing integer greater than or equal to the sum of the longest sequential prefix. */


import java.util.*;

class Solution {
    public int missingInteger(int[] nums) {
        // Step 1: Find sum of longest sequential prefix
        int sum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }

        // Step 2: Store all numbers in a HashSet
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        // Step 3: Find smallest missing integer >= sum
        while (set.contains(sum)) {
            sum++;
        }

        return sum;
    }
}