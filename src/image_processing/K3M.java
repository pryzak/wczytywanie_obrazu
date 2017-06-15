package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import utils.CloneImage;
import utils.CompareImages;

public class K3M {

    private final int[][] nMatrix = new int[][] {{128,1,2}, {64,0,4}, {32,16,8}};
    
    private final int[] a0 = new int[] {3, 6, 7, 12, 14, 15, 24, 28, 30, 31, 48, 56, 60, 62, 63, 96, 
        112, 120, 124, 126, 127, 129, 131, 135, 143, 159, 191, 192, 193, 195, 199, 207, 
        223, 224, 225, 227, 231, 239, 240, 241, 243, 247, 248, 249, 251, 252, 253, 254};
    private final int[] a1 = new int[] {7, 14, 28, 56, 112, 131, 193, 224};
    private final int[] a2 = new int[] {7, 14, 15, 28, 30, 56, 60, 112, 120, 131, 135, 193, 195, 224, 225, 240};
    private final int[] a3 = new int[] {7, 14, 15, 28, 30, 31, 56, 60, 62, 112, 120, 124,
        131,135, 143, 193, 195, 199, 224, 225, 227, 240, 241, 248};
    private final int[] a4 = new int[] {7, 14, 15, 28, 30, 31, 56, 60, 62, 63, 112, 120, 124, 126, 131, 135,
        143, 159, 193, 195, 199, 207, 224, 225, 227, 231, 240, 241, 243, 248, 249, 252};
    private final int[] a5 = new int[] {7, 14, 15, 28, 30, 31, 56, 60, 62, 63, 112, 120, 124, 126, 131, 135, 143, 159,
        191, 193, 195, 199, 207, 224, 225, 227, 231, 239, 240, 241, 243, 248, 249, 251, 252, 254};
    private final int[] a1pix = new int[] {3, 6, 7, 12, 14, 15, 24, 28, 30, 31, 48, 56, 60, 62, 63, 96,
        112, 120, 124, 126, 127, 129, 131, 135, 143, 159, 191, 192, 193, 195, 199, 207,
        223, 224, 225, 227, 231, 239, 240, 241, 243, 247, 248, 249, 251, 252, 253, 254};
        
    private int[][] calculateWeights(BufferedImage bi) {
        int[][] weights = new int[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                weights[j][i] = 0;
                for(int ii = -1; ii < 2; ii++) {
                    for(int jj = -1; jj < 2; jj++) {
                        if(j + jj < 0 || j + jj >= bi.getWidth() || i + ii < 0 || i + ii >= bi.getHeight())
                            continue;
                        Color c = new Color(bi.getRGB(j + jj, i + ii));
                        if(c.getRed() == 0)
                            weights[j][i] += nMatrix[ii + 1][jj + 1];
                    }
                }
            }
        }
        return weights;
    }
    
    private boolean isWeightInArray(int weight, int[] array) {
        for(int i = 0; i < array.length; i++) {
            if(array[i] == weight)
                return true;
        }
        return false;
    }
    
    private boolean[][] getBorders(int[][] weights, int ii, int jj) {
        boolean[][] borders = new boolean[jj][ii];
        for (int i = 0; i < ii; i++) {
            for (int j = 0; j < jj; j++) {
                borders[j][i] = isWeightInArray(weights[j][i], a0);
            }
        }
        return borders;
    }
    
    
    private BufferedImage phaseI(BufferedImage bi, boolean[][] borders, int[][] weights, int[] arrayI) {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                if(!borders[j][i])
                    continue;
                if(isWeightInArray(weights[j][i], arrayI)) {
                    bi.setRGB(j, i, new Color(255, 255, 255).getRGB());
                }
            }
        }
        return bi;
    }
    
    private BufferedImage phaseThin(BufferedImage bi, int[][] weights, int[] arrayI) {
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                if(isWeightInArray(weights[j][i], arrayI)) {
                    bi.setRGB(j, i, new Color(255, 255, 255).getRGB());
                }
            }
        }
        return bi;
    }
    
    public BufferedImage skeletonization(BufferedImage bi) {
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
        CloneImage cli = new CloneImage();
        BufferedImage prev = cli.deepCopy(bi);
        BufferedImage imgTmp = cli.deepCopy(bi);
        CompareImages ci = new CompareImages();
        while(true) {
            int[][] weights = calculateWeights(prev);
            boolean[][] borders = getBorders(weights, bi.getHeight(), bi.getWidth());
            imgTmp = phaseI(imgTmp, borders, weights, a1);
            imgTmp = phaseI(imgTmp, borders, weights, a2);
            imgTmp = phaseI(imgTmp, borders, weights, a3);
            imgTmp = phaseI(imgTmp, borders, weights, a4);
            imgTmp = phaseI(imgTmp, borders, weights, a5);
            for (int i = 0; i < bi.getHeight(); i++) {
                for (int j = 0; j < bi.getWidth(); j++) {
                    borders[j][i] = false;
                }
            }
            if(ci.compareEqual(prev, imgTmp)) {
                imgTmp = phaseThin(imgTmp, weights, a1pix);
                return imgTmp;
            }
            prev = cli.deepCopy(imgTmp);
        }
    }
    
}
