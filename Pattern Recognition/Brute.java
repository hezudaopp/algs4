public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = j+1; k < N; k++) {
                    if (points[i].SLOPE_ORDER.compare(points[j], points[k]) != 0)
                        continue;
                    for (int l = k+1; l <N; l++) {
                        if (points[i].SLOPE_ORDER.compare(points[j], points[l]) != 0)
                            continue;
                        else {
                            StdOut.println(points[i] + " -> " + points[j] + " -> " + points[k] + " -> " + points[l]);
                            Point minPoint = points[i], maxPoint = points[i];
                            int[] indexArr = new int[]{i, j, k, l};
                            for (int index = 1; index < 4; index++) {
                                if (minPoint.compareTo(points[indexArr[index]]) < 0) {
                                    minPoint = points[indexArr[index]];
                                }
                                if (maxPoint.compareTo(points[indexArr[index]]) > 0) {
                                    maxPoint = points[indexArr[index]];
                                }
                            }
                            minPoint.drawTo(maxPoint);
                        }
                    }
                }
            }
        }

        // display to screen all at once
        StdDraw.show(0);
    }
}