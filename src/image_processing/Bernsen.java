package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Bernsen {
   
    
    public BufferedImage binarize1(BufferedImage bi, int x) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int max = 0;
                int min = 255;
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        Color c = new Color(bi.getRGB(jj, ii));
                        int col = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                        if(col > max)
                            max = col;
                        if(col < min)
                            min = col;
                    }
                }
                int threshold = (max + min) / 2;
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
    
    public BufferedImage binarize2(BufferedImage bi, int x) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int max = 0;
                int min = 255;
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        Color c = new Color(bi.getRGB(jj, ii));
                        int col = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                        if(col > max)
                            max = col;
                        if(col < min)
                            min = col;
                    }
                }
                int threshold = (max + min) / 2;
                Color color = new Color(bi.getRGB(j, i));
                Color newColor;
                if(color.getRed() < threshold || color.getGreen() < threshold || color.getBlue() < threshold)
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
    
    public BufferedImage binarize1(BufferedImage bi, int x, int gThreshold, int epsilon) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int max = 0;
                int min = 255;
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        Color c = new Color(bi.getRGB(jj, ii));
                        int col = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                        if(col > max)
                            max = col;
                        if(col < min)
                            min = col;
                    }
                }
                int threshold;
                if(max - min > epsilon)
                    threshold = (max + min) / 2;
                else
                    threshold = gThreshold;
                Color color = new Color(bi.getRGB(j, i));
                Color newColor;
                if(color.getRed() < threshold || color.getGreen() < threshold || color.getBlue() < threshold)
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
    
    public BufferedImage binarize2(BufferedImage bi, int x, int gThreshold, int epsilon) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int max = 0;
                int min = 255;
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        Color c = new Color(bi.getRGB(jj, ii));
                        int col = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                        if(col > max)
                            max = col;
                        if(col < min)
                            min = col;
                    }
                }
                int threshold;
                if(max - min > epsilon)
                    threshold = (max + min) / 2;
                else
                    threshold = gThreshold;
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
