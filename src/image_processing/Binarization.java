package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import utils.ImageToArray;

public class Binarization {
    
    private GrayScale gs;
//    private OtsuThresholding ot;
    private Otsu ot;
    private BHT bht;
    private Bernsen bernsen;
    private MeanMedianBinarization mmb;
    private WhiteRohrer wr;
    private Niblack niblack;
    private Sauvola sauvola;
    private ImageToArray ita;
    private RidlerCalvard rc;
    
    public BufferedImage binarize(BufferedImage bi, int threshold) {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                Color newColor;
                if((red + green + blue) / 3 < threshold) {
                    newColor = new Color(0, 0, 0);
                }
                else {
                    newColor = new Color(255, 255, 255);
                }
                bi.setRGB(j,i,newColor.getRGB());
            }
        }
        return bi;
    }
    
    public BufferedImage binarize2(BufferedImage bi, int threshold) {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                Color newColor;
                if(red < threshold || green < threshold || blue < threshold) {
                    newColor = new Color(0, 0, 0);
                }
                else {
                    newColor = new Color(255, 255, 255);
                }
                bi.setRGB(j,i,newColor.getRGB());
            }
        }
        return bi;
    }
    
    public BufferedImage otsuBinarize(BufferedImage bi) {
        if(gs == null)
            gs = new GrayScale();
//        ot = new OtsuThresholding();
        ot = new Otsu();
        if(ita == null)
            ita = new ImageToArray();
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        int[] grayScaleArray = ita.convertToArrayGray(bi);
        int threshold = (int) ot.getOtsuThreshold(grayScaleArray);
        System.out.println(threshold);
        bi = binarize(bi, threshold);
        return bi;
    }
    
    public BufferedImage bernsenBinarize(BufferedImage bi, int side) {
        if(bernsen == null)
            bernsen = new Bernsen();
        if(gs == null)
            gs = new GrayScale();
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        bi = bernsen.binarize1(bi, side);
        return bi;
    }
    public BufferedImage bernsenBinarize2(BufferedImage bi, int side, int gThreshold, int epsilon) {
        if(bernsen == null)
            bernsen = new Bernsen();
        if(gs == null)
            gs = new GrayScale();
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        bi = bernsen.binarize1(bi, side, gThreshold, epsilon);
        return bi;
    }
    
    public BufferedImage whiteRohrerBinarize(BufferedImage bi, int side, double k) {
        if(wr == null)
            wr = new WhiteRohrer();
        if(gs == null)
            gs = new GrayScale();
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        bi = wr.binarize(bi, side, k);
        return bi;
    }
    
    public BufferedImage niblackBinarize(BufferedImage bi, int side, double k) {
        if(niblack == null)
            niblack = new Niblack();
        if(gs == null)
            gs = new GrayScale();
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        bi = niblack.binarize(bi, side, k);
        return bi;
    }
    
    public BufferedImage sauvolaBinarize(BufferedImage bi, int side, double k, int r) {
        if(sauvola == null)
            sauvola = new Sauvola();
        if(gs == null)
            gs = new GrayScale();
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        bi = sauvola.binarize(bi, side, k, r);
        return bi;
    }
    
    public BufferedImage bhtBinarize(BufferedImage bi) {
        if(gs == null)
            gs = new GrayScale();
        bht = new BHT();
        bi = gs.grayScaleYUV(bi);
        if(ita == null)
            ita = new ImageToArray();
        int[] grayScaleArray = ita.convertToArrayGray(bi);
        int threshold = (int) bht.findThreshold(grayScaleArray);
        System.out.println(threshold);
        bi = binarize(bi, threshold);
        return bi;
    }
    public BufferedImage bhtBinarize2(BufferedImage bi) {
        if(gs == null)
            gs = new GrayScale();
        bht = new BHT();
        bi = gs.grayScaleYUV(bi);
        if(ita == null)
            ita = new ImageToArray();
        int[] grayScaleArray = ita.convertToArrayGray(bi);
        int threshold = (int) bht.findThreshold2(grayScaleArray);
        System.out.println(threshold);
        bi = binarize(bi, threshold);
        return bi;
    }
    public BufferedImage bhtBinarize3(BufferedImage bi) {
        if(gs == null)
            gs = new GrayScale();
        bht = new BHT();
        bi = gs.grayScaleYUV(bi);
        if(ita == null)
            ita = new ImageToArray();
        int[] grayScaleArray = ita.convertToArrayGray(bi);
        int threshold = (int) bht.findThreshold3(grayScaleArray);
        System.out.println(threshold);
        bi = binarize(bi, threshold);
        return bi;
    }
    
    public BufferedImage meanBinarize1(BufferedImage bi, int side) {
        if(mmb == null)
            mmb = new MeanMedianBinarization();
        if(gs == null)
            gs = new GrayScale();
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        bi = mmb.meanBinarize1(bi, side);
        return bi;
    }
    
    public BufferedImage medianBinarize1(BufferedImage bi, int side) {
        if(mmb == null)
            mmb = new MeanMedianBinarization();
        if(gs == null)
            gs = new GrayScale();
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        bi = mmb.medianBinarize1(bi, side);
        return bi;
    }
    
    public BufferedImage ridlerCalvardBinarize(BufferedImage bi) {
        if(rc == null)
            rc = new RidlerCalvard();
        if(gs == null)
            gs = new GrayScale();
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        int threshold = (int) rc.getThreshold(bi);
        System.out.println(threshold);
        bi = binarize(bi, threshold);
        return bi;
    }
    
}
