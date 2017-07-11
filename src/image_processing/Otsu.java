package image_processing;

public class Otsu {
    
    public int getOtsuThreshold(int[] grayScaleValues) {
        int n = grayScaleValues.length;
        int[] histogram = new HistogramHelper().getHistogram(grayScaleValues);
        
        float sum = 0;
        for (int i=0; i<256; i++)
            sum += i * histogram[i];
        
        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 127;
        
        for (int t=0; t<256; t++) {
            wB += histogram[t];               // Weight Background
            if (wB == 0) continue;

            wF = n - wB;                 // Weight Foreground
            if (wF == 0) break;

            sumB += (float) (t * histogram[t]);

            float mB = sumB / wB;            // Mean Background
            float mF = (sum - sumB) / wF;    // Mean Foreground

            // Calculate Between Class Variance
            float varBetween = (float)wB * (float)wF * (mB - mF) * (mB - mF);

            // Check if new maximum found
            if (varBetween > varMax) {
               varMax = varBetween;
               threshold = t;
            }
        }
        
        return threshold;
    }
}
