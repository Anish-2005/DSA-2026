//3640. Trionic Array II

///You are given an integer array nums of length n.

//A trionic subarray is a contiguous subarray nums[l...r] (with 0 <= l < r < n) for which there exist indices l < p < q < r such that:

///nums[l...p] is strictly increasing,
//nums[p...q] is strictly decreasing,
//nums[q...r] is strictly increasing.
//Return the maximum sum of any trionic subarray in nums.

class Solution {
    public long maxSumTrionic(int[] nums) {
        int n = nums.length;
        long presum[] = new long[n+1];

        for(int i=1; i<=n; i++) {
            presum[i] = presum[i-1] + nums[i-1];
        }

        long maxsum = Long.MIN_VALUE;
        int i = 1;

        while(i < n-2) {
            int left = i-1;

            while(i < n && nums[i] > nums[i-1])
                i++;
            if(i == left+1) {
                i++;
                continue;
            }
            int pIndex = i-1;

            while(i < n && nums[i] < nums[i-1]) 
                i++;
            if(i == pIndex+1) {
                i++;
                continue;
            }
            int qIndex = i-1;

            while(i < n && nums[i] > nums[i-1])
                i++;
            if(i == qIndex+1) {
                i++;
                continue;
            }
            int right = i-1;

            long maxprefixsum = Long.MIN_VALUE;
            long minprefixsum = Long.MAX_VALUE;
            
            for(int j = left; j < pIndex; j++) {
                minprefixsum = Math.min(minprefixsum, presum[j]); 
            }
            
            for(int j = qIndex+1; j <= right; j++) {
                maxprefixsum = Math.max(maxprefixsum, presum[j+1]); 
            }
            
            maxsum = Math.max(maxsum, maxprefixsum - minprefixsum);
            i = qIndex + 1;
        }

        return maxsum;
    }
}