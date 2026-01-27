//3650. Minimum Cost Path with Edge Reversals

//You are given a directed, weighted graph with n nodes 
// labeled from 0 to n - 1, and an array edges where edges[i]
//  = [ui, vi, wi] represents a directed edge from node ui to node vi with cost wi.

//Each node ui has a switch that can be used at most once:
//  when you arrive at ui and have not yet used its switch, 
// you may activate it on one of its incoming edges vi → ui 
// reverse that edge to ui → vi and immediately traverse it.

//The reversal is only valid for that single move, and using a 
// reversed edge costs 2 * wi.

//Return the minimum total cost to travel from node 0 to node n - 1.
//  If it is not possible, return -1.

import java.util.*;

class Solution {

    static class Edge {
        int to;
        int cost;
        Edge(int t, int c) {
            to = t;
            cost = c;
        }
    }

    static class State {
        int node;
        int used;
        long dist;
        State(int n, int u, long d) {
            node = n;
            used = u;
            dist = d;
        }
    }

    public int minCost(int n, int[][] edges) {
        List<Edge>[] graph = new ArrayList[n];
        List<Edge>[] reverse = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            reverse[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(new Edge(e[1], e[2]));
            reverse[e[1]].add(new Edge(e[0], e[2]));
        }

        long[][] dist = new long[n][2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.dist));
        dist[0][0] = 0;
        pq.offer(new State(0, 0, 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            int u = cur.node;
            int used = cur.used;
            long d = cur.dist;

            if (d > dist[u][used]) continue;

            // Normal edges
            for (Edge e : graph[u]) {
                if (dist[e.to][used] > d + e.cost) {
                    dist[e.to][used] = d + e.cost;
                    pq.offer(new State(e.to, used, dist[e.to][used]));
                }
            }

            // Reverse edges (only if switch unused)
            if (used == 0) {
                for (Edge e : reverse[u]) {
                    long nd = d + 2L * e.cost;
                    if (dist[e.to][1] > nd) {
                        dist[e.to][1] = nd;
                        pq.offer(new State(e.to, 1, nd));
                    }
                }
            }
        }

        long ans = Math.min(dist[n - 1][0], dist[n - 1][1]);
        return ans == Long.MAX_VALUE ? -1 : (int) ans;
    }
}
