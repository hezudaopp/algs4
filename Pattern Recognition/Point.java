import java.util.Comparator;

public class Point implements Comparable<Point> {
    public int x;
    public int y;
    // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER = new PointSlopeComparator();   
    
    // construct the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draw this point
    public void draw() {
        StdDraw.point(x, y);
    }
    
    // draw the line segment from this point to that point
    public   void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    // string representation
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    // is this point lexicographically smaller than that point?
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else {
            if (this.x < that.x) return -1;
            else if (this.x > that.x) return 1;
            else return 0;
        }
    }
    
    // the slope between this point and that point
    public double slopeTo(Point that) {
        return ((this.y - that.y) * 1.0) / (1.0 * (this.x - that.x));
    }
    
    private class PointSlopeComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double p1Slope, p2Slope;
            
            if (compareTo(p1) == 0)
                p1Slope = Double.NEGATIVE_INFINITY;
            else if (x == p1.x) 
                p1Slope = Double.POSITIVE_INFINITY;
            else
                p1Slope = slopeTo(p1);
            
            if (compareTo(p2) == 0)
                p2Slope = Double.NEGATIVE_INFINITY;
            else if (x == p2.x) 
                p2Slope = Double.POSITIVE_INFINITY;
            else
                p2Slope = slopeTo(p2);
            
            if (p1Slope < p2Slope)
                return -1;
            else if (p1Slope > p2Slope)
                return 1;
            else
                return 0;
        }
    }
}