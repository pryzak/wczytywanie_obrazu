
package utils;

public class StandardDeviation {
    
    public double getStd(int[] matrix) {
        return Math.sqrt(getVar(matrix));
    }
    
    public double getVar(int[] matrix) {
        int sum = 0;
        for(int i = 0; i < matrix.length; i++)
            sum += matrix[i];
        double mean = (double) sum / (double) matrix.length;
        double[] deviations = new double[matrix.length];
        for(int i = 0; i < matrix.length; i++) {
            double sub = (double) matrix[i] - mean;
            deviations[i] = sub * sub;
        }
        double sum2 = 0;
        for(int i = 0; i < deviations.length; i++)
            sum2 += deviations[i];
        double variance = sum2 / (double) deviations.length;
        return variance;
    }
    
    public double getStd(double[] matrix) {
        return Math.sqrt(getVar(matrix));
    }
    
    public double getVar(double[] matrix) {
        double sum = 0;
        for(int i = 0; i < matrix.length; i++)
            sum += matrix[i];
        double mean = sum / (double) matrix.length;
        double[] deviations = new double[matrix.length];
        for(int i = 0; i < matrix.length; i++) {
            double sub = matrix[i] - mean;
            deviations[i] = sub * sub;
        }
        double sum2 = 0;
        for(int i = 0; i < deviations.length; i++)
            sum2 += deviations[i];
        double variance = sum2 / (double) deviations.length;
        return variance;
    }
    
}
