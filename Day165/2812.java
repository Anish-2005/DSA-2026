/*2812. Find the Safest Path in a Grid

You are given a 0-indexed 2D matrix grid of size n x n, where (r, c) represents:

A cell containing a thief if grid[r][c] = 1
An empty cell if grid[r][c] = 0
You are initially positioned at cell (0, 0). In one move, you can move to any adjacent cell in the grid, including cells containing thieves.

The safeness factor of a path on the grid is defined as the minimum manhattan distance from any cell in the path to any thief in the grid.

Return the maximum safeness factor of all paths leading to cell (n - 1, n - 1).

An adjacent cell of cell (r, c), is one of the cells (r, c + 1), (r, c - 1), (r + 1, c) and (r - 1, c) if it exists.

The Manhattan distance between two cells (a, b) and (x, y) is equal to |a - x| + |b - y|, where |val| denotes the absolute value of val.

 

Example 1:


Input: grid = [[1,0,0],[0,0,0],[0,0,1]]
Output: 0
Explanation: All paths from (0, 0) to (n - 1, n - 1) go through the thieves in cells (0, 0) and (n - 1, n - 1). */


class Solution {
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();

        int[][] dist = new int[n][n];
        Queue<int[]> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        // Multi-source BFS
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    dist[i][j] = 0;
                    q.offer(new int[]{i, j});
                }
            }
        }

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];

            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n)
                    continue;

                if (dist[nx][ny] > dist[x][y] + 1) {
                    dist[nx][ny] = dist[x][y] + 1;
                    q.offer(new int[]{nx, ny});
                }
            }
        }

        int low = 0, high = 2 * n, ans = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canReach(dist, mid)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }

    private boolean canReach(int[][] dist, int safe) {
        int n = dist.length;

        if (dist[0][0] < safe)
            return false;

        Queue<int[]> q = new LinkedList<>();
        boolean[][] vis = new boolean[n][n];

        q.offer(new int[]{0, 0});
        vis[0][0] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];

            if (x == n - 1 && y == n - 1)
                return true;

            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n)
                    continue;

                if (vis[nx][ny] || dist[nx][ny] < safe)
                    continue;

                vis[nx][ny] = true;
                q.offer(new int[]{nx, ny});
            }
        }

        return false;
    }
}