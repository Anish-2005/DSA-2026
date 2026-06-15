/*
3558. Number of Ways to Assign Edge Weights I

There is an undirected tree with n nodes labeled from 1 to n, rooted at node 1. The tree is represented by a 2D integer array edges of length n - 1, where edges[i] = [ui, vi] indicates that there is an edge between nodes ui and vi.

Initially, all edges have a weight of 0. You must assign each edge a weight of either 1 or 2.

The cost of a path between any two nodes u and v is the total weight of all edges in the path connecting them.

Select any one node x at the maximum depth. Return the number of ways to assign edge weights in the path from node 1 to x such that its total cost is odd.

Since the answer may be large, return it modulo 109 + 7.

Note: Ignore all edges not in the path from node 1 to x.

 

Example 1:



Input: edges = [[1,2]]

Output: 1

Explanation:

The path from Node 1 to Node 2 consists of one edge (1 → 2).
Assigning weight 1 makes the cost odd, while 2 makes it even. Thus, the number of valid assignments is 1. */

class Solution {
    static final long MOD = 1000000007L;

    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length + 1;

        java.util.List<Integer>[] g = new java.util.ArrayList[n + 1];
        for (int i = 1; i <= n; i++) g[i] = new java.util.ArrayList<>();

        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }

        java.util.Queue<Integer> q = new java.util.LinkedList<>();
        boolean[] vis = new boolean[n + 1];
        q.offer(1);
        vis[1] = true;

        int depth = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int u = q.poll();
                for (int v : g[u]) {
                    if (!vis[v]) {
                        vis[v] = true;
                        q.offer(v);
                    }
                }
            }
            if (!q.isEmpty()) depth++;
        }

        if (depth == 0) return 0;

        long ans = 1;
        long base = 2;
        int exp = depth - 1;

        while (exp > 0) {
            if ((exp & 1) == 1) ans = (ans * base) % MOD;
            base = (base * base) % MOD;
            exp >>= 1;
        }

        return (int) ans;
    }
}