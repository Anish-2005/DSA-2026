//2943. Maximize Area of Square Hole in Grid

//You are given the two integers, n and m and
//  two integer arrays, hBars and vBars. The grid 
// has n + 2 horizontal and m + 2 vertical bars, 
// creating 1 x 1 unit cells. The bars are indexed starting from 1.

//You can remove some of the bars in hBars from
//  horizontal bars and some of the bars in vBars 
// from vertical bars. Note that other bars are fixed and cannot be removed.

//Return an integer denoting the maximum area of a 
// square-shaped hole in the grid, after removing some bars (possibly none).

class Solution {

    private int longestConsecutive(int[] bars) {
        if (bars.length == 0) return 1;

        Arrays.sort(bars);
        int maxLen = 1;
        int curr = 1;

        for (int i = 1; i < bars.length; i++) {
            if (bars[i] == bars[i - 1] + 1) {
                curr++;
            } else {
                curr = 1;
            }
            maxLen = Math.max(maxLen, curr);
        }
        return maxLen + 1;
    }

    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        int maxH = longestConsecutive(hBars);
        int maxV = longestConsecutive(vBars);

        int side = Math.min(maxH, maxV);
        return side * side;
    }
}
