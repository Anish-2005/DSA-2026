/*2075. Decode the Slanted Ciphertext

A string originalText is encoded using a slanted transposition cipher to a string encodedText with the help of a matrix having a fixed number of rows rows.

originalText is placed first in a top-left to bottom-right manner.


The blue cells are filled first, followed by the red cells, then the yellow cells, and so on, until we reach the end of originalText. The arrow indicates the order in which the cells are filled. All empty cells are filled with ' '. The number of columns is chosen such that the rightmost column will not be empty after filling in originalText.

encodedText is then formed by appending all characters of the matrix in a row-wise fashion.


The characters in the blue cells are appended first to encodedText, then the red cells, and so on, and finally the yellow cells. The arrow indicates the order in which the cells are accessed.

For example, if originalText = "cipher" and rows = 3, then we encode it in the following manner:


The blue arrows depict how originalText is placed in the matrix, and the red arrows denote the order in which encodedText is formed. In the above example, encodedText = "ch ie pr".

Given the encoded string encodedText and number of rows rows, return the original string originalText.

Note: originalText does not have any trailing spaces ' '. The test cases are generated such that there is only one possible originalText.

 

Example 1:

Input: encodedText = "ch   ie   pr", rows = 3
Output: "cipher"
Explanation: This is the same example described in the problem description.
*/

class Solution {
    public String decodeCiphertext(String encodedText, int rows) {
        if(rows == 0) return "";
        
        int n = encodedText.length();
        int cols = n / rows;
        
        char[][] grid = new char[rows][cols];
        int index = 0;
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                grid[i][j] = encodedText.charAt(index++);
            }
        }
        
        StringBuilder result = new StringBuilder();
        
        for(int start = 0; start < cols; start++) {
            int r = 0, c = start;
            
            while(r < rows && c < cols) {
                result.append(grid[r][c]);
                r++;
                c++;
            }
        }
        
        int end = result.length() - 1;
        while(end >= 0 && result.charAt(end) == ' ') {
            end--;
        }
        
        return result.substring(0, end + 1);
    }
}