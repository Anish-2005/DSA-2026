//3719. Longest Balanced Subarray I
//You are given an integer array nums.

//A subarray is called balanced if the number of distinct even numbers in the subarray is equal to the number of distinct odd numbers.

//Return the length of the longest balanced subarray.

 

import java.util.*;

class Solution {
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            Set<Integer> even = new HashSet<>();
            Set<Integer> odd = new HashSet<>();

            for (int j = i; j < n; j++) {
                int x = nums[j];
                if ((x & 1) == 0) even.add(x);
                else odd.add(x);

                if (even.size() == odd.size())
                    ans = Math.max(ans, j - i + 1);
            }
        }
        return ans;
    }
}