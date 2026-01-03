//1411. Number of Ways to Paint N × 3 Grid
//You have a grid of size n x 3 and you want to paint 
//each cell of the grid with exactly one of the three 
//colors: Red, Yellow, or Green while making sure that 
//no two adjacent cells have the same color (i.e., no two 
//cells that share vertical or horizontal sides have 
//the same color).

//Given n the number of rows of the grid, return the number of ways you can
//  paint this grid. As the answer may grow large, the answer must be computed
//  modulo 109 + 7.

class Solution {
    public int numOfWays(int n) {
        final long MOD = 1_000_000_007L;
        long a = 6; 
        long b = 6; 
        for (int i = 2; i <= n; i++) {
            long newA = (3 * a + 2 * b) % MOD;
            long newB = (2 * a + 2 * b) % MOD;
            a = newA;
            b = newB;
        }
        return (int)((a + b) % MOD);
    }
}
