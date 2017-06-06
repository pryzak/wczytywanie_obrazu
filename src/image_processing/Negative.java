package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Negative {
    
    public BufferedImage negate(BufferedImage bi) {
        int[][] newColors = new int[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                newColors[j][i] = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue()).getRGB();
            }
        }
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                bi.setRGB(j, i, newColors[j][i]);
            }
        }
        return bi;
    }
    
}
