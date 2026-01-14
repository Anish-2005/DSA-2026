//2348. Number of Zero-Filled Subarrays

//Given an integer array nums, return the number of subarrays filled with 0.

//A subarray is a contiguous non-empty sequence of elements within an array.

class Solution {
    public long zeroFilledSubarray(int[] nums) {
        long res = 0, cnt = 0;
        int i = 0, n = nums.length;

        while (i < n) {
            if (nums[i++] == 0) {
                res += ++cnt;
            } else {
                cnt = 0;
            }
        }
        return res;
    }
}

