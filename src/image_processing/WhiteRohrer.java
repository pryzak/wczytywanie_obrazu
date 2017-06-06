package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class WhiteRohrer {
    
    public BufferedImage binarize(BufferedImage bi, int x, double k) throws RuntimeException {
        if(k < 1)
            throw new RuntimeException("k must be greater than or equal to 1!");
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                if(c.getRed() != c.getGreen() || c.getRed() != c.getBlue() || c.getGreen() != c.getBlue())
                    throw new RuntimeException("Image is not grayScale!");
            }
        }
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int countPixels = 0;
                int sum = 0;
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        countPixels++;
                        Color c = new Color(bi.getRGB(jj, ii));
                        sum += c.getRed();
                    }
                }
                int threshold = (int) (((double) sum / (double) countPixels) / k);
                Color color = new Color(bi.getRGB(j, i));
                Color newColor;
                if((color.getRed() + color.getGreen() + color.getBlue()) / 3 < threshold)
                    newColor = new Color(0, 0, 0);
                else
                    newColor = new Color(255, 255, 255);
                newColors[j][i] = newColor;
            }
        }
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                bi.setRGB(j, i, newColors[j][i].getRGB());
            }
        }
        return bi;
    }
    
}
