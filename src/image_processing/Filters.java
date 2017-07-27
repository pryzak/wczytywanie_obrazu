package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.StandardDeviation;

public class Filters {

    public static float lowPass[] = new float[]{1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9};
    public static float gauss1[] = new float[]{1.f / 16, 2.f / 16, 1.f / 16, 2.f / 16, 4.f / 16, 2.f / 16, 1.f / 16, 2.f / 16, 1.f / 16};
    public static float gauss2[] = new float[]{1.f / 52, 1.f / 52, 2.f / 52, 1.f / 52, 1.f / 52, 1.f / 52, 2.f / 52, 4.f / 52, 2.f / 52, 1.f / 52, 2.f / 52, 4.f / 52, 8.f / 52, 4.f / 52, 2.f / 52, 1.f / 52, 2.f / 52, 4.f / 52, 2.f / 52, 1.f / 52, 1.f / 52, 1.f / 52, 2.f / 52, 1.f / 52, 1.f / 52};
    public static float gauss3[] = new float[] {1.f/140, 1.f/140, 2.f/140, 2.f/140, 2.f/140, 1.f/140, 1.f/140,
                                                1.f/140, 2.f/140, 2.f/140, 4.f/140, 2.f/140, 2.f/140, 1.f/140,
                                                2.f/140, 2.f/140, 4.f/140, 8.f/140, 4.f/140, 2.f/140, 2.f/140,
                                                2.f/140, 4.f/140, 8.f/140, 16.f/140, 8.f/140, 4.f/140, 2.f/140,
                                                2.f/140, 2.f/140, 4.f/140, 8.f/140, 4.f/140, 2.f/140, 2.f/140,
                                                1.f/140, 2.f/140, 2.f/140, 4.f/140, 2.f/140, 2.f/140, 1.f/140,
                                                1.f/140, 1.f/140, 2.f/140, 2.f/140, 2.f/140, 1.f/140, 1.f/140};
    
    public static float highPass[] = new float[]{-1, -1, -1, -1, 9, -1, -1, -1, -1};

    public BufferedImage filter(BufferedImage bi, float[] filter) {
        Kernel kernel;
        int n = (int) Math.sqrt(filter.length);
        kernel = new Kernel(n, n, filter);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bi = op.filter(bi, null);
        return bi;
    }
    
    public BufferedImage universalFilter(BufferedImage bi, int[] filter) {
        if(Math.sqrt(filter.length) % 1 != 0)
            throw new RuntimeException("Mask side must be integer!");
        int side = (int) Math.sqrt(filter.length);
        int iter = 0;
        int[][] mask = new int[side][side];
        for(int i = 0; i < side; i++)
            for(int j = 0; j < side; j++)
                mask[j][i] = filter[iter++];
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        int n = (side - 1) / 2;
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int weightSumR = 0;
                int weightSumG = 0;
                int weightSumB = 0;
                int sum = 0;
                for(int ii = i - n, iii = 0; ii <= i + n; ii++, iii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n, jjj = 0; jj <= j + n; jj++, jjj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        Color c = new Color(bi.getRGB(jj, ii));
                        weightSumR += c.getRed() * mask[jjj][iii];
                        weightSumG += c.getGreen()* mask[jjj][iii];
                        weightSumB += c.getBlue()* mask[jjj][iii];
                        sum += mask[jjj][iii];
                    }
                }
                if(sum == 0)
                    sum = 1;
                int newR = weightSumR / sum;
                if(newR < 0)
                    newR = 0;
                else if(newR > 255)
                    newR = 255;
                int newG = weightSumG / sum;
                if(newG < 0)
                    newG = 0;
                else if(newG > 255)
                    newG = 255;
                int newB = weightSumB / sum;
                if(newB < 0)
                    newB = 0;
                else if(newB > 255)
                    newB = 255;
                Color newColor = new Color(newR, newG, newB);
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
    
