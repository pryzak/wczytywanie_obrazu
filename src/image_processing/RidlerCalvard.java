package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class RidlerCalvard {
    
    public int getThreshold(BufferedImage bi) {
        double u_b, u_ob;
        int sum_b = 0, sum_ob = 0, n_b, n_ob;
        int t0, t1;
        BufferedImage tmpBi = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        for(int i = 0; i < bi.getHeight(); i++)
            for(int j = 0; j < bi.getWidth(); j++) {
                if((j == 0 && i == 0) || (j == 0 && i == bi.getHeight() - 1) || (j == bi.getWidth() - 1 && i == 0) || (j == bi.getWidth() - 1 && i == bi.getHeight() - 1)) {
                    Color c = new Color(bi.getRGB(j, i));
                    sum_b += c.getRed();
                }
                else {
                    Color c = new Color(bi.getRGB(j, i));
                    sum_ob += c.getRed();
                }
            }
        u_b = (double)sum_b / (double) 4;
        u_ob = (double)sum_ob / (double) (bi.getWidth() * bi.getHeight() - 4);
        t0 = (int) ((u_b + u_ob) / 2);
        while(true) {
            sum_b = 0;
            sum_ob = 0;
            n_b = 0;
            n_ob = 0;
            for(int i = 0; i < bi.getHeight(); i++) {
                for(int j = 0; j < bi.getWidth(); j++) {
                    Color c = new Color(bi.getRGB(j, i));
                    if(c.getRed() < t0) {
                        sum_ob += c.getRed();
                        n_ob++;
                    }
                    else {
                        sum_b += c.getRed();
                        n_b++;
                    }
                }
            }
            u_b = (double)sum_b / (double) n_b;
            u_ob = (double)sum_ob / (double) n_ob;
            t1 = (int) ((u_b + u_ob) / 2);
            if(t1 == t0)
                return t1;
            t0 = t1;
        }
    }
    
}
