//85. Maximal rectangle

//Given a rows x cols binary matrix filled with 0's and 1's,
//  find the largest rectangle containing only 1's and return its area.

class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;

        int[] height = new int[cols];
        int maxArea = 0;

        for (int i = 0; i < rows; i++) {
            // Build histogram
            for (int j = 0; j < cols; j++) {
                height[j] = (matrix[i][j] == '1') ? height[j] + 1 : 0;
            }
            maxArea = Math.max(maxArea, largestRectangle(height));
        }
        return maxArea;
    }

    private int largestRectangle(int[] h) {
        int n = h.length;
        int[] stack = new int[n + 1]; // manual stack
        int top = -1;
        int max = 0;

        for (int i = 0; i <= n; i++) {
            int curr = (i == n) ? 0 : h[i];

            while (top != -1 && curr < h[stack[top]]) {
                int height = h[stack[top--]];
                int width = (top == -1) ? i : i - stack[top] - 1;
                int area = height * width;
                if (area > max) max = area;
            }
            stack[++top] = i;
        }
        return max;
    }
}
