/*
3660. Jump Game IX

You are given an integer array nums.

From any index i, you can jump to another index j under the following rules:

Jump to index j where j > i is allowed only if nums[j] < nums[i].
Jump to index j where j < i is allowed only if nums[j] > nums[i].
For each index i, find the maximum value in nums that can be reached by following any sequence of valid jumps starting at i.

Return an array ans where ans[i] is the maximum value reachable starting from index i.
*/

class Solution {
    public int[] maxValue(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];

        int[] suffixMin = new int[n];
        suffixMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], nums[i]);
        }

        int start = 0;
        int prefixMax = nums[0];
        int compMax = nums[0];

        for (int i = 0; i < n; i++) {
            prefixMax = Math.max(prefixMax, nums[i]);
            compMax = Math.max(compMax, nums[i]);

            if (i == n - 1 || prefixMax <= suffixMin[i + 1]) {

                for (int j = start; j <= i; j++) {
                    ans[j] = compMax;
                }

                start = i + 1;

                if (start < n) {
                    prefixMax = nums[start];
                    compMax = nums[start];
                }
            }
        }

        return ans;
    }
}