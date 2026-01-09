//865. Smallest Subtree with all the Deepest Nodes

//Given the root of a binary tree, the depth of each node is the shortest distance to the root.

//Return the smallest subtree such that it contains all the deepest nodes in the original tree.

//A node is called the deepest if it has the largest depth possible among any node in the entire tree.

//The subtree of a node is a tree consisting of that node, plus the set of all descendants of that node.


class Solution {

    private static class Pair {
        TreeNode node;
        int depth;

        Pair(TreeNode n, int d) {
            node = n;
            depth = d;
        }
    }

    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        return dfs(root).node;
    }

    private Pair dfs(TreeNode root) {
        if (root == null) return new Pair(null, 0);

        Pair left = dfs(root.left);
        Pair right = dfs(root.right);

        if (left.depth > right.depth)
            return new Pair(left.node, left.depth + 1);

        if (right.depth > left.depth)
            return new Pair(right.node, right.depth + 1);

        return new Pair(root, left.depth + 1);
    }
}
