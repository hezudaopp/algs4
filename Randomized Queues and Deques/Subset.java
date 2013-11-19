public class Subset {
    public static void main(String[] args) {
        if (args.length != 1) 
            throw new IllegalArgumentException();
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            queue.enqueue(input);
            if ((StdRandom.uniform(2) % 2) == 0) {
                deque.addFirst(input);
            } else {
                deque.addLast(input);
            }
        }
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
        for (int i = 0; i < k; i++) {
            if ((StdRandom.uniform(2) % 2) == 0) {
                StdOut.println(deque.removeFirst());
            } else {
                StdOut.println(deque.removeLast());
            }
        }
    }
}