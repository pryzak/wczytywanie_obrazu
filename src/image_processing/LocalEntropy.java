package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LocalEntropy {

    public int getThreshold(BufferedImage bi) {
        int w[][] = new int[256][256];
        double p[][] = new double[256][256];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                for (int m = 0; m < bi.getWidth(); m++) {
                    for (int n = 0; n < bi.getHeight(); n++) {
                        int d = 0;
                        if(m < bi.getWidth() - 1 && n < bi.getHeight() - 1) {
                            Color c = new Color(bi.getRGB(m, n));
                            int x = c.getRed();
                            c = new Color(bi.getRGB(m + 1, n));
                            int y = c.getRed();
                            c = new Color(bi.getRGB(m, n + 1));
                            int z = c.getRed();
                            if (x == i && (y == j || z == j)) {
                                d = 1;
                            }
                        }
                        w[i][j] += d;
                    }
                }
            }
        }
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int pom;
                p[i][j] = (double) w[i][j] / (double) (bi.getWidth() * bi.getHeight() * w[i][j]);
            }
        }
        int maxt = 0;
        for (int t = 0; t < 256; t++) {
            double pat = 0;
            for (int i = 0; i <= t; i++) {
                for (int j = 0; j <= t; j++) {
                    pat += p[i][j];
                }
            }
            double pdt = 0;
            for (int i = t+1; i < 256; i++) {
                for (int j = t+1; j < 256; j++) {
                    pdt += p[i][j];
                }
            }
            double hat = 0;
            for (int i = 0; i <= t; i++) {
                for (int j = 0; j <= t; j++) {
                    hat += (p[i][j] / pat) * ((Math.log(p[i][j] / pat) / Math.log(2)));
                }
            }
            hat *= -1;
            double hdt = 0;
            for (int i = t+1; i < 256; i++) {
                for (int j = t+1; j < 256; j++) {
                    hdt += (p[i][j] / pdt) * ((Math.log(p[i][j] / pdt) / Math.log(2)));
                }
            }
            hdt *= -1;
            double hadt = hat + hdt;
            if(hadt > maxt)
                maxt = (int) hadt;
        }
        return maxt;
    }

}
