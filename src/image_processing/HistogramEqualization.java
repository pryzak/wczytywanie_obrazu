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
        bi = gs.grayScaleAvg(bi);
        int[] grayScale = gs.convertToArray(bi);
        int[] histogram = hh.getHistogram(grayScale);
        double[] distribution = hh.getDistribution(histogram);
        int[] lut = hh.getHistogramEquationLUT(distribution, bi.getHeight() * bi.getWidth());
        bi = lutHelper.processLUT(bi, lut);
        return bi;
    }
}
