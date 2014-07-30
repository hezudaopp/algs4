public class Brute {
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
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				double slopeij = points[i].slopeTo(points[j]);
				for (int k = j+1; k < N; k++) {
					double slopeik = points[i].slopeTo(points[k]);
					if (slopeij != slopeik) continue;
					for (int l = k+1; l < N; l++) {
						double slopeil = points[i].slopeTo(points[l]);
						if (slopeij != slopeil) continue;
						else {
							StdOut.println(points[i] + " -> " + points[j] + " -> " + points[k] + " -> " + points[l]);
							points[i].drawTo(points[l]);
						}
					}
				}
			}
		}
	}
}