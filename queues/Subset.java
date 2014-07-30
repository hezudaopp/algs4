public class Subset {
    public static void main(String[] args) {
        if (args.length != 1) return;
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue();
        int k = Integer.parseInt(args[0]);
        if (k <= 0) return;
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (randomizedQueue.size() >= k)
                randomizedQueue.dequeue();
            randomizedQueue.enqueue(str);
        }
        java.util.Iterator<String> it = randomizedQueue.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }
    }
}