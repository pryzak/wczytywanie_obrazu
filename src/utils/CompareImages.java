package utils;

import java.awt.image.BufferedImage;

public class CompareImages {
    
    public boolean compareEqual(BufferedImage a, BufferedImage b) {
        if(a.getHeight() != b.getHeight() || a.getWidth() != b.getWidth() || a.getType() != b.getType())
            throw new RuntimeException("Can't compare!");
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++) {
                if(a.getRGB(j, i) != b.getRGB(j, i))
                    return false;
            }
        }
        return true;
    }
    
    public double comparePercent(BufferedImage a, BufferedImage b) {
        if(a.getHeight() != b.getHeight() || a.getWidth() != b.getWidth() || a.getType() != b.getType())
            throw new RuntimeException("Can't compare!");
        int mn = a.getWidth() * a.getHeight();
        int correct = 0;
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++) {
                if(a.getRGB(j, i) == b.getRGB(j, i))
                    correct ++;
            }
        }
        double result = (double) correct / (double) mn;
        return result;
    }
    
}
