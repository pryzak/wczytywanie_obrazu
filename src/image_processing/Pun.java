package image_processing;

import java.awt.image.BufferedImage;
import utils.CloneImage;
import utils.ImageToArray;

public class Pun {
    
    public int getThreshold(BufferedImage bi) {
        Binarization b = new Binarization();
        CloneImage ci = new CloneImage();
        double[] h = new double[256];
        int count = bi.getWidth() * bi.getHeight();
        //
        int[] f = new HistogramHelper().getHistogram(new ImageToArray().convertToArrayGray(bi));
        double[] p = new double[256];
        for(int t = 0; t < 256; t++) {
            p[t] = (double) f[t] / (double) count;
        }
        //
        for(int t = 0; t < 256; t++) {
            //
//            BufferedImage biClone = ci.deepCopy(bi);
//            biClone = b.binarize(biClone, t);
//            int[] f = new HistogramHelper().getHistogram(new ImageToArray().convertToArrayGray(biClone));
//            double[] p = new double[256];
//            for(int tt = 0; tt < 256; tt++) {
//                p[tt] = (double) f[tt] / (double) count;
//            }
            //
            double p_ob = 0, p_b = 0;
            for(int k = 0; k <= t; k++) {
                p_ob += p[k];
            }
            for(int l = t+1; l < 256; l++) {
                p_b += p[l];
            }
            h[t] = - p_ob * (Math.log(p_ob) / Math.log(2)) - p_b * (Math.log(p_b) / Math.log(2));
        }
        double hMax = 0;
        int threshold = 127;
        for(int t = 0; t < 256; t++) {
            if(h[t] > hMax) {
                hMax = h[t];
                threshold = t;
            }
        }
        return threshold;
    }
    
}
