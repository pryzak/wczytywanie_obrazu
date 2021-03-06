package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class DivideImage {
    
    public BufferedImage[][] divide(BufferedImage bi, int side) {
        int nHorizontal = bi.getWidth() / side;
        if(nHorizontal * side < bi.getWidth())
            nHorizontal++;
        int nVertical = bi.getHeight() / side;
        if(nVertical * side < bi.getHeight())
            nVertical++;
        int[] horizontalSides = new int[nHorizontal];
        int[] verticalSides = new int[nVertical];
        int restHorizontal = bi.getWidth() - ((bi.getWidth() / side) * side);
        int restVertical = bi.getHeight()- ((bi.getHeight() / side) * side);     
        for(int i = 0; i < nHorizontal - 1; i++)
            horizontalSides[i] = side;
        if(restHorizontal > 0)
            horizontalSides[nHorizontal - 1] = restHorizontal;
        else
            horizontalSides[nHorizontal - 1] = side;
        for(int i = 0; i < nVertical - 1; i++)
            verticalSides[i] = side;
        if(restVertical > 0)
            verticalSides[nVertical - 1] = restVertical;
        else
            verticalSides[nVertical - 1] = side;
        BufferedImage[][] bis = new BufferedImage[nVertical][nHorizontal];
        int biType = bi.getType();
        for(int i = 0; i < nVertical; i++) {
            for(int j = 0; j < nHorizontal; j++) {
                bis[i][j] = new BufferedImage(horizontalSides[j], verticalSides[i], biType);
            }
        }
        int x = 0, y = 0;
        int pastX = 0, pastY = 0;
        for(int i = 0; i < bis.length; i++) {
            for(int j = 0; j < bis[i].length; j++) {
                y = 0 + pastY;
                for(int yy = 0; yy < verticalSides[i]; yy++) {
                    x = 0 + pastX;
                    for(int xx = 0; xx < horizontalSides[j]; xx++) {
                        Color c = new Color(bi.getRGB(x, y));
                        bis[i][j].setRGB(xx, yy, c.getRGB());
                        x++;
                    }
                    y++;
                }
                pastX += horizontalSides[j];
            }
            pastX = 0;
            pastY += verticalSides[i];
        }
        return bis;
    }
    
    public BufferedImage merge(BufferedImage[][] bis) {
        int width = 0, height = 0;
        for (BufferedImage[] bi : bis) {
            height += bi[0].getHeight();
        }
        for (BufferedImage bi : bis[0]) {
            width += bi.getWidth();
        }
        int localX = 0, localY = 0, globalX = 0, globalY = 0;
        BufferedImage bi = new BufferedImage(width, height, bis[0][0].getType());
        int tempY = 0;
        for (BufferedImage[] bi__ : bis) {
            tempY = bi__[0].getHeight();
            for (BufferedImage bi_ : bi__) {
                localY = globalY;
                for(int i = 0; i < bi_.getHeight(); i++) {
                    localX = globalX;
                    for(int j = 0; j < bi_.getWidth(); j++) {
                        Color c = new Color(bi_.getRGB(j, i));
                        bi.setRGB(localX, localY, c.getRGB());
                        localX++;
                    }
                    localY++;
                }
                globalX += bi_.getWidth();
            }
            globalX = 0;
            globalY += tempY;
        }
        return bi;
    }
    
}
