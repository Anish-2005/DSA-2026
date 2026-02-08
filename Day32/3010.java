//3010. Divide an Array Into Subarrays With Minimum Cost I

//You are given an array of integers nums of length n.

//The cost of an array is the value of its first element. For example, the cost of [1,2,3] is 1 while the cost of [3,4,1] is 3.

//You need to divide nums into 3 disjoint contiguous subarrays.

//Return the minimum possible sum of the cost of these subarrays.

class Solution {
    public int minimumCost(int[] nums) {
        int n = nums.length;
        int first = nums[0];
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int i = 1; i < n; i++) {
            if (nums[i] < min1) {
                min2 = min1;
                min1 = nums[i];
            } else if (nums[i] < min2) {
                min2 = nums[i];
            }
        }

        return first + min1 + min2;
    }
}
