//3379. Transformed Array

//You are given an integer array nums that represents a circular array. Your task is to create a new array result of the same size, following these rules:

//For each index i (where 0 <= i < nums.length), perform the following independent actions:
//If nums[i] > 0: Start at index i and move nums[i] steps to the right in the circular array. Set result[i] to the value of the index where you land.
//If nums[i] < 0: Start at index i and move abs(nums[i]) steps to the left in the circular array. Set result[i] to the value of the index where you land.
//If nums[i] == 0: Set result[i] to nums[i].
//Return the new array result.
//
//Note: Since nums is circular, moving past the last element wraps around to the beginning, and moving before the first element wraps back to the end.

class Solution {
    public int[] constructTransformedArray(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int move = nums[i];
            if (move == 0) {
                result[i] = 0;
            } else {
                int idx = (i + move) % n;
                if (idx < 0) idx += n;
                result[i] = nums[idx];
            }
        }
        return result;
    }
}