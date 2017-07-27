package utils;

import image_processing.GrayScale;
import image_processing.Morphology;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CompareImages {
    
    public boolean compareEqual(BufferedImage a, BufferedImage b) {
        if(a.getHeight() != b.getHeight() || a.getWidth() != b.getWidth())
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
        if(a.getHeight() != b.getHeight() || a.getWidth() != b.getWidth())
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
    
    
    private class Pixel {
        private int x;
        private int y;
        boolean bin;
        Pixel() {}
        Pixel(int x, int y, boolean bin) {this.x = x; this.y = y; this.bin = bin;}
        public int getX() {return x;}
        public void setX(int x) {this.x = x;}
        public int getY() {return y;}
        public void setY(int y) {this.y = y;}
        public boolean getBin() {return bin;}
        public void setBin(boolean bin) {this.bin = bin;}
    }
    
    
//    public double calculateMissclaficationError(BufferedImage result, BufferedImage groundTruth) {
//        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth() || result.getType() != groundTruth.getType())
//            throw new RuntimeException("Can't calculate!");
//        List<Pixel> resultPixels = new ArrayList<>();
//        List<Pixel> gtPixels = new ArrayList<>();
//        for(int i = 0; i < result.getWidth(); i++) {
//            for(int j = 0; j < result.getHeight(); j++) {
//                int resCol = new Color(result.getRGB(i, j)).getRed();
//                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
//                boolean resBin = false;
//                boolean gtBin = false;
//                if(resCol == 255) resBin = true;
//                if(gtCol == 255) gtBin = true;
//                resultPixels.add(new Pixel(i, j, resBin));
//                gtPixels.add(new Pixel(i, j, gtBin));
//            }
//        }
//        List<Pixel> b_o = new ArrayList<>();
//        List<Pixel> b_t = new ArrayList<>();
//        List<Pixel> f_o = new ArrayList<>();
//        List<Pixel> f_t = new ArrayList<>();
//        for(Pixel p : gtPixels) {
//            if(p.getBin()) b_o.add(p);
//            else f_o.add(p);
//        }
//        for(Pixel p : resultPixels) {
//            if(p.getBin()) b_t.add(p);
//            else f_t.add(p);
//        }
//        
//        return 1;
//    }
    
    public double calculateMissclaficationError(BufferedImage result, BufferedImage groundTruth) {
        return 1.0 - comparePercent(result, groundTruth);
//        BigDecimal bd = new BigDecimal(1.0 - comparePercent(result, groundTruth));
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    
    
    public double calculateRegionNonuniformity(BufferedImage original, BufferedImage result) {
        if(original.getHeight() != result.getHeight() || original.getWidth() != result.getWidth())
            throw new RuntimeException("Can't compare!");
        original = new GrayScale().grayScaleAvg(original);
        int f_t = 0;
        int b_t = 0;
        List<Integer> whole = new ArrayList<>();
        List<Integer> fg = new ArrayList<>();
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int origCol = new Color(original.getRGB(i, j)).getRed();
                if(resCol == 255) b_t++;
                else if(resCol == 0) {
                    f_t++;
                    fg.add(origCol);
                }
                whole.add(origCol);
            }
        }
        StandardDeviation std = new StandardDeviation();
        int[] wholeMatrix = new int[whole.size()];
        int[] fgMatrix = new int[fg.size()];
        for(int i = 0; i < whole.size(); i++) {
            wholeMatrix[i] = whole.get(i);
        }
        for(int i = 0; i < fg.size(); i++) {
            fgMatrix[i] = fg.get(i);
        }
        double varWhole = std.getVar(wholeMatrix);
        double varFg = std.getVar(fgMatrix);
        if(varWhole == 0)
            return 0;
        double nu = (double) f_t / ((double) f_t + (double) b_t) * (varFg / varWhole);
        return nu;
//        BigDecimal bd = new BigDecimal(nu);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
        
    }
    
    public double calculateRelativeForegroundAreaError(BufferedImage result, BufferedImage groundTruth) {
        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth())
            throw new RuntimeException("Can't compare!");
        int a_t = 0, a_o = 0;
        int common = 0;
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
                if(resCol == 0)
                    a_t++;
                if(gtCol == 0)
                    a_o++;
                if(resCol == 0 && gtCol == 0)
                    common++;
            }
        }
        double rae = 0;
        if(a_t < a_o) {
            rae = ((double) a_o - (double) common) / (double) a_o;
        }
        else {
            rae = ((double) a_t - (double) common) / (double) a_t;
        }
        return rae;
