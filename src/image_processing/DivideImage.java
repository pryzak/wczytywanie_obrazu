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
        
        //TODO
        return null;
    }
    
    public BufferedImage connect(BufferedImage[][] bis) {
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
