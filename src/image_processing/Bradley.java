package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Bradley {
   
    public BufferedImage binarize(BufferedImage bi, int s, int t) {
        int[][] intImg = new int[bi.getWidth()][bi.getHeight()];
        int[][] out = new int[bi.getWidth()][bi.getHeight()];
        for(int i = 0; i < bi.getWidth(); i++) {
            int sum = 0;
            for(int j = 0; j < bi.getHeight(); j++) {
                int gray = new Color(bi.getRGB(i, j)).getRed();
                sum += gray;
                if(i == 0)
                    intImg[i][j] = sum;
                else
                    intImg[i][j] = intImg[i-1][j] + sum;
            }
        }
        for(int i = 0; i < bi.getWidth(); i++) {
            for(int j = 0; j < bi.getHeight(); j++) {
                int x1 = i - s/2;
                int x2 = i + s/2;
                int y1 = j - s/2;
                int y2 = j + s/2;
                int count = (x2 - x1) * (y2 - y1);
                int sum = 0;
//                if(x2 >= 0 && x2 < bi.getWidth() && y2 >= 0 && y2 < bi.getHeight())
//                    sum += intImg[x2][y2];
//                if(x2 >= 0 && x2 < bi.getWidth() && y1-1 >= 0 && y1-1 < bi.getHeight())
//                    sum -= intImg[x2][y1-1];
//                if(x1-1 >= 0 && x1-1 < bi.getWidth() && y2 >= 0 && y2 < bi.getHeight())
//                    sum -= intImg[x1-1][y2];
//                if(x1-1 >= 0 && x1-1 < bi.getWidth() && y1-1 >= 0 && y1-1 < bi.getHeight())
//                    sum += intImg[x1-1][y1-1];
                if (x1 - 1 < 0)
                    x1 = 1;
                if(x2 < 0)
                    x2 = 0;
                if (x2 >= bi.getWidth())
                    x2 = bi.getWidth() - 1;
                if (x1 >= bi.getWidth())
                    x1 = bi.getWidth() - 1;
                if (y1 - 1 < 0)
                    y1 = 1;
                if (y2 < 0)
                    y2 = 0;
                if (y2 >= bi.getHeight())
                    y2 = bi.getHeight() - 1;
                if (y1 >= bi.getHeight())
                    y1 = bi.getHeight() - 1;
                sum = intImg[x2][y2] - intImg[x2][y1-1] - intImg[x1-1][y2] + intImg[x1-1][y1-1];
//                try {
//                    sum = intImg[x2][y2] - intImg[x2][y1-1] - intImg[x1-1][y2] + intImg[x1-1][y1-1];
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    
//                }
                int gray = new Color(bi.getRGB(i, j)).getRed();
                if((double)((double)gray * (double)count) <= (double) ((double)sum * ((double)100 - (double)t) / (double)100))
                    out[i][j] = 0;
                else
                    out[i][j] = 255;
            }
        }
        for(int i = 0; i < bi.getWidth(); i++) {
            for(int j = 0; j < bi.getHeight(); j++) {
                
                bi.setRGB(i, j, new Color(out[i][j], out[i][j], out[i][j]).getRGB());
            }
        }
        return bi;
    }
    
}
