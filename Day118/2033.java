/*2033. Minimum Operations to Make a Uni-Value Grid

You are given a 2D integer grid of size m x n and an integer x. In one operation, you can add x to or subtract x from any element in the grid.

A uni-value grid is a grid where all the elements of it are equal.

Return the minimum number of operations to make the grid uni-value. If it is not possible, return -1.
*/

import java.util.*;

class Solution {
    public int minOperations(int[][] grid, int x) {
        List<Integer> list = new ArrayList<>();
        
        int mod = grid[0][0] % x;
        
        for (int[] row : grid) {
            for (int val : row) {
                if (val % x != mod) return -1;
                list.add(val);
            }
        }
        
        Collections.sort(list);
        
        int median = list.get(list.size() / 2);
        int ops = 0;
        
        for (int val : list) {
            ops += Math.abs(val - median) / x;
        }
        
        return ops;
    }
}