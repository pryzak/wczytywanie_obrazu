package image_processing;

public class SE {
    
    private int[][] mask; //1, 0 lub -1 (piksel nieokreslony)
    private int xOrigin;
    private int yOrigin;
    
    public SE(int[][] mask, int xOrigin, int yOrigin) {
        this.mask = mask;
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
    }

    public int[][] getMask() {
        return mask;
    }

    public void setMask(int[][] mask) {
        this.mask = mask;
    }

    public int getxOrigin() {
        return xOrigin;
    }

    public void setxOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }
    
    public int getyOrigin() {
        return yOrigin;
    }

    public void setyOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }
}
