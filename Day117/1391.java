/*
1391. Check if There is a Valid Path in a Grid

You are given an m x n grid. Each cell of grid represents a street. The street of grid[i][j] can be:

1 which means a street connecting the left cell and the right cell.
2 which means a street connecting the upper cell and the lower cell.
3 which means a street connecting the left cell and the lower cell.
4 which means a street connecting the right cell and the lower cell.
5 which means a street connecting the left cell and the upper cell.
6 which means a street connecting the right cell and the upper cell.
*/ 

import java.util.*;

class Solution {
    public boolean hasValidPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        int[][][] dirs = {
            {},
            {{0,-1},{0,1}},
            {{-1,0},{1,0}},
            {{0,-1},{1,0}},
            {{0,1},{1,0}},
            {{0,-1},{-1,0}},
            {{0,1},{-1,0}}
        };

        Map<String, String> opp = new HashMap<>();
        opp.put("0,-1","0,1");
        opp.put("0,1","0,-1");
        opp.put("-1,0","1,0");
        opp.put("1,0","-1,0");

        boolean[][] vis = new boolean[m][n];
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0,0});
        vis[0][0] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];

            if(r == m-1 && c == n-1) return true;

            for(int[] d : dirs[grid[r][c]]) {
                int nr = r + d[0], nc = c + d[1];
                if(nr>=0 && nr<m && nc>=0 && nc<n && !vis[nr][nc]) {
                    String key = d[0] + "," + d[1];
                    String rev = opp.get(key);
                    for(int[] nd : dirs[grid[nr][nc]]) {
                        if((nd[0] + "," + nd[1]).equals(rev)) {
                            vis[nr][nc] = true;
                            q.offer(new int[]{nr,nc});
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }
}