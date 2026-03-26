/*3548. Equal Sum Grid Partition II
Hard
Topics
premium lock icon
Companies
Hint
You are given an m x n matrix grid of positive integers. Your task 
is to determine if it is possible to make either one horizontal or 
one vertical cut on the grid such that:

Each of the two resulting sections formed by the cut is non-empty.
The sum of elements in both sections is equal, or can be made equal by 
discounting at most one single cell in total (from either section).
If a cell is discounted, the rest of the section must remain connected.
Return true if such a partition exists; otherwise, return false.

Note: A section is connected if every cell in it can be reached from any 
other cell by moving up, down, left, or right through other cells in the section.

 

Example 1:

Input: grid = [[1,4],[2,3]]

Output: true
*/
\


import java.util.*;

class Solution {

    public boolean canPartitionGrid(int[][] grid) {

        int m = grid.length, n = grid[0].length;

        long total = 0;

        HashMap<Integer,Integer> bottom = new HashMap<>();

        for(int[] r : grid)
            for(int v : r){
                total += v;
                bottom.put(v,bottom.getOrDefault(v,0)+1);
            }

        HashMap<Integer,Integer> top = new HashMap<>();

        long topSum = 0;

        for(int r=0;r<m-1;r++){

            for(int c=0;c<n;c++){

                int v = grid[r][c];

                topSum += v;

                top.put(v,top.getOrDefault(v,0)+1);

                bottom.put(v,bottom.get(v)-1);

                if(bottom.get(v)==0) bottom.remove(v);
            }

            long bottomSum = total-topSum;

            if(topSum==bottomSum) return true;

            long diff = Math.abs(topSum-bottomSum);

            if(topSum>bottomSum){

                if(top.containsKey((int)diff) && canRemove(grid,0,r,0,n-1,diff))
                    return true;

            }else{

                if(bottom.containsKey((int)diff) && canRemove(grid,r+1,m-1,0,n-1,diff))
                    return true;
            }
        }

        HashMap<Integer,Integer> left = new HashMap<>();
        HashMap<Integer,Integer> right = new HashMap<>();

        for(int[] r:grid)
            for(int v:r)
                right.put(v,right.getOrDefault(v,0)+1);

        long leftSum = 0;

        for(int c=0;c<n-1;c++){

            for(int r=0;r<m;r++){

                int v = grid[r][c];

                leftSum += v;

                left.put(v,left.getOrDefault(v,0)+1);

                right.put(v,right.get(v)-1);

                if(right.get(v)==0) right.remove(v);
            }

            long rightSum = total-leftSum;

            if(leftSum==rightSum) return true;

            long diff = Math.abs(leftSum-rightSum);

            if(leftSum>rightSum){

                if(left.containsKey((int)diff) && canRemove(grid,0,m-1,0,c,diff))
                    return true;

            }else{

                if(right.containsKey((int)diff) && canRemove(grid,0,m-1,c+1,n-1,diff))
                    return true;
            }
        }

        return false;
    }

   boolean canRemove(int[][] g,int r1,int r2,int c1,int c2,long diff){

    int h = r2 - r1 + 1;
    int w = c2 - c1 + 1;

    if(h==1 && w==1)
        return g[r1][c1]==diff;

    if(h==1)
        return g[r1][c1]==diff || g[r1][c2]==diff;

    if(w==1)
        return g[r1][c1]==diff || g[r2][c1]==diff;

    for(int r=r1;r<=r2;r++)
        for(int c=c1;c<=c2;c++)
            if(g[r][c]==diff)
                return true;

    return false;
}
}
