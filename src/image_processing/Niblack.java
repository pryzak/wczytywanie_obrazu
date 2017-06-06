package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import utils.StandardDeviation;

public class Niblack {
    
    public BufferedImage binarize(BufferedImage bi, int x, double k) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        if(k >= 0)
            throw new RuntimeException("k must be lower than 0!");
        int n = (x - 1) / 2;
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int countPixels = 0;
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;
                List<Integer> reds = new ArrayList<>();
                List<Integer> greens = new ArrayList<>();
                List<Integer> blues = new ArrayList<>();
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        countPixels++;
                        Color c = new Color(bi.getRGB(jj, ii));
                        reds.add(c.getRed());
                        greens.add(c.getGreen());
                        blues.add(c.getBlue());
                        sumRed += c.getRed();
                        sumGreen += c.getGreen();
                        sumBlue += c.getBlue();
                    }
                }
                double mean = ((double)sumRed / (double)countPixels + (double)sumGreen / (double)countPixels + (double)sumBlue / (double)countPixels) / (double)3;
                StandardDeviation sd = new StandardDeviation();
                double redStd = sd.getStd(reds.stream().mapToInt(c->c).toArray());
                double greenStd = sd.getStd(greens.stream().mapToInt(c->c).toArray());
                double blueStd = sd.getStd(blues.stream().mapToInt(c->c).toArray());
                double std = (redStd + greenStd + blueStd) / (double)3;
                int threshold = (int) (mean + (k * std));
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
