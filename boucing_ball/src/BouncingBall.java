
public class BouncingBall {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int N = 100;
		Ball[] balls = new Ball[N];
		for (int i = 0; i < N; i++)
			balls[i] = new Ball();
		while(true) {
			StdDraw.clear();
			StdDraw.line(0, 0, 0, 1) ;
			StdDraw.line(0, 0, 1, 0);
			StdDraw.line(0, 1, 1, 1);
			StdDraw.line(1, 0, 1, 1);
			for (int i = 0; i < N; i++) {
				balls[i].move(0.5);
				balls[i].draw();
			}
			StdDraw.show(50);
		}
	}

}
