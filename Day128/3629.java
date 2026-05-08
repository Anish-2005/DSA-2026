/*
3629. Minimum Jumps to Reach End via Prime Teleportation

You are given an integer array nums of length n.

You start at index 0, and your goal is to reach index n - 1.

From any index i, you may perform one of the following operations:

Adjacent Step: Jump to index i + 1 or i - 1, if the index is within bounds.
Prime Teleportation: If nums[i] is a prime number p, you may instantly jump to any index j != i such that nums[j] % p == 0.
Return the minimum number of jumps required to reach index n - 1.

 

Example 1:

Input: nums = [1,2,4,6]

Output: 2

Explanation:

One optimal sequence of jumps is:

Start at index i = 0. Take an adjacent step to index 1.
At index i = 1, nums[1] = 2 is a prime number. Therefore, we teleport to index i = 3 as nums[3] = 6 is divisible by 2.
Thus, the answer is 2.

Example 2:

Input: nums = [2,3,4,7,9]

Output: 2

Explanation:

One optimal sequence of jumps is:

Start at index i = 0. Take an adjacent step to index i = 1.
At index i = 1, nums[1] = 3 is a prime number. Therefore, we teleport to index i = 4 since nums[4] = 9 is divisible by 3.
Thus, the answer is 2. */

import java.util.*;

class Solution {
    public int minJumps(int[] nums) {
        int n = nums.length;
        
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            Set<Integer> factors = primeFactors(nums[i]);
            
            for (int f : factors) {
                map.computeIfAbsent(f, k -> new ArrayList<>()).add(i);
            }
        }
        
        Queue<Integer> q = new LinkedList<>();
        boolean[] vis = new boolean[n];
        Set<Integer> usedPrime = new HashSet<>();
        
        q.offer(0);
        vis[0] = true;
        
        int steps = 0;
        
        while (!q.isEmpty()) {
            int size = q.size();
            
            while (size-- > 0) {
                int i = q.poll();
                
                if (i == n - 1) return steps;
                
                if (i - 1 >= 0 && !vis[i - 1]) {
                    vis[i - 1] = true;
                    q.offer(i - 1);
                }
                
                if (i + 1 < n && !vis[i + 1]) {
                    vis[i + 1] = true;
                    q.offer(i + 1);
                }
                
                if (isPrime(nums[i]) && !usedPrime.contains(nums[i])) {
                    usedPrime.add(nums[i]);
                    
                    List<Integer> list = map.getOrDefault(nums[i], new ArrayList<>());
                    
                    for (int idx : list) {
                        if (!vis[idx]) {
                            vis[idx] = true;
                            q.offer(idx);
                        }
                    }
                }
            }
            
            steps++;
        }
        
        return -1;
    }
    
    private boolean isPrime(int x) {
        if (x < 2) return false;
        
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) return false;
        }
        
        return true;
    }
    
    private Set<Integer> primeFactors(int x) {
        Set<Integer> set = new HashSet<>();
        
        for (int i = 2; i * i <= x; i++) {
            while (x % i == 0) {
                set.add(i);
                x /= i;
            }
        }
        
        if (x > 1) set.add(x);
        
        return set;
    }
}