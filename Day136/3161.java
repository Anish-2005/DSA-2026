/*
3161. Block Placement Queries

There exists an infinite number line, with its origin at 0 and extending towards the positive x-axis.

You are given a 2D array queries, which contains two types of queries:

For a query of type 1, queries[i] = [1, x]. Build an obstacle at distance x from the origin. It is guaranteed that there is no obstacle at distance x when the query is asked.
For a query of type 2, queries[i] = [2, x, sz]. Check if it is possible to place a block of size sz anywhere in the range [0, x] on the line, such that the block entirely lies in the range [0, x]. A block cannot be placed if it intersects with any obstacle, but it may touch it. Note that you do not actually place the block. Queries are separate.
Return a boolean array results, where results[i] is true if you can place the block specified in the ith query of type 2, and false otherwise.

 

Example 1:

Input: queries = [[1,2],[2,3,3],[2,3,1],[2,2,2]]
Output: [false,true,true]
Explanation:
For query 0, place an obstacle at x = 2. A block of size at most 2 can be placed before x = 3.
*/

import java.util.*;

class Solution {

    static class SegmentTree {
        int[] tree;
        int n;

        SegmentTree(int n) {
            this.n = n;
            tree = new int[4 * n];
        }

        void update(int idx, int val) {
            update(1, 0, n - 1, idx, val);
        }

        void update(int node, int l, int r, int idx, int val) {
            if (l == r) {
                tree[node] = val;
                return;
            }

            int mid = (l + r) / 2;

            if (idx <= mid)
                update(node * 2, l, mid, idx, val);
            else
                update(node * 2 + 1, mid + 1, r, idx, val);

            tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
        }

        int query(int ql, int qr) {
            return query(1, 0, n - 1, ql, qr);
        }

        int query(int node, int l, int r, int ql, int qr) {
            if (ql > r || qr < l)
                return 0;

            if (ql <= l && r <= qr)
                return tree[node];

            int mid = (l + r) / 2;

            return Math.max(
                query(node * 2, l, mid, ql, qr),
                query(node * 2 + 1, mid + 1, r, ql, qr)
            );
        }
    }

    public List<Boolean> getResults(int[][] queries) {

        int maxX = 0;

        for (int[] q : queries) {
            maxX = Math.max(maxX, q[1]);
        }

        TreeSet<Integer> set = new TreeSet<>();
        set.add(0);

        SegmentTree seg = new SegmentTree(maxX + 2);

        List<Boolean> ans = new ArrayList<>();

        for (int[] q : queries) {

            if (q[0] == 1) {

                int x = q[1];

                Integer prev = set.floor(x);
                Integer next = set.ceiling(x);

                if (prev != null) {
                    seg.update(x, x - prev);
                }

                if (next != null) {
                    seg.update(next, next - x);
                }

                set.add(x);

            } else {

                int x = q[1];
                int sz = q[2];

                Integer prev = set.floor(x);

                int best = 0;

                if (prev != null) {
                    best = Math.max(best, x - prev);
                }

                best = Math.max(best, seg.query(0, x));

                ans.add(best >= sz);
            }
        }

        return ans;
    }
}