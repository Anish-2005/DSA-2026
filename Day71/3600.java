/*3600. Maximize Spanning Tree Stability with Upgrades

You are given an integer n, representing n nodes numbered from 0 to n - 1 
and a list of edges, where edges[i] = [ui, vi, si, musti]:

ui and vi indicates an undirected edge between nodes ui and vi.
si is the strength of the edge.
musti is an integer (0 or 1). If musti == 1, the edge must be included in 
the spanning tree. These edges cannot be upgraded.
You are also given an integer k, the maximum number of upgrades you can perform. 
Each upgrade doubles the strength of an edge, and each eligible edge 
(with musti == 0) can be upgraded at most once.

The stability of a spanning tree is defined as the minimum strength score among 
all edges included in it.

Return the maximum possible stability of any valid spanning tree. If it is 
impossible to connect all nodes, return -1.

Note: A spanning tree of a graph with n nodes is a subset of the edges that 
connects all nodes together (i.e. the graph is connected) without forming any 
cycles, and uses exactly n - 1 edges.
*/

import java.util.*;

class Solution {

    // Disjoint Set Union (Union-Find) to manage connected components.
    static class DSU {
        // parent[i] stores the representative parent of node i.
        // rank[i] stores a small tree-height heuristic for union by rank.
        int[] parent, rank;

        // Initialize DSU with n separate components.
        DSU(int n) {
            // Allocate parent array.
            parent = new int[n];
            // Allocate rank array (all zero initially).
            rank = new int[n];
            // Initially, every node is its own parent (separate set).
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        // Find the representative of x with path compression.
        int find(int x) {
            // If x is not a root, recursively find root and compress path.
            if (parent[x] != x) parent[x] = find(parent[x]);
            // Return the root representative.
            return parent[x];
        }

        // Union two nodes; returns true only if a merge happened.
        boolean union(int a, int b) {
            // Find roots of both nodes.
            int pa = find(a), pb = find(b);
            // Already in the same set -> adding this edge would create a cycle.
            if (pa == pb) return false;

            // Attach smaller-rank tree under larger-rank tree.
            if (rank[pa] < rank[pb]) parent[pa] = pb;
            else if (rank[pb] < rank[pa]) parent[pb] = pa;
            else {
                // Same rank: choose one as parent and increase its rank.
                parent[pb] = pa;
                rank[pa]++;
            }
            // Successful merge.
            return true;
        }
    }

    // Binary search for the maximum feasible stability value.
    public int maxStability(int n, int[][] edges, int k) {

        // Search range for stability: [0, max possible doubled strength].
        int left = 0, right = 0;
        // right bound uses doubled value because one upgrade can double an edge.
        for (int[] e : edges) right = Math.max(right, e[2] * 2);

        // Best valid stability found so far.
        int ans = -1;

        // Standard binary search on answer space.
        while (left <= right) {
            // Candidate stability to test.
            int mid = (left + right) / 2;

            // If we can build a valid spanning tree with min edge >= mid...
            if (can(n, edges, k, mid)) {
                // ...record it and try higher stability.
                ans = mid;
                left = mid + 1;
            } else {
                // Otherwise, lower the target stability.
                right = mid - 1;
            }
        }

        // Return maximum feasible stability (or -1 if impossible).
        return ans;
    }

    // Check whether target stability is achievable.
    boolean can(int n, int[][] edges, int k, int target) {

        // Fresh DSU for this feasibility test.
        DSU dsu = new DSU(n);
        // Number of upgrades used so far.
        int upgrades = 0;
        // Number of edges currently accepted into the spanning structure.
        int count = 0;

        // Edges already strong enough without upgrade.
        List<int[]> good = new ArrayList<>();
        // Edges that can reach target only after one upgrade.
        List<int[]> upgrade = new ArrayList<>();

        // First pass: force must-include edges and classify optional edges.
        for (int[] e : edges) {
            // Unpack edge fields: endpoints, strength, must-flag.
            int u = e[0], v = e[1], s = e[2], must = e[3];

            // Mandatory edges must be included and cannot be upgraded.
            if (must == 1) {
                // If mandatory edge is below target, this target is impossible.
                if (s < target) return false;
                // If mandatory edges form a cycle, no valid spanning tree exists.
                if (!dsu.union(u, v)) return false;
                // Count this mandatory edge in the spanning structure.
                count++;
            } else {
                // Optional edge already meets target without upgrade.
                if (s >= target) good.add(e);
                // Optional edge can meet target after one upgrade.
                else if (s * 2 >= target) upgrade.add(e);
            }
        }

        // Add all strong optional edges greedily (no upgrade cost).
        for (int[] e : good) {
            // Only count edge if it connects two different components.
            if (dsu.union(e[0], e[1])) count++;
        }

        // Then use upgradeable edges while we still have upgrade budget.
        for (int[] e : upgrade) {
            // Stop if we already used the maximum allowed upgrades.
            if (upgrades == k) break;
            // Use edge only if it helps connect components.
            if (dsu.union(e[0], e[1])) {
                // Spend one upgrade for this selected edge.
                upgrades++;
                // Count this edge as part of spanning structure.
                count++;
            }
        }

        // A spanning tree on n nodes must have exactly n - 1 selected edges.
        return count == n - 1;
    }
}