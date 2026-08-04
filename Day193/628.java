/*628. Maximum Product of Three Numbers
Easy
Topics
premium lock icon
Companies
Given an integer array nums, find three numbers whose product is maximum and return the maximum product.

 

Example 1:

Input: nums = [1,2,3]
Output: 6
Example 2:

Input: nums = [1,2,3,4]
Output: 24
Example 3:

Input: nums = [-1,-2,-3]
Output: -6 */


import java.util.Arrays;

class Solution {
    public int maximumProduct(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;

        int a = nums[n - 1] * nums[n - 2] * nums[n - 3];

        int b = nums[0] * nums[1] * nums[n - 1];

        return Math.max(a, b);
    }
}

