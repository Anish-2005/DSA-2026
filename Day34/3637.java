//3637. Trionic Array I

//You are given an integer array nums of length n.

//An array is trionic if there exist indices 0 < p < q < n − 1 such that:

//nums[0...p] is strictly increasing,
//nums[p...q] is strictly decreasing,
//nums[q...n − 1] is strictly increasing.
//Return true if nums is trionic, otherwise return false.

class Solution {
    public boolean isTrionic(int[] nums) {
        int n = nums.length;
        if (n < 4) return false;

        int i = 0;

        while (i + 1 < n && nums[i] < nums[i + 1]) i++;
        if (i == 0 || i == n - 1) return false;

        while (i + 1 < n && nums[i] > nums[i + 1]) i++;
        if (i == n - 1) return false;

        while (i + 1 < n && nums[i] < nums[i + 1]) i++;

        return i == n - 1;
    }
}