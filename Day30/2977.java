//2977. Minimum Cost to Convert String II

//You are given two 0-indexed strings source and target, both of length n and consisting of lowercase English characters. You are also given two 0-indexed string arrays original and changed, and an integer array cost, where cost[i] represents the cost of converting the string original[i] to the string changed[i].

//You start with the string source. In one operation, you can pick a substring x from the string, and change it to y at a cost of z if there exists any index j such that cost[j] == z, original[j] == x, and changed[j] == y. You are allowed to do any number of operations, but any pair of operations must satisfy either of these two conditions:

//The substrings picked in the operations are source[a..b] and source[c..d] with either b < c or d < a. In other words, the indices picked in both operations are disjoint.
//The substrings picked in the operations are source[a..b] and source[c..d] with a == c and b == d. In other words, the indices picked in both operations are identical.
//Return the minimum cost to convert the string source to the string target using any number of operations. If it is impossible to convert source to target, return -1.

//Note that there may exist indices i, j such that original[j] == original[i] and changed[j] == changed[i].

import java.util.*;

class Solution {
    public long minimumCost(String source, String target, String[] original, String[] changed, int[] cost) {
        int n = source.length();
        long INF = Long.MAX_VALUE / 4;

        Map<String, Integer> id = new HashMap<>();
        int idx = 0;
        for (int i = 0; i < original.length; i++) {
            if (!id.containsKey(original[i])) id.put(original[i], idx++);
            if (!id.containsKey(changed[i])) id.put(changed[i], idx++);
        }

        int m = idx;
        long[][] dist = new long[m][m];
        for (int i = 0; i < m; i++) Arrays.fill(dist[i], INF);
        for (int i = 0; i < m; i++) dist[i][i] = 0;

        for (int i = 0; i < original.length; i++) {
            int u = id.get(original[i]);
            int v = id.get(changed[i]);
            dist[u][v] = Math.min(dist[u][v], cost[i]);
        }

        for (int k = 0; k < m; k++)
            for (int i = 0; i < m; i++)
                for (int j = 0; j < m; j++)
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];

        long[] dp = new long[n + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            if (dp[i] == INF) continue;

            if (source.charAt(i) == target.charAt(i)) {
                dp[i + 1] = Math.min(dp[i + 1], dp[i]);
            }

            for (String s : id.keySet()) {
                int len = s.length();
                if (i + len > n) continue;
                if (!source.substring(i, i + len).equals(s)) continue;
                String t = target.substring(i, i + len);
                if (!id.containsKey(t)) continue;
                long c = dist[id.get(s)][id.get(t)];
                if (c < INF) {
                    dp[i + len] = Math.min(dp[i + len], dp[i] + c);
                }
            }
        }

        return dp[n] >= INF ? -1 : dp[n];
    }
}
