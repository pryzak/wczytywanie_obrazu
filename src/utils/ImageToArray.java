package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageToArray {
    
    public int[] convertToArrayGray(BufferedImage bi) {
        int[] grayArray = new int[bi.getHeight() * bi.getWidth()];
        int index = 0;
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                grayArray[index++] = c.getRed();
            }
        }
        return grayArray;
    }
    
    public int[][] convertToArrayRGB(BufferedImage bi) {
        int[][] rgbArray = new int[3][bi.getHeight() * bi.getWidth()];
        int index = 0;
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                rgbArray[0][index] = c.getRed();
                rgbArray[1][index] = c.getGreen();
                rgbArray[2][index++] = c.getBlue();
            }
        }
        return rgbArray;
    }
    
}