    public BufferedImage meanFilter(BufferedImage bi, int x) throws RuntimeException {
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
    
    
    
    public BufferedImage medianFilter(BufferedImage bi, int x) throws RuntimeException {
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
                Color newColor = new Color(medianReds, medianGreens, medianBlues);
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
    
    public BufferedImage kuwaharaFilter(BufferedImage bi, int x) throws RuntimeException {
        if(x % 2 == 0)
            throw new RuntimeException("x is even!");
        int n = (x - 1) / 2;
        int l = (x / 2) + 1;
        StandardDeviation std = new StandardDeviation();
        Color[][] newColors = new Color[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                double[] mean_red = new double[4];
                double[] mean_green = new double[4];
                double[] mean_blue = new double[4];
                int suma_red = 0, sumb_red = 0, sumc_red = 0, sumd_red = 0;
                int suma_green = 0, sumb_green = 0, sumc_green = 0, sumd_green = 0;
                int suma_blue = 0, sumb_blue = 0, sumc_blue = 0, sumd_blue = 0;
                double[] std_red = new double[4];
                double[] std_green = new double[4];
                double[] std_blue = new double[4];
                int[] arra_red = new int[l * l];
                int[] arrb_red = new int[l * l];
                int[] arrc_red = new int[l * l];
                int[] arrd_red = new int[l * l];
                int[] arra_green = new int[l * l];
                int[] arrb_green = new int[l * l];
                int[] arrc_green = new int[l * l];
                int[] arrd_green = new int[l * l];
                int[] arra_blue = new int[l * l];
                int[] arrb_blue = new int[l * l];
                int[] arrc_blue = new int[l * l];
                int[] arrd_blue = new int[l * l];
                int iarra = 0, iarrb = 0, iarrc = 0, iarrd = 0;
                for(int ii = i - n; ii <= i + n; ii++) {
                    if(ii < 0 || ii >= bi.getHeight())
                        continue;
                    for(int jj = j - n; jj <= j + n; jj++) {
                        if(jj < 0 || jj >= bi.getWidth())
                            continue;
                        Color c = new Color(bi.getRGB(jj, ii));
                        if(ii <= i && jj <= j) {
                            suma_red += c.getRed();
                            suma_green += c.getGreen();
                            suma_blue += c.getBlue();
                            arra_red[iarra] = c.getRed();
                            arra_green[iarra] = c.getGreen();
                            arra_blue[iarra++] = c.getBlue();
                        }
                        if(ii <= i && jj >= j) {
                            sumb_red += c.getRed();
                            sumb_green += c.getGreen();
                            sumb_blue += c.getBlue();
                            arrb_red[iarrb] = c.getRed();
                            arrb_green[iarrb] = c.getGreen();
                            arrb_blue[iarrb++] = c.getBlue();
                        }
                        if(ii >= i && jj <= j) {
                            sumc_red += c.getRed();
                            sumc_green += c.getGreen();
                            sumc_blue += c.getBlue();
                            arrc_red[iarrc] = c.getRed();
                            arrc_green[iarrc] = c.getGreen();
                            arrc_blue[iarrc++] = c.getBlue();
                        }
                        if(ii >= i && jj >= j) {
                            sumd_red += c.getRed();
                            sumd_green += c.getGreen();
                            sumd_blue += c.getBlue();
                            arrd_red[iarrd] = c.getRed();
                            arrd_green[iarrd] = c.getGreen();
                            arrd_blue[iarrd++] = c.getBlue();
                        }
                    }
                }
                mean_red[0] = (double) suma_red / (double) (l*l);
                mean_green[0] = (double) suma_green / (double) (l*l);
                mean_blue[0] = (double) suma_blue / (double) (l*l);
                std_red[0] = std.getStd(arra_red);
                std_green[0] = std.getStd(arra_green);
                std_blue[0] = std.getStd(arra_blue);
                mean_red[1] = (double) sumb_red / (double) (l*l);
                mean_green[1] = (double) sumb_green / (double) (l*l);
                mean_blue[1] = (double) sumb_blue / (double) (l*l);
                std_red[1] = std.getStd(arrb_red);
                std_green[1] = std.getStd(arrb_green);
                std_blue[1] = std.getStd(arrb_blue);
                mean_red[2] = (double) sumc_red / (double) (l*l);
                mean_green[2] = (double) sumc_green / (double) (l*l);
                mean_blue[2] = (double) sumc_blue / (double) (l*l);
                std_red[2] = std.getStd(arrc_red);
                std_green[2] = std.getStd(arrc_green);
                std_blue[2] = std.getStd(arrc_blue);
                mean_red[3] = (double) sumd_red / (double) (l*l);
                mean_green[3] = (double) sumd_green / (double) (l*l);
                mean_blue[3] = (double) sumd_blue / (double) (l*l);
                std_red[3] = std.getStd(arrd_red);
                std_green[3] = std.getStd(arrd_green);
                std_blue[3] = std.getStd(arrd_blue);
                double minStd_red = Double.MAX_VALUE;
                double minStd_green = Double.MAX_VALUE;
                double minStd_blue = Double.MAX_VALUE;
                double res_mean_red = 0, res_mean_green = 0, res_mean_blue = 0;
                for(int part = 0; part < 4; part++) {
                    if(std_red[part] < minStd_red) {
                        minStd_red = std_red[part];
                        res_mean_red = mean_red[part];
                    }
                    if(std_green[part] < minStd_green) {
                        minStd_green = std_green[part];
                        res_mean_green = mean_green[part];
                    }
                    if(std_blue[part] < minStd_blue) {
                        minStd_blue = std_blue[part];
                        res_mean_blue = mean_blue[part];
                    }
                }
                Color newColor = new Color((int)res_mean_red, (int)res_mean_green, (int)res_mean_blue);
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
