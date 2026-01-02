//3479. Fruits Into Baskets III
//You are given two arrays of integers, fruits and baskets, each of length n, where fruits[i] represents the quantity of the ith type of fruit, and baskets[j] represents the capacity of the jth basket.

//From left to right, place the fruits according to these rules:

//Each fruit type must be placed in the leftmost available basket with a capacity greater than or equal to the quantity of that fruit type.
//Each basket can hold only one type of fruit.
//If a fruit type cannot be placed in any basket, it remains unplaced.
//Return the number of fruit types that remain unplaced after all possible allocations are made.
class Solution {
    int[] tree;
    int n;
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        n = baskets.length;
        tree = new int[4 * n];
        build(baskets, 0, n - 1, 1);
        int unplaced = 0;
        for (int fruit : fruits) {
            int idx = query(0, n - 1, 1, fruit);
            if (idx == -1) {
                unplaced++;
            } else {
                update(0, n - 1, 1, idx);
            }
        }

       return unplaced;
    }
    void build(int[] arr, int l, int r, int node) {
        if (l == r) {
            tree[node] = arr[l];
            return;
        }
        int mid = (l + r) / 2;
        build(arr, l, mid, node * 2);
        build(arr, mid + 1, r, node * 2 + 1);
        tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
    }
    int query(int l, int r, int node, int fruit) {
        if (tree[node] < fruit) return -1;
        if (l == r) return l;

        int mid = (l + r) / 2;
        int left = query(l, mid, node * 2, fruit);
        if (left != -1) return left;
        return query(mid + 1, r, node * 2 + 1, fruit);
    }
    void update(int l, int r, int node, int idx) {
        if (l == r) {
            tree[node] = 0;
            return;
        }
        int mid = (l + r) / 2;
        if (idx <= mid) update(l, mid, node * 2, idx);
        else update(mid + 1, r, node * 2 + 1, idx);
        tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
    }
}
