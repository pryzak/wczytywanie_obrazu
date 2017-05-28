package image_processing;

public class SE_Point {
    
    private int point;
    private boolean origin = false;
    
    public SE_Point(int point) throws RuntimeException {
        if(point != 0 && point != 1)
            throw new RuntimeException("Wrong number!");
        this.point = point;
    }
    
    public SE_Point(int point, boolean origin) throws RuntimeException {
        if(point != 0 && point != 1)
            throw new RuntimeException("Wrong number!");
        this.point = point;
        this.origin = origin;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isOrigin() {
        return origin;
    }

    public void setOrigin(boolean origin) {
        this.origin = origin;
    }
    
}
