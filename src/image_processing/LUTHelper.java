package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LUTHelper {
    
    public BufferedImage processLUT(BufferedImage bi, int[] LUT) {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                int newGray = LUT[gray];
                Color newColor = new Color(newGray, newGray, newGray);
                bi.setRGB(j,i,newColor.getRGB());
            }
        }
        return bi;
    }
    
    public BufferedImage processLUT(BufferedImage bi, int[][] LUT) {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                int red = c.getRed();
                int newRed = LUT[0][red];
                int green = c.getGreen();
                int newGreen = LUT[0][green];
                int blue = c.getBlue();
                int newBlue = LUT[0][blue];
                Color newColor = new Color(newRed, newGreen, newBlue);
                bi.setRGB(j,i,newColor.getRGB());
            }
        }
        return bi;
    }
    
}
