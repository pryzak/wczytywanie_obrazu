package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import utils.CloneImage;
import utils.CompareImages;
import utils.RotateMatrix;

public class Morphology {
    
//    private SE_Point morf0[][] = new SE_Point[][] { { new SE_Point(1), new SE_Point(1), new SE_Point(1) },
//                                                    { new SE_Point(1), new SE_Point(1, true), new SE_Point(1) },
//                                                    { new SE_Point(1), new SE_Point(1), new SE_Point(1) } };
    private SE morf0 = new SE(new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }, 1, 1);
    private float morf1[][] = new float[][] { { 0, 0, 0 }, { 1, 1, 1 }, { 0, 0, 0 } };
    private float morf2[][] = new float[][] { { 0, 1, 0 }, { 0, 1, 0 }, { 0, 1, 0 } };
    private float morf3[][] = new float[][] { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } };
    
    
    
    public BufferedImage dilation(BufferedImage bi, SE se) throws RuntimeException {        
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                if((c.getRed() != 0 && c.getRed() != 255) ||
                       (c.getGreen() != 0 && c.getGreen() != 255) ||
                        (c.getBlue() != 0 && c.getBlue() != 255) ||
                        (c.getRed() != c.getGreen() || c.getRed() != c.getBlue() || c.getGreen() != c.getBlue()))
                    throw new RuntimeException("Image is not binary   " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
            }
        }
        int[][] newColors = new int[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                newColors[j][i] = bi.getRGB(j, i);
                for(int y = i - se.getyOrigin(), yy = 0; y < i - se.getyOrigin() + se.getMask().length; y++, yy++) {
                    if(y < 0 || y > bi.getHeight() - 1)
                       continue;
                    for(int x = j - se.getxOrigin(), xx = 0; x < j - se.getxOrigin() + se.getMask()[yy].length; x++, xx++) {
                        if(x < 0 || x > bi.getWidth() - 1)
                            continue;
                        Color c = new Color(bi.getRGB(x, y));
                        int col = c.getRed();
                        if(se.getMask()[yy][xx] == 1 && col == 0) {
                            Color newColor = new Color(0, 0, 0);
                            newColors[j][i] = newColor.getRGB();
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                bi.setRGB(j, i, newColors[j][i]);
            }
        }
        return bi;
    }
    
    public BufferedImage erosion(BufferedImage bi, SE se) throws RuntimeException {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                if((c.getRed() != 0 && c.getRed() != 255) ||
                       (c.getGreen() != 0 && c.getGreen() != 255) ||
                        (c.getBlue() != 0 && c.getBlue() != 255) ||
                        (c.getRed() != c.getGreen() || c.getRed() != c.getBlue() || c.getGreen() != c.getBlue()))
                    throw new RuntimeException("Image is not binary");
            }
        }
        int[][] newColors = new int[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                newColors[j][i] = bi.getRGB(j, i);
                for(int y = i - se.getyOrigin(), yy = 0; y < i - se.getyOrigin() + se.getMask().length; y++, yy++) {
                    if(y < 0 || y > bi.getHeight() - 1)
                       continue;
                    for(int x = j - se.getxOrigin(), xx = 0; x < j - se.getxOrigin() + se.getMask()[yy].length; x++, xx++) {
                        if(x < 0 || x > bi.getWidth() - 1)
                            continue;
                        Color c = new Color(bi.getRGB(x, y));
                        int col = c.getRed();
                        if(se.getMask()[yy][xx] == 1 && col == 255) {
                            Color newColor = new Color(255, 255, 255);
                            newColors[j][i] = newColor.getRGB();
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                bi.setRGB(j, i, newColors[j][i]);
            }
        }
        return bi;
    }
    
    public BufferedImage hitOrMiss(BufferedImage bi, SE se) throws RuntimeException {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                if((c.getRed() != 0 && c.getRed() != 255) ||
                       (c.getGreen() != 0 && c.getGreen() != 255) ||
                        (c.getBlue() != 0 && c.getBlue() != 255) ||
                        (c.getRed() != c.getGreen() || c.getRed() != c.getBlue() || c.getGreen() != c.getBlue()))
                    throw new RuntimeException("Image is not binary");
            }
        }
        int[][] newColors = new int[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                boolean hit = true;
                newColors[j][i] = bi.getRGB(j, i);
                for(int y = i - se.getyOrigin(), yy = 0; y < i - se.getyOrigin() + se.getMask().length; y++, yy++) {
                    if(y < 0 || y > bi.getHeight() - 1)
                       continue;
                    for(int x = j - se.getxOrigin(), xx = 0; x < j - se.getxOrigin() + se.getMask()[yy].length; x++, xx++) {
                        if(x < 0 || x > bi.getWidth() - 1)
                            continue;
                        if(se.getMask()[yy][xx] != 1 && se.getMask()[yy][xx] != 0)
                            continue;
                        Color c = new Color(bi.getRGB(x, y));
                        int col = c.getRed();
                        if(se.getMask()[yy][xx] == 1 && col == 255) {
                            hit = false;
                            break;
                        }
                        else if(se.getMask()[yy][xx] == 0 && col == 0) {
                            hit = false;
                            break;
                        }
                    }
                }
                if(hit) {
                    Color newColor = new Color(0, 0, 0);
                    newColors[j][i] = newColor.getRGB();
                }
                else {
                    Color newColor = new Color(255, 255, 255);
                    newColors[j][i] = newColor.getRGB();
                }
            }
        }
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                bi.setRGB(j, i, newColors[j][i]);
            }
        }
        return bi;
    }
    
    public BufferedImage thinning(BufferedImage bi, SE se) {
        CloneImage ci = new CloneImage();
        BufferedImage hom = ci.deepCopy(bi);
        hom = hitOrMiss(hom, se);
        BufferedImage result = substract(bi, hom);
        return result;
    }
    
    public BufferedImage substract(BufferedImage a, BufferedImage b) {
        BufferedImage result = new CloneImage().deepCopy(a);
        for(int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++) {
                Color cHom = new Color(b.getRGB(j, i));
                Color cA = new Color(a.getRGB(j, i));
                if(cHom.getRed() == 0 && cA.getRed() == 0) {
                    result.setRGB(j, i, new Color(255, 255, 255).getRGB());
                }
            }
        }
        return result;
    }
    
    
    
    public BufferedImage skeletonization(BufferedImage bi) {
        RotateMatrix rm = new RotateMatrix();
        int[][] se11 = new int[][] {{0, 0, 0}, {-1, 1, -1}, {1, 1, 1}};
        int[][] se12 = rm.rotate90(se11);
        int[][] se13 = rm.rotate90(se12);
        int[][] se14 = rm.rotate90(se13);
        int[][] se21 = new int[][] {{-1, 0, 0}, {1, 1, 0}, {-1, 1, -1}};
        int[][] se22 = rm.rotate90(se21);
        int[][] se23 = rm.rotate90(se22);
        int[][] se24 = rm.rotate90(se23);
        SE se11_ = new SE(se11, 1, 1);
        SE se12_ = new SE(se12, 1, 1);
        SE se13_ = new SE(se13, 1, 1);
        SE se14_ = new SE(se14, 1, 1);
        SE se21_ = new SE(se21, 1, 1);
        SE se22_ = new SE(se22, 1, 1);
        SE se23_ = new SE(se23, 1, 1);
        SE se24_ = new SE(se24, 1, 1);
        BufferedImage prev = new CloneImage().deepCopy(bi);
        BufferedImage result = new CloneImage().deepCopy(bi);
        CompareImages ci = new CompareImages();
        while(true) {
            result = thinning(result, se11_);
            result = thinning(result, se12_);
            result = thinning(result, se13_);
            result = thinning(result, se14_);
            result = thinning(result, se21_);
            result = thinning(result, se22_);
            result = thinning(result, se23_);
            result = thinning(result, se24_);
            if(ci.compareEqual(prev, result))
                return result;
            prev = new CloneImage().deepCopy(result);
        }
    }
    
    public BufferedImage extractEdges(BufferedImage bi) {
        CloneImage ci = new CloneImage();
        BufferedImage biClone = ci.deepCopy(bi);
        BufferedImage biClone2 = ci.deepCopy(bi);
        biClone = erosion(biClone, morf0);
        biClone2 = substract(biClone2, biClone);
        return biClone2;
    }
    
}
