package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
                newColors[j][i] = new Color(255, 255, 255).getRGB();
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
//                            bi.setRGB(j, i, newColor.getRGB());
                            newColors[j][i] = newColor.getRGB();
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
                newColors[j][i] = new Color(0, 0, 0).getRGB();
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
//                            bi.setRGB(j, i, newColor.getRGB());
                            newColors[j][i] = newColor.getRGB();
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
}
