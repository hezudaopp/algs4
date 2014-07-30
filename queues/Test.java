public class Test {
	public static void main(String[] args) {
		final int N = 10;
		final int T = 5000;
		int[] count = new int[N];
		for (int i = 0; i < T; i++) {
			RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue();
			for (int j = 1; j <= N; j++) {
				randomizedQueue.enqueue(j);
			}
			int index = -1;
			int r = -1;
			do {
				r = randomizedQueue.dequeue();
				index++;
			} while (r != N-1);
			count[index]++;
		}
		for (int i = 0; i < N; i++) {
			StdOut.println(count[i]);
		}
	}
}