package image_processing;

import java.awt.image.BufferedImage;
import utils.CloneImage;
import utils.ImageToArray;

public class KapurSahooWong {
    
     public int getThreshold(BufferedImage bi) {
        Binarization b = new Binarization();
        CloneImage ci = new CloneImage();
        double[] h = new double[256];
        int count = bi.getWidth() * bi.getHeight();
        //
        int[] f = new HistogramHelper().getHistogram(new ImageToArray().convertToArrayGray(bi));
        double[] p = new double[256];
        for(int t = 0; t < 256; t++) {
            p[t] = (double) f[t] / (double) count;
        }
        //
        for(int t = 0; t < 256; t++) {
            //
//            BufferedImage biClone = ci.deepCopy(bi);
//            biClone = b.binarize(biClone, t);
//            int[] f = new HistogramHelper().getHistogram(new ImageToArray().convertToArrayGray(biClone));
//            double[] p = new double[256];
//            for(int tt = 0; tt < 256; tt++) {
//                p[tt] = (double) f[tt] / (double) count;
//            }
            //
            double p_ob = 0, p_b = 0;
            for(int k = 0; k <= t; k++) {
                p_ob += p[k];
            }
            for(int l = t+1; l < 256; l++) {
                p_b += p[l];
            }
            double pkpob = 0, pkpb = 0;
            for(int k = 0; k <= t; k++) {
                if(p_ob != 0.0 && p[k] != 0.0) {
                    pkpob += (p[k] / p_ob) * (Math.log(p[k] / p_ob) / Math.log(2));
                }
            }
            for(int l = t+1; l < 256; l++) {
                if(p_b != 0.0 && p[l] != 0.0) {
                    pkpb += (p[l] / p_b) * (Math.log(p[l] / p_b) / Math.log(2));
                }
            }
            double h_ob = - pkpob;
            double h_b = - pkpb;
            h[t] = h_ob + h_b;
        }
        double hMax = 0;
        int threshold = 127;
        for(int t = 0; t < 256; t++) {
            if(h[t] > hMax) {
                hMax = h[t];
                threshold = t;
            }
        }
        return threshold;
    }
     
    public int getThreshold2(BufferedImage bi) {
        int[] data = new HistogramHelper().getHistogram(new ImageToArray().convertToArrayGray(bi));
        int threshold=-1;
		int ih, it;
		int first_bin;
		int last_bin;
		double tot_ent;  /* total entropy */
		double max_ent;  /* max entropy */
		double ent_back; /* entropy of the background pixels at a given threshold */
		double ent_obj;  /* entropy of the object pixels at a given threshold */
		double [] norm_histo = new double[256]; /* normalized histogram */
		double [] P1 = new double[256]; /* cumulative normalized histogram */
		double [] P2 = new double[256]; 

		double total =0;
		for (ih = 0; ih < 256; ih++ ) 
			total+=data[ih];

		for (ih = 0; ih < 256; ih++ )
			norm_histo[ih] = data[ih]/total;

		P1[0]=norm_histo[0];
		P2[0]=1.0-P1[0];
		for (ih = 1; ih < 256; ih++ ){
			P1[ih]= P1[ih-1] + norm_histo[ih];
			P2[ih]= 1.0 - P1[ih];
		}

		/* Determine the first non-zero bin */
		first_bin=0;
		for (ih = 0; ih < 256; ih++ ) {
			if ( !(Math.abs(P1[ih])<2.220446049250313E-16)) {
				first_bin = ih;
				break;
			}
		}

		/* Determine the last non-zero bin */
		last_bin=255;
		for (ih = 255; ih >= first_bin; ih-- ) {
			if ( !(Math.abs(P2[ih])<2.220446049250313E-16)) {
				last_bin = ih;
				break;
			}
		}

		// Calculate the total entropy each gray-level
		// and find the threshold that maximizes it 
		max_ent = Double.MIN_VALUE;

		for ( it = first_bin; it <= last_bin; it++ ) {
			/* Entropy of the background pixels */
			ent_back = 0.0;
			for ( ih = 0; ih <= it; ih++ )  {
				if ( data[ih] !=0 ) {
					ent_back -= ( norm_histo[ih] / P1[it] ) * Math.log ( norm_histo[ih] / P1[it] );
				}
			}

			/* Entropy of the object pixels */
			ent_obj = 0.0;
			for ( ih = it + 1; ih < 256; ih++ ){
				if (data[ih]!=0){
				ent_obj -= ( norm_histo[ih] / P2[it] ) * Math.log ( norm_histo[ih] / P2[it] );
				}
			}

			/* Total entropy */
			tot_ent = ent_back + ent_obj;

			// IJ.log(""+max_ent+"  "+tot_ent);
			if ( max_ent < tot_ent ) {
				max_ent = tot_ent;
				threshold = it;
			}
		}
		return threshold;
    }

     
}
