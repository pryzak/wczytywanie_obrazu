package image_processing;

public class BHT {
    
//    public double meanUntilL(int l, int[] histogram) {
//        if(l >= histogram.length)
//            throw new RuntimeException("Wrong input");
//        double ul = 0;
//        int fi_sum = 0;
//        for(int i = 0; i <= l; i++) {
//            fi_sum += histogram[i];
//            ul += (double)(i * histogram[i]) / (double) fi_sum;
//        }
//        return ul;
//    }
    
    public double meanUntilL(int l, int[] histogram) {
        if(l >= histogram.length)
            throw new RuntimeException("Wrong input");
        int ixfi = 0;
        int fi_sum = 0;
        for(int i = 0; i <= l; i++) {
            fi_sum += histogram[i];
            ixfi += i * histogram[i];
            
        }
        return (double) ixfi / (double) fi_sum;
    }
    
    public double varianceUntilL(int l, int[] histogram) {
        if(l >= histogram.length)
            throw new RuntimeException("Wrong input");
        int fi_sum = 0;
        for(int i = 0; i <= l; i++) {
           fi_sum += histogram[i]; 
        }
        double a = (double) 1 / (double) fi_sum;
        double b = 0;
        double mean_i = meanUntilL(l, histogram);
        for(int i = 0; i <= l; i++) {
            b += (double) histogram[i] * (double)(i - mean_i) * (double)(i - mean_i);
        }
        return a * b;
    }
    
    public int findL(int[] histogram) {
        int uL = (int) Math.ceil((double)meanUntilL(histogram.length - 1, histogram));
        double[] vars = new double[histogram.length];
        for(int i = uL; i < histogram.length; i++) {
            vars[i] = varianceUntilL(i, histogram);
        }
        for(int i = uL; i < vars.length; i++) {
            if(vars[i - 1] > vars[i])
                return i;
        }
        return 0;
    }
    
    public int findIS(int[] histogram) {
        for(int i = 0; i < histogram.length; i++) {
            if(histogram[i] != 0)
                return i;
        }
        return histogram.length - 1;
    }
    
    public int findIE(int[] histogram) {
        for(int i = histogram.length - 1; i >= 0; i--) {
            if(histogram[i] != 0)
                return i;
        }
        return 0;
        
    }
    
    public int findWL(int[] histogram, int IS, int IM) {
        int fi_sum = 0;
        for(int i = IS; i <= IM; i++) {
            fi_sum += histogram[i];
        }
        return fi_sum;
    }
    
    public int findWR(int[] histogram, int IE, int IM) {
        int fi_sum = 0;
        for(int i = IM + 1; i <= IE; i++) {
            fi_sum += histogram[i];
        }
        return fi_sum;
    }
    
    public int findThreshold(int[] grayScaleValues) {
        int[] histogram = new HistogramHelper().getHistogram(grayScaleValues);
        int IS = findIS(histogram);
        int IE = findIE(histogram);
        int IM = (int) (((double) IS + (double) IE) / (double) 2);
        int WL = findWL(histogram, IS, IM);
        int WR = findWR(histogram, IE, IM);
        while(WR > WL) {
            WR -= histogram[IE--];
            if(((double) IS + (double) IE) / (double) 2 < IM && IM >= 0 && IM < histogram.length) {
                WL -= histogram[IM];
                WR += histogram[IM--];
            }
        }
        return IM;
    }
    
    public int findThreshold2(int[] grayScaleValues) {
        int[] histogram = new HistogramHelper().getHistogram(grayScaleValues);
        int IS = findIS(histogram);
        int IE = findIE(histogram);
        int IM = (int) (((double) IS + (double) IE) / (double) 2);
        int WL = findWL(histogram, IS, IM);
        int WR = findWR(histogram, IE, IM);
        while(IS != IE) {
            if(WR > WL) {
                WR -= histogram[IE--];
                if(((double) IS + (double) IE) / (double) 2 < IM && IM >= 0 && IM < histogram.length) {
                    WL -= histogram[IM];
                    WR += histogram[IM--];
                }
            }
            else {
                WL += histogram[IS++];
                if(((double) IS + (double) IE) / (double) 2 >= IM && IM >= 0 && IM < histogram.length - 1) {
                    WL += histogram[IM + 1];
                    WR -= histogram[IM + 1];
                    IM++;
                }
            }
        }
        return IM;
    }
    
    public int findThreshold3(int[] grayScaleValues) {
        
        int[] histogram = new HistogramHelper().getHistogram(grayScaleValues);
        int IS = findIS(histogram);
        int IE = findIE(histogram);
        int IM = (int) (((double) IS + (double) IE) / (double) 2);
        int WL = findWL(histogram, IS, IM);
        int WR = findWR(histogram, IE, IM);
        while(IS <= IE) {
            if(WR > WL) {
                WR -= histogram[IE--];
                if(((double) IS + (double) IE) / (double) 2 < IM && IM >= 0 && IM < histogram.length) {
                    WR += histogram[IM];
                    WL -= histogram[IM--];
                }
            } else {
                WL -= histogram[IS++]; 
                if(((double) IS + (double) IE) / (double) 2 >= IM && IM >= 0 && IM < histogram.length - 1) {
                    WL += histogram[IM + 1];
                    WR -= histogram[IM + 1];
                    IM++;
                }
            }
        }
        return IM;
    }
    
    public int findThreshold4(int[] grayScaleValues) {
        int[] histogram = new HistogramHelper().getHistogram(grayScaleValues);
        int l = findL(histogram);
        double varL = varianceUntilL(histogram.length - 1, histogram);
        double sqrtvar = Math.sqrt(varL);
        return (int) (l - sqrtvar);
    }
}