//        BigDecimal bd = new BigDecimal(rae);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    public double calculateEdgeMismatch(BufferedImage result, BufferedImage groundTruth) {
        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth())
            throw new RuntimeException("Can't compare!");
        int c_e = 0;
        List<Pixel> e_o = new ArrayList<>();
        List<Pixel> e_t = new ArrayList<>();
        Morphology morph = new Morphology();
        BufferedImage resEdges = morph.extractEdges(result);
        BufferedImage gtEdges = morph.extractEdges(groundTruth);
        for (int i = 0; i < result.getHeight(); i++) {
            for (int j = 0; j < result.getWidth(); j++) {
                int resCol = new Color(resEdges.getRGB(j, i)).getRed();
                int gtCol = new Color(gtEdges.getRGB(j, i)).getRed();
                if(resCol == 0 && gtCol == 0) {
                    c_e++;
                }
                else if(resCol == 255 && gtCol == 0) {
                    e_o.add(new Pixel(j, i, false));
                }
                else if(gtCol == 255 && resCol == 0) {
                    e_t.add(new Pixel(j, i, false));
                }
            }
        }
        int n = result.getWidth() * result.getHeight();
        double maxdist = 0.025 * (double) n;
        double d_max = 0.1 * (double) n;
        double w = (double) 10 / (double) n;
        int alpha = 2;
        double sum_eo = 0, sum_et = 0;
        for(Pixel p : e_o) {
            //TODO
        }
        //TODO
        return 0;
    }
    
    
    private double euclidesDistance(Pixel a, Pixel b) {
        return Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
    }
    
    public double ME(BufferedImage result, BufferedImage groundTruth) {
        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth())
            throw new RuntimeException("Can't compare!");
        double tp = 0, fp = 0, tn = 0, fn = 0;
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
                if(resCol == 0 && gtCol == 0)
                    tp++;
                else if(resCol == 0 && gtCol == 255)
                    fp++;
                else if(resCol == 255 && gtCol == 255)
                    tn++;
                else if(resCol == 255 && gtCol == 0)
                    fn++;
            }
        }
        double me = (double)1 - ((tp + tn) / (tp + fn + tn + fp));
        return me;
//        BigDecimal bd = new BigDecimal(me);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    public double RFAE(BufferedImage result, BufferedImage groundTruth) {
        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth())
            throw new RuntimeException("Can't compare!");
        double tp = 0, fp = 0, tn = 0, fn = 0;
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
                if(resCol == 0 && gtCol == 0)
                    tp++;
                else if(resCol == 0 && gtCol == 255)
                    fp++;
                else if(resCol == 255 && gtCol == 255)
                    tn++;
                else if(resCol == 255 && gtCol == 0)
                    fn++;
            }
        }
        double rfae;
        if(fp + tp < tp + fn) {
            rfae = (fn - fp) / (tp + fn);
//            rfae = ((tp + fn) - (fp + tp)) / (tp + fn);
        }
        else {
            rfae = (fp - fn) / (fp + tp);
//            rfae = ((fp + tp) - (tp + fn)) / (fp + tp);
        }
        return rfae;
//        BigDecimal bd = new BigDecimal(rfae);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    public double ACCURACY(BufferedImage result, BufferedImage groundTruth) {
        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth())
            throw new RuntimeException("Can't compare!");
        double tp = 0, fp = 0, tn = 0, fn = 0;
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
                if(resCol == 0 && gtCol == 0)
                    tp++;
                else if(resCol == 0 && gtCol == 255)
                    fp++;
                else if(resCol == 255 && gtCol == 255)
                    tn++;
                else if(resCol == 255 && gtCol == 0)
                    fn++;
            }
        }
        double accuracy = (tp + tn) / (tp + tn + fn + fp);
        return accuracy;
