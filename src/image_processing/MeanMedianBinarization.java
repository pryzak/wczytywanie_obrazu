package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeanMedianBinarization {
    
    public BufferedImage meanBinarize1(BufferedImage bi, int x) throws RuntimeException {
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
                int threshold = (sumRed / countPixels + sumGreen / countPixels + sumBlue / countPixels) / 3;
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
    
    public BufferedImage meanBinarize2(BufferedImage bi, int x) throws RuntimeException {
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
                int threshold = (sumRed / countPixels + sumGreen / countPixels + sumBlue / countPixels) / 3;
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
    
    public BufferedImage medianBinarize1(BufferedImage bi, int x) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                List<Integer> reds = new ArrayList<>();
                List<Integer> greens = new ArrayList<>();
                List<Integer> blues = new ArrayList<>();
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        Color c = new Color(bi.getRGB(jj, ii));
                        reds.add(c.getRed());
                        greens.add(c.getGreen());
                        blues.add(c.getBlue());
                    }
                }
                Collections.sort(reds);
                Collections.sort(greens);
                Collections.sort(blues);
                int medianReds;
                int medianGreens;
                int medianBlues;
                if(reds.size() % 2 == 1) {
                    medianReds = reds.get(reds.size() / 2);
                }
                else {
                    medianReds = (reds.get(reds.size() / 2 - 1) + reds.get(reds.size() / 2)) / 2;
                }
                if(greens.size() % 2 == 1) {
                    medianGreens = greens.get(greens.size() / 2);
                }
                else {
                    medianGreens = (greens.get(greens.size() / 2 - 1) + greens.get(greens.size() / 2)) / 2;
                }
                if(blues.size() % 2 == 1) {
                    medianBlues = blues.get(blues.size() / 2);
                }
                else {
                    medianBlues = (blues.get(blues.size() / 2 - 1) + blues.get(blues.size() / 2)) / 2;
                }
                int threshold = (medianReds + medianGreens + medianBlues) / 3;
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
    
    public BufferedImage medianBinarize2(BufferedImage bi, int x) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                List<Integer> reds = new ArrayList<>();
                List<Integer> greens = new ArrayList<>();
                List<Integer> blues = new ArrayList<>();
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        Color c = new Color(bi.getRGB(jj, ii));
                        reds.add(c.getRed());
                        greens.add(c.getGreen());
                        blues.add(c.getBlue());
                    }
                }
                Collections.sort(reds);
                Collections.sort(greens);
                Collections.sort(blues);
                int medianReds;
                int medianGreens;
                int medianBlues;
                if(reds.size() % 2 == 1) {
                    medianReds = reds.get(reds.size() / 2);
                }
                else {
                    medianReds = (reds.get(reds.size() / 2 - 1) + reds.get(reds.size() / 2)) / 2;
                }
                if(greens.size() % 2 == 1) {
                    medianGreens = greens.get(greens.size() / 2);
                }
                else {
                    medianGreens = (greens.get(greens.size() / 2 - 1) + greens.get(greens.size() / 2)) / 2;
                }
                if(blues.size() % 2 == 1) {
                    medianBlues = blues.get(blues.size() / 2);
                }
                else {
                    medianBlues = (blues.get(blues.size() / 2 - 1) + blues.get(blues.size() / 2)) / 2;
                }
                int threshold = (medianReds + medianGreens + medianBlues) / 3;
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
    
}
