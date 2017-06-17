package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;
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
        131, 135, 143, 193, 195, 199, 224, 225, 227, 240, 241, 248};
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
    
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    
    
    private int[][] imgToBinary(BufferedImage bi) {
        int[][] bin = new int[bi.getWidth()][bi.getHeight()];
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                if(c.getRed() == 0)
                    bin[j][i] = 1;
                else if(c.getRed() == 255)
                    bin[j][i] = 0;
            }
        }
        return bin;
    }
    
    
    private int[][] markBorders(int[][] imgBin) {
        for (int i = 0; i < imgBin[0].length; i++) {
            for (int j = 0; j < imgBin.length; j++) {
                if(imgBin[j][i] == 1) {
                    if((i > 0 && imgBin[j][i-1]%2 == 0) ||
                            (i < imgBin[0].length - 1 && imgBin[j][i+1]%2 == 0) ||
                            (j > 0 && imgBin[j-1][i]%2 == 0) ||
                            (j < imgBin.length - 1 && imgBin[j+1][i]%2 == 0)) {
                        imgBin[j][i] = 3;
                    }
                }
            }
        }
        return imgBin;
    }
    
    int getWeight(int x, int y, int[][] imgBin) {
        int n = 0 , ne = 0, w = 0, e = 0, sw = 0, s = 0, se = 0, nw = 0;
        if(y > 0)
            n = imgBin[x][y - 1] % 2;
        if(y > 0 && x < imgBin.length - 1)
            ne = (imgBin[x+1][y-1] % 2) * 2;
        if(x > 0)
            w = (imgBin[x-1][y] % 2) * 64;
        if(x < imgBin.length - 1)
            e = (imgBin[x+1][y] % 2) * 4;
        if(y < imgBin[0].length - 1 && x > 0)
            sw = (imgBin[x-1][y+1] % 2) * 32;
        if(y < imgBin[0].length - 1)
            s = (imgBin[x][y+1] % 2) * 16;
        if(y < imgBin[0].length - 1 && x < imgBin.length - 1)
            se = (imgBin[x+1][y+1] % 2) * 8;
        if(y > 0 && x > 0)
            nw = (imgBin[x-1][y-1] % 2) * 128;
        return n + ne + w + e + sw + s + se + nw;
    }
    
    private int[][] phaseN(int[][] imgBin, int[] arrayI) {
        for (int i = 0; i < imgBin[0].length; i++) {
            for (int j = 0; j < imgBin.length; j++) {
                if(imgBin[j][i] == 3) {
                    if(isWeightInArray(getWeight(j, i, imgBin), arrayI)) {
                        imgBin[j][i] = 0;
                    }
                }
            }
        }
        return imgBin;
    }
    
    private int[][] phaseLast(int[][] imgBin, int[] arrayI) {
        for (int i = 0; i < imgBin[0].length; i++) {
            for (int j = 0; j < imgBin.length; j++) {
                if(isWeightInArray(getWeight(j, i, imgBin), arrayI)) {
                    imgBin[j][i] = 0;
                }
            }
        }
        return imgBin;
    }
    
    public BufferedImage skeletonization2(BufferedImage bi) {
        int[][] imgBin = imgToBinary(bi);
        boolean thinned = false;
//        imgBin = markBorders(imgBin);
        while(!thinned) {
            imgBin = markBorders(imgBin);
            int[][] imgBinCopy = imgBin.clone();
            imgBin = phaseN(imgBin, a1);
            imgBin = phaseN(imgBin, a2);
            imgBin = phaseN(imgBin, a3);
            imgBin = phaseN(imgBin, a4);
            imgBin = phaseN(imgBin, a5);
            if(Arrays.equals(imgBin, imgBinCopy)) {
                thinned = true;
            }
            for (int i = 0; i < bi.getHeight(); i++) {
                for (int j = 0; j < bi.getWidth(); j++) {
                    if(imgBin[j][i] == 3) {
                        imgBin[j][i] = 1;
                    }
                }
            }
        }
//        for (int i = 0; i < bi.getHeight(); i++) {
//            for (int j = 0; j < bi.getWidth(); j++) {
//                if(imgBin[j][i] == 3) {
//                    imgBin[j][i] = 1;
//                }
//            } 
//        }
        imgBin = phaseLast(imgBin, a1pix);
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                if(imgBin[j][i] % 2 == 0)
                    bi.setRGB(j, i, new Color(255, 255, 255).getRGB());
                else
                    bi.setRGB(j, i, new Color(0, 0, 0).getRGB());
            }
        }
        return bi;
    }
    
}
