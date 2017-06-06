package image_processing;

import java.awt.image.BufferedImage;

public class HistogramEqualization {
    
    private HistogramHelper hh;
    private GrayScale gs;
    private LUTHelper lutHelper;
    
    public BufferedImage histogramEquation(BufferedImage bi) {
        if(hh == null)
            hh = new HistogramHelper();
        if(gs == null)
            gs = new GrayScale();
        if(lutHelper == null)
            lutHelper = new LUTHelper();
//        bi = gs.grayScaleAvg(bi);
//        int[] grayScale = gs.convertToArray(bi);
//        int[] histogram = hh.getHistogram(grayScale);
        int[][] rgbScale = gs.convertToArrayRGB(bi);
        int[] histogramR = hh.getHistogram(rgbScale[0]);
        int[] histogramG = hh.getHistogram(rgbScale[1]);
        int[] histogramB = hh.getHistogram(rgbScale[2]);
        double[] distributionR = hh.getDistribution(histogramR);
        double[] distributionG = hh.getDistribution(histogramG);
        double[] distributionB = hh.getDistribution(histogramB);
//        int[] lut = hh.getHistogramEquationLUT(distribution, bi.getHeight() * bi.getWidth());
        int[] lutR = hh.getHistogramEquationLUT(distributionR, bi.getHeight() * bi.getWidth());
        int[] lutG = hh.getHistogramEquationLUT(distributionG, bi.getHeight() * bi.getWidth());
        int[] lutB = hh.getHistogramEquationLUT(distributionB, bi.getHeight() * bi.getWidth());
        int[][] lut = new int[3][lutR.length];
        lut[0] = lutR;
        lut[1] = lutG;
        lut[2] = lutB;
        bi = lutHelper.processLUT(bi, lut);
        return bi;
    }
}
