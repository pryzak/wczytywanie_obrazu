package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import utils.StandardDeviation;

public class WienerFilter {
    
    public BufferedImage filter(BufferedImage bi) {
        bi = new GrayScale().grayScaleAvg(bi);
        int x = 3;
        int n = (x - 1) / 2;
        int[][] newColors = new int[bi.getWidth()][bi.getHeight()];
        double[][] variances = new double[bi.getWidth()][bi.getHeight()];
        double[][] means = new double[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            System.out.print("");
            for (int j = 0; j < bi.getWidth(); j++) {
                int countPixels = 0;
                int sum = 0;
                List<Integer> colors = new ArrayList<>();
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        if(ii == i && jj == j)
                            continue;
                        countPixels++;
                        Color c = new Color(bi.getRGB(jj, ii));
                        colors.add(c.getRed());
                        sum += c.getRed();
                    }
                }
                double mean = (double)sum / (double)countPixels;
                means[j][i] = mean;
                StandardDeviation sd = new StandardDeviation();
                double var = sd.getVar(colors.stream().mapToInt(c->c).toArray());
                variances[j][i] = var;
            }
        }
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                double sumVars = 0;
                double count = 0;
                if(j > 0) {
                    sumVars += variances[j-1][i];
                    count++;
                }
                if(j > 0 && i > 0) {
                    sumVars += variances[j-1][i-1];
                    count++;
                }
                if(j > 0 && i < bi.getHeight() - 1) {
                    sumVars += variances[j-1][i+1];
                    count++;
                }
                if(i > 0) {
                    sumVars += variances[j][i-1];
                    count++;
                }
                if(i < bi.getHeight() - 1) {
                    sumVars += variances[j][i+1];
                    count++;
                }
                if(i > 0 && j < bi.getWidth() - 1) {
                    sumVars += variances[j+1][i-1];
                    count++;
                }
                if(j < bi.getWidth() - 1) {
                    sumVars += variances[j+1][i];
                    count++;
                }
                if(j < bi.getWidth() - 1 && i < bi.getHeight() - 1) {
                    sumVars += variances[j+1][i+1];
                    count++;
                }
                double meanVars = sumVars / (double) count;
                if(variances[j][i] != 0) {
                    int newColor = (int) (means[j][i] + (variances[j][i] - meanVars)*(new Color(bi.getRGB(j, i)).getRed() - means[j][i]) / variances[j][i]);
                    if(newColor > 255)
                        newColor = 255;
                    if(newColor < 0)
                        newColor = 0;
                    newColors[j][i] = newColor;
                }
                else
                    newColors[j][i] = (int) means[j][i];
            }
        }
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(newColors[j][i], newColors[j][i], newColors[j][i]);
                bi.setRGB(j, i, c.getRGB());
            }
        }
        return bi;
    }
    
}
