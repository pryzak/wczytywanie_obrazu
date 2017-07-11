package image_processing;

import java.util.concurrent.ThreadLocalRandom;

public class OptimalDistribution {
    
    public int getThreshold(int[] grayScaleValues) {
        int[] histogram = new HistogramHelper().getHistogram(grayScaleValues);
        double[] p = new double[256];
        for(int i = 0; i < 256; i++) {
            p[i] = (double)histogram[i] / (double)grayScaleValues.length;
        }
        int t_t = ThreadLocalRandom.current().nextInt(0, 256);
        double w_b, w_f, u_b, u_f;
        while(true) {
            w_b = 0;
            w_f = 0;
            u_b = 0;
            u_f = 0;
            for(int i = 0; i < t_t; i++) {
                w_b += p[i];
            }
            for(int i = t_t; i < 256; i++) {
                w_f += p[i];
            }
            for(int i = 0; i < t_t; i++) {
                if(w_b != 0)
                    u_b += p[i] * (double)(i / w_b);
            }
            for(int i = t_t; i < 256; i++) {
                if(w_f != 0)
                    u_f += p[i] * (double)(i / w_f);
            }
            int t_t1 = (int) ((u_b + u_f) / 2);
            if(t_t1 == t_t)
                return t_t1;
            t_t = t_t1;
        }
    }
    
}
