public class Fast {
	private static class Line {
		private double slope;
		private Point point;
		public Line(double s, Point p) {
			slope = s;
			point = p;
		}

		@Override
		public boolean equals(Object t) {
			if (t instanceof Line) {
				Line that = (Line) t;
				return this.slope == that.slope && this.point.compareTo(that.point) == 0;
			}
			return false;
		}

		@Override
		public int hashCode() {
			StdOut.println(2);
			return point.hashCode();
		}
	}	

	public static void main(String[] args) {
		if (args.length != 1) return;
		String filename = args[0];
		In inStream = new In(filename);
		int N = inStream.readInt();
		if (N <= 1) return;
		Point[] points = new Point[N];
		StdDraw.setYscale(0, 32768);
		StdDraw.setXscale(0, 32768);
		for (int i = 0; i < N; i++) {
			points[i] = new Point(inStream.readInt(), inStream.readInt());
			points[i].draw();
		}
		java.util.Arrays.sort(points);
		Point[] slopeSortedPoints = new Point[N];
		java.util.List<Line> existLines = new java.util.ArrayList<Line>(); 
		for (int l = 0; l < N-3; l++) {
			Point basePoint = points[l];
			for (int i = l+1; i < N; i++) {
				slopeSortedPoints[i] = points[i];
			}
			// java.util.Arrays.sort(points, l+1, N-1, basePoint.SLOPE_ORDER);
			java.util.Arrays.sort(slopeSortedPoints, l+1, N, basePoint.SLOPE_ORDER);
			int i = l+1;
			while (i < N) {
				double curSlope = basePoint.slopeTo(slopeSortedPoints[i]);
				int j = i;	// l+1
				i++; // l+2
				while (i < N && basePoint.slopeTo(slopeSortedPoints[i]) == curSlope) {
					i++;
				}
				// if i >= l+3
				if (i-j >= 3) {
					Line line = new Line(curSlope, slopeSortedPoints[i-1]);
					if (!existLines.contains(line)) {
						existLines.add(line);
						StdOut.print(basePoint);
						for (int k = j; k < i; k++) {
							StdOut.print(" -> " + slopeSortedPoints[k]);
						}
						StdOut.println();
						basePoint.drawTo(slopeSortedPoints[i-1]);
					}
				}
			}
		}
	}
}