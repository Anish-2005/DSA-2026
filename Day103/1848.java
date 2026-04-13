/*1848. Minimum Distance to the Target Element

Given an integer array nums (0-indexed) and two integers target and start, find an index i such that nums[i] == target and abs(i - start) is minimized. Note that abs(x) is the absolute value of x.

Return abs(i - start).

It is guaranteed that target exists in nums. */

class Solution {
    public int getMinDistance(int[] nums, int target, int start) {
        
        int minDist = Integer.MAX_VALUE;

        for(int i = 0; i < nums.length; i++){
            if(nums[i] == target){
                minDist = Math.min(minDist, Math.abs(i - start));
            }
        }

        return minDist;
    }
}