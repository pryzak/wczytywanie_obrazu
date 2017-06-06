package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GrayScale {
    
    public BufferedImage grayScaleYUV(BufferedImage bi) {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                int red = (int)(c.getRed() * 0.299);
                int green = (int)(c.getGreen() * 0.587);
                int blue = (int)(c.getBlue() *0.114);
                
                Color newColor = new Color(red+green+blue, red+green+blue, red+green+blue);
                
                bi.setRGB(j,i,newColor.getRGB());
            }
        }
        return bi;
    }
    
    public BufferedImage grayScaleAvg(BufferedImage bi) {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                
                Color newColor = new Color(gray, gray, gray);
                
                bi.setRGB(j,i,newColor.getRGB());
            }
        }
        return bi;
    }   
    
}
