//3634. Minimum Removals to Balance Array

//You are given an integer array nums and an integer k.

//An array is considered balanced if the value of its maximum element is at most k times the minimum element.

//You may remove any number of elements from nums​​​​​​​ without making it empty.

//Return the minimum number of elements to remove so that the remaining array is balanced.

//Note: An array of size 1 is considered balanced as its maximum and minimum are equal, and the condition always holds true.

import java.util.*;

class Solution {
    public int minRemoval(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int ans = n;
        int l = 0;
        for (int r = 0; r < n; r++) {
            while ((long) nums[r] > (long) k * nums[l]) {
                l++;
            }
            ans = Math.min(ans, n - (r - l + 1));
        }
        return ans;
    }
}