package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Filters {

    public static float lowPass[] = new float[]{1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9};
    public static float highPass[] = new float[]{-1, -1, -1, -1, 9, -1, -1, -1, -1};

    public BufferedImage filter(BufferedImage bi, float[] filter) {
        Kernel kernel;
        kernel = new Kernel(3, 3, filter);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bi = op.filter(bi, null);
        return bi;
    }
    
    public BufferedImage meanFilter(BufferedImage bi, int x) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                //TODO
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        //TODO
                    }
                }
                //TODO
            }
        }
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                bi.setRGB(j, i, newColors[j][i].getRGB());
            }
        }
        return bi;
    }
    
    
    
    public BufferedImage medianFilter(BufferedImage bi, int x) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int countPixels = 0;
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        countPixels++;
                        Color c = new Color(bi.getRGB(jj, ii));
                        sumRed += c.getRed();
                        sumGreen += c.getGreen();
                        sumBlue += c.getBlue();
                    }
                }
                Color newColor = new Color(sumRed / countPixels, sumGreen / countPixels, sumBlue / countPixels);
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
