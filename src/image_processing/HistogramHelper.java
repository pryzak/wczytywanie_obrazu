package image_processing;

public class HistogramHelper {

    public int[] getHistogram(int[] grayScaleValues) {
        int[] histogram = new int[256];

        for (int i = 0; i < grayScaleValues.length; i++) {
            histogram[grayScaleValues[i]]++;
        }
        return histogram;
    }

    public double[] getDistribution(int[] histogram) {
        int n = 0;
        for (int i = 0; i < histogram.length; i++) {
            n += histogram[i];
        }
        double[] distribution = new double[histogram.length];
        int suma = 0;
        for (int i = 0; i < histogram.length; i++) {
            suma += histogram[i];
            distribution[i] = (double) suma / (double) n;
//            distribution[i] = (double) suma;
        }
        return distribution;
    }

    public int[] getHistogramEquationLUT(double[] distribution, int n) {
        int[] lut = new int[distribution.length];
        double d0 = 0;
        for (double d : lut) {
            if (d != 0) {
                d0 = d;
                break;
            }
        }
        int k = distribution.length - 1;
        for (int i = 0; i < distribution.length; i++) {
            lut[i] = (int) (((distribution[i] - d0) / (1 - d0)) * (k - 1));
//            lut[i] = (int) Math.round(((distribution[i] - d0) / (n - d0)) * (k - 1));
        }
        return lut;
    }

}
