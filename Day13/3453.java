//3453. Separate Squares I

//You are given a 2D integer array squares. 
// Each squares[i] = [xi, yi, li] represents the coordinates of 
// the bottom-left point and the side length of a square parallel to the x-axis.

//Find the minimum y-coordinate value of a horizontal line such 
// that the total area of the squares above the line equals the 
// total area of the squares below the line.

//Answers within 10-5 of the actual answer will be accepted.

//Note: Squares may overlap. Overlapping areas should be counted multiple times.

class Solution {
    public double separateSquares(int[][] squares) {
        double low = Double.MAX_VALUE, high = Double.MIN_VALUE;

        for (int[] s : squares) {
            low = Math.min(low, s[1]);
            high = Math.max(high, s[1] + s[2]);
        }

        for (int i = 0; i < 80; i++) {
            double mid = (low + high) / 2.0;
            double above = 0.0, below = 0.0;

            for (int[] s : squares) {
                double y = s[1];
                double l = s[2];
                double top = y + l;

                if (mid <= y) {
                    above += l * l;
                } else if (mid >= top) {
                    below += l * l;
                } else {
                    above += (top - mid) * l;
                    below += (mid - y) * l;
                }
            }

            if (above > below) low = mid;
            else high = mid;
        }

        return (low + high) / 2.0;
    }
}
