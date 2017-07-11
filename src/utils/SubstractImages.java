package utils;

import java.awt.image.BufferedImage;

public class SubstractImages {
    
    public BufferedImage substract(BufferedImage a, BufferedImage b) {
        if(a.getHeight() != b.getHeight() || a.getWidth() != b.getWidth() || a.getType() != b.getType())
            throw new RuntimeException("Can't compare!");    
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++) {
                int s = a.getRGB(j, i) - b.getRGB(j, i);
                a.setRGB(j,i,s);
            }
        }
        return a;
    }
    
}