//        BigDecimal bd = new BigDecimal(accuracy);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    public double SENSITIVITY(BufferedImage result, BufferedImage groundTruth) {
        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth())
            throw new RuntimeException("Can't compare!");
        double tp = 0, fp = 0, tn = 0, fn = 0;
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
                if(resCol == 0 && gtCol == 0)
                    tp++;
                else if(resCol == 0 && gtCol == 255)
                    fp++;
                else if(resCol == 255 && gtCol == 255)
                    tn++;
                else if(resCol == 255 && gtCol == 0)
                    fn++;
            }
        }
        double sensitivity = tp / (tp + fn);
        return sensitivity;
//        BigDecimal bd = new BigDecimal(sensitivity);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    public double SPECIFICITY(BufferedImage result, BufferedImage groundTruth) {
        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth())
            throw new RuntimeException("Can't compare!");
        double tp = 0, fp = 0, tn = 0, fn = 0;
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
                if(resCol == 0 && gtCol == 0)
                    tp++;
                else if(resCol == 0 && gtCol == 255)
                    fp++;
                else if(resCol == 255 && gtCol == 255)
                    tn++;
                else if(resCol == 255 && gtCol == 0)
                    fn++;
            }
        }
        double specificity  = tn / (tn + fp);
        return specificity;
//        BigDecimal bd = new BigDecimal(specificity);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    public double EER(BufferedImage result, BufferedImage groundTruth) {
        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth())
            throw new RuntimeException("Can't compare!");
        double tp = 0, fp = 0, tn = 0, fn = 0;
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
                if(resCol == 0 && gtCol == 0)
                    tp++;
                else if(resCol == 0 && gtCol == 255)
                    fp++;
                else if(resCol == 255 && gtCol == 255)
                    tn++;
                else if(resCol == 255 && gtCol == 0)
                    fn++;
            }
        }
        double eer = (fn / (tp + fn)) + (fp / (tp + fn));
        return eer;
//        BigDecimal bd = new BigDecimal(eer);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    public double FMEASURE(BufferedImage result, BufferedImage groundTruth) {
        if(result.getHeight() != groundTruth.getHeight() || result.getWidth() != groundTruth.getWidth())
            throw new RuntimeException("Can't compare!");
        double tp = 0, fp = 0, tn = 0, fn = 0;
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
                if(resCol == 0 && gtCol == 0)
                    tp++;
                else if(resCol == 0 && gtCol == 255)
                    fp++;
                else if(resCol == 255 && gtCol == 255)
                    tn++;
                else if(resCol == 255 && gtCol == 0)
                    fn++;
            }
        }
        double recall = tp / (tp + fn);
        double precision = tp / (tp + fp);
        double f = (2.0 * recall * precision) / (recall + precision);
        return f;
//        BigDecimal bd = new BigDecimal(f);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
    public double PSNR(BufferedImage result, BufferedImage groundTruth) {
        double mse;
        double mn = result.getHeight() * result.getWidth();
        int[][] resultMatrix = new int[result.getWidth()][result.getHeight()];
        int[][] groundTruthMatrix = new int[groundTruth.getWidth()][groundTruth.getHeight()];
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                int resCol = new Color(result.getRGB(i, j)).getRed();
                int gtCol = new Color(groundTruth.getRGB(i, j)).getRed();
                if(resCol == 255)
                    resultMatrix[i][j] = 0;
                else resultMatrix[i][j] = 1;
                if(gtCol == 255)
                    groundTruthMatrix[i][j] = 0;
                else groundTruthMatrix[i][j] = 1;
            }
        }
        double sum = 0;
        for(int i = 0; i < result.getWidth(); i++) {
            for(int j = 0; j < result.getHeight(); j++) {
                sum += Math.pow(groundTruthMatrix[i][j] - resultMatrix[i][j], 2);
            }
        }
        mse = sum / mn;
        int c = 1;
        double toLog = (double)(c * c) / mse;
        double psnr = 10.0 * (Math.log(toLog) / Math.log(10));
        return psnr;
//        BigDecimal bd = new BigDecimal(psnr);
//        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }
    
}
