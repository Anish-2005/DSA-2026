//3651. Minimum Cost Path with Teleportations

//You are given a m x n 2D integer array grid and an integer k. You start at the top-left cell (0, 0) and your goal is to reach the bottom‐right cell (m - 1, n - 1).

//There are two types of moves available:

//Normal move: You can move right or down from your current cell (i, j), i.e. you can move to (i, j + 1) (right) or (i + 1, j) (down). The cost is the value of the destination cell.

//Teleportation: You can teleport from any cell (i, j), to any cell (x, y) such that grid[x][y] <= grid[i][j]; the cost of this move is 0. You may teleport at most k times.

//Return the minimum total cost to reach cell (m - 1, n - 1) from (0, 0).
import java.util.*;

class Solution {
    static class State {
        int r, c, t;
        long d;
        State(int r, int c, int t, long d) {
            this.r = r;
            this.c = c;
            this.t = t;
            this.d = d;
        }
    }

    public int minCost(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        long INF = (long) 1e18;

        long[][][] dist = new long[k + 1][m][n];
        for (int t = 0; t <= k; t++)
            for (int i = 0; i < m; i++)
                Arrays.fill(dist[t][i], INF);

        List<int[]> cells = new ArrayList<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                cells.add(new int[]{grid[i][j], i, j});

        cells.sort(Comparator.comparingInt(a -> a[0]));

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.d));
        dist[0][0][0] = 0;
        pq.add(new State(0, 0, 0, 0));

        int[] dr = {0, 1};
        int[] dc = {1, 0};

        int[] ptr = new int[k + 1];

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.d != dist[cur.t][cur.r][cur.c]) continue;

            if (cur.r == m - 1 && cur.c == n - 1) return (int) cur.d;

            for (int d = 0; d < 2; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                if (nr < m && nc < n) {
                    long nd = cur.d + grid[nr][nc];
                    if (nd < dist[cur.t][nr][nc]) {
                        dist[cur.t][nr][nc] = nd;
                        pq.add(new State(nr, nc, cur.t, nd));
                    }
                }
            }

            if (cur.t < k) {
                int nt = cur.t + 1;
                while (ptr[nt] < cells.size() && cells.get(ptr[nt])[0] <= grid[cur.r][cur.c]) {
                    int[] cell = cells.get(ptr[nt]++);
                    int r = cell[1], c = cell[2];
                    if (cur.d < dist[nt][r][c]) {
                        dist[nt][r][c] = cur.d;
                        pq.add(new State(r, c, nt, cur.d));
                    }
                }
            }
        }

        return -1;
    }
}
