/*3488. Closest Equal Element Queries

You are given a circular array nums and an array queries.

For each query i, you have to find the following:

The minimum distance between the element at index queries[i] and any other index j in the circular array, where nums[j] == nums[queries[i]]. If no such index exists, the answer for that query should be -1.
Return an array answer of the same size as queries, where answer[i] represents the result for query i.

 

Example 1:

Input: nums = [1,3,1,4,1,3,2], queries = [0,3,5]

Output: [2,-1,3]

Explanation:

Query 0: The element at queries[0] = 0 is nums[0] = 1. The nearest index with the same value is 2, and the distance between them is 2.
Query 1: The element at queries[1] = 3 is nums[3] = 4. No other index contains 4, so the result is -1.
Query 2: The element at queries[2] = 5 is nums[5] = 3. The nearest index with the same value is 1, and the distance between them is 3 (following the circular path: 5 -> 6 -> 0 -> 1).
*/

import java.util.*;

class Solution {
    public List<Integer> solveQueries(int[] nums, int[] queries) {
        int n = nums.length;
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
        
        List<Integer> ans = new ArrayList<>();
        
        for (int idx : queries) {
            List<Integer> list = map.get(nums[idx]);
            
            if (list.size() == 1) {
                ans.add(-1);
                continue;
            }
            
            int pos = Collections.binarySearch(list, idx);
            
            int left = list.get((pos - 1 + list.size()) % list.size());
            int right = list.get((pos + 1) % list.size());
            
            int d1 = Math.abs(idx - left);
            int d2 = Math.abs(idx - right);
            
            d1 = Math.min(d1, n - d1);
            d2 = Math.min(d2, n - d2);
            
            ans.add(Math.min(d1, d2));
        }
        
        return ans;
    }
}