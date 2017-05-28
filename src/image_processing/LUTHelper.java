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
    
}
