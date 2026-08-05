/*
3310. Remove Methods From Project
You are maintaining a project that has n methods numbered from 0 to n - 1.

You are given two integers n and k, and a 2D integer array invocations,
 where invocations[i] = [ai, bi] indicates that method ai invokes method bi.

There is a known bug in method k. Method k, along with any method invoked by it,
 either directly or indirectly, are considered suspicious and we aim to remove them.

A group of methods can only be removed if no method outside the group invokes any methods within it.

Return an array containing all the remaining methods after removing all the suspicious methods. 
You may return the answer in any order. If it is not possible to remove all the suspicious methods, none should be removed.


Example 1:

Input: n = 4, k = 1, invocations = [[1,2],[0,1],[3,2]]
Output: [0,1,2,3]
*/

import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        // Build directed graph
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : invocations) {
            int u = edge[0];
            int v = edge[1];

            graph.get(u).add(v);
        }

        // Find all suspicious methods reachable from k
        boolean[] suspicious = new boolean[n];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(k);
        suspicious[k] = true;

        while (!queue.isEmpty()) {

            int current = queue.poll();

            for (int next : graph.get(current)) {

                if (!suspicious[next]) {
                    suspicious[next] = true;
                    queue.offer(next);
                }
            }
        }

        // Check if any NON-suspicious method invokes
        // a suspicious method.
        for (int[] edge : invocations) {

            int u = edge[0];
            int v = edge[1];

            if (!suspicious[u] && suspicious[v]) {

                // Cannot remove suspicious methods
                List<Integer> result = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    result.add(i);
                }

                return result;
            }
        }

        // Removal is possible:
        // return only non-suspicious methods
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            if (!suspicious[i]) {
                result.add(i);
            }
        }

        return result;
    }
}