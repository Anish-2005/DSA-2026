//1582. Special Positions in a Binary Matrix

//Given an m x n binary matrix mat, return the number of special positions in mat.

//A position (i, j) is called special if mat[i][j] == 1 and all other elements in row i and column j are 0 (rows and columns are 0-indexed). 

class Solution {
    public int numSpecial(int[][] mat) {
        int m = mat.length; //number of rows
        int n = mat[0].length; //number of columns

        int[] row = new int[m]; //array to store count of 1s in each row
        int[] col = new int[n]; //array to store count of 1s in each column

        for(int i = 0; i < m; i++) { //loop through each row
            for(int j = 0; j < n; j++) { //loop through each column
                if(mat[i][j] == 1) { //if the element is 1, increment the count for the corresponding row and column
                    row[i]++; //increment row count
                    col[j]++; //increment column count
                }
            }
        }

        int count = 0; //variable to count special positions
        for(int i = 0; i < m; i++) { //loop through each row
            for(int j = 0; j < n; j++) { //loop through each column
                if(mat[i][j] == 1 && row[i] == 1 && col[j] == 1) { //if the element is 1 and there is only one 1 in the row and column, it is a special position
                    count++; //increment the count of special positions
                }
            }
        }

        return count; // return the total count of special positions
    }
}