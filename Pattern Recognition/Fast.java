import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class Fast {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] slopeSortPoints = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            slopeSortPoints[i] = new Point(x, y);
            slopeSortPoints[i].draw();
        }
        
        Set<Line> lines = new HashSet<Line>();
        for (int i = 0; i < N; i++) {
            Arrays.sort(slopeSortPoints, i+1, N, slopeSortPoints[i].SLOPE_ORDER);
            double lastSlope = Double.NEGATIVE_INFINITY;
            int count = 1;
            int last = -1;
            for (int j = i+1; j < N; j++) {
                double curSlope = slopeSortPoints[i].slopeTo(slopeSortPoints[j]);
                if (curSlope == lastSlope && j == N - 1 && count >= 3) {
                    last = j;
                    count++;
                } else if (curSlope > lastSlope && count >= 4) {
                    last = j - 1;
                } else if (curSlope == lastSlope){
                    last = -1;
                    count++;
                } else {
                    last = -1;
                    count = 2;
                }
                
                if (last > 0) {
                    Point[] pointsStack = new Point[count];
                    int index = 0;
                    pointsStack[index++] = slopeSortPoints[i];
                    for (int k = last - count + 2; k <= last; k++) {
                        pointsStack[index++] = slopeSortPoints[k];
                    }
                    index--;
                    Arrays.sort(pointsStack);
                    double slope = pointsStack[0].slopeTo(pointsStack[index]);
                    double intercept = pointsStack[0].y - pointsStack[0].x*slope;
                    Line curLine = new Line(slope, intercept);
                    if (lines.add(curLine)) {
                        pointsStack[0].drawTo(pointsStack[index]);
                        int c = 0;
                        while (c < index) {
                            StdOut.print(pointsStack[c++] + " -> ");
                        }
                        StdOut.println(pointsStack[c]);
                    }
                    count = 2; // for curSlope > lastSlope
                }
                lastSlope = curSlope;
            }
        }
        
        // display to screen all at once
        StdDraw.show(0);
    }
    
    private static class Line {
        private double slope;
        private double intercept;
        private Line (double slope, double intercept) {
            this.slope = slope;
            this.intercept = intercept;
        }
        public boolean equals (Object that) {
            if (that instanceof Line) {
                Line t = (Line) that;
                return this.slope == t.slope && this.intercept == t.intercept;
            }
            return false;
        }
        public int hashCode() { 
            return 1;
        }
    }
}