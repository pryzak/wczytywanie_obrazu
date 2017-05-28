package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Binarization {
    
    private GrayScale gs;
//    private OtsuThresholding ot;
    private Otsu ot;
    private BHT bht;
    
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
    
    public BufferedImage otsuBinarize(BufferedImage bi) {
        if(gs == null)
            gs = new GrayScale();
//        ot = new OtsuThresholding();
        ot = new Otsu();
        
//        bi = gs.grayScaleAvg(bi);
        bi = gs.grayScaleYUV(bi);
        int[] grayScaleArray = gs.convertToArray(bi);
        int threshold = (int) ot.getOtsuThreshold(grayScaleArray);
        System.out.println(threshold);
        bi = binarize(bi, threshold);
        return bi;
    }
    
    
    public BufferedImage bhtBinarize(BufferedImage bi) {
        if(gs == null)
            gs = new GrayScale();
        bht = new BHT();
        bi = gs.grayScaleYUV(bi);
        int[] grayScaleArray = gs.convertToArray(bi);
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
        int[] grayScaleArray = gs.convertToArray(bi);
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
        int[] grayScaleArray = gs.convertToArray(bi);
        int threshold = (int) bht.findThreshold3(grayScaleArray);
        System.out.println(threshold);
        bi = binarize(bi, threshold);
        return bi;
    }
    
}
