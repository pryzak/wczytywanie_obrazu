
package utils;

public class RotateMatrix {
    
    public int[][] rotate90(int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                tmp[i][j] = matrix[matrix[i].length - j - 1][i];
            }
        }
        return tmp;
    }
}
