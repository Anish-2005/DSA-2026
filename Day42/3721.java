//3721. Longest Balanced Subarray II

//You are given an integer array nums.

//A subarray is called balanced if the number of distinct even numbers in the subarray is equal to the number of distinct odd numbers.

//Return the length of the longest balanced subarray.

 

class Solution {
    static class SegmentTree {
        int[] min, max, lazy;
        int n;

        SegmentTree(int n) {
            this.n = n;
            min = new int[4 * n];
            max = new int[4 * n];
            lazy = new int[4 * n];
        }

        void push(int node) {
            if (lazy[node] != 0) {
                lazy[2 * node] += lazy[node];
                min[2 * node] += lazy[node];
                max[2 * node] += lazy[node];
                lazy[2 * node + 1] += lazy[node];
                min[2 * node + 1] += lazy[node];
                max[2 * node + 1] += lazy[node];
                lazy[node] = 0;
            }
        }

        void update(int node, int start, int end, int l, int r, int val) {
            if (l > end || r < start) return;
            if (l <= start && end <= r) {
                lazy[node] += val;
                min[node] += val;
                max[node] += val;
                return;
            }
            push(node);
            int mid = (start + end) / 2;
            update(2 * node, start, mid, l, r, val);
            update(2 * node + 1, mid + 1, end, l, r, val);
            min[node] = Math.min(min[2 * node], min[2 * node + 1]);
            max[node] = Math.max(max[2 * node], max[2 * node + 1]);
        }

        int findFirstZero(int node, int start, int end, int rLimit) {
            if (start > rLimit || min[node] > 0 || max[node] < 0) return -1;
            if (start == end) return min[node] == 0 ? start : -1;
            push(node);
            int mid = (start + end) / 2;
            int res = findFirstZero(2 * node, start, mid, rLimit);
            if (res != -1) return res;
            return findFirstZero(2 * node + 1, mid + 1, end, rLimit);
        }
    }

    public int longestBalanced(int[] nums) {
        int n = nums.length;
        SegmentTree st = new SegmentTree(n);
        int[] lastPos = new int[100001];
        java.util.Arrays.fill(lastPos, -1);
        int maxLen = 0;

        for (int r = 0; r < n; r++) {
            int val = nums[r];
            int prev = lastPos[val];
            int diff = (val % 2 == 0) ? 1 : -1;
            st.update(1, 0, n - 1, prev + 1, r, diff);
            int l = st.findFirstZero(1, 0, n - 1, r);
            if (l != -1) maxLen = Math.max(maxLen, r - l + 1);
            lastPos[val] = r;
        }
        return maxLen;
    }
}