/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Krzysiek
 */
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
        horizontalSides[nHorizontal - 1] = restHorizontal;
        for(int i = 0; i < nVertical - 1; i++)
            verticalSides[i] = side;
        verticalSides[nVertical - 1] = restVertical;
        BufferedImage[][] bis = new BufferedImage[nVertical][nHorizontal];
        int biType = bi.getType();
        for(int i = 0; i < nVertical; i++) {
            for(int j = 0; j < nHorizontal; j++) {
                bis[i][j] = new BufferedImage(horizontalSides[j], verticalSides[i], biType);
            }
        }
        for(int i = 0; i < bi.getHeight(); i++) {
            for(int j = 0; j < bi.getWidth(); j++) {
                
                //TODO wypelnianie mniejszych obrazow pikselami wiekszego
            }
        }
        
        //TODO
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
                        Color c = new Color(bi.getRGB(j, i));
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
