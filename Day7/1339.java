//1339. Maximum Product of Splitted Binary Tree
//Given the root of a binary tree, split the binary 
// tree into two subtrees by removing one edge such 
// that the product of the sums of the subtrees is maximized.
//
//Return the maximum product of the sums of the two subtrees.
//  Since the answer may be too large, return it modulo 10^9 + 7.

class Solution {
    private long totalSum = 0;
    private long maxProduct = 0;
    private static final int MOD = 1_000_000_007;

    private long getTotalSum(TreeNode root) {
        if (root == null) return 0;
        return root.val + getTotalSum(root.left) + getTotalSum(root.right);
    }

    private long dfs(TreeNode root) {
        if (root == null) return 0;

        long left = dfs(root.left);
        long right = dfs(root.right);

        long sum = root.val + left + right;
        maxProduct = Math.max(maxProduct, sum * (totalSum - sum));

        return sum;
    }

    public int maxProduct(TreeNode root) {
        totalSum = getTotalSum(root);
        dfs(root);
        return (int) (maxProduct % MOD);
    }
}