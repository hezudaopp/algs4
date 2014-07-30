public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 2;
    private Item[] q;
    private int count;

    public RandomizedQueue() { // construct an empty randomized queue
        count = 0;
        q = (Item[]) new Object[INIT_CAPACITY];
    }
    public boolean isEmpty() { // is the queue empty?
        return count == 0;
    }

    public int size() { // return the number of items on the queue
        return count;
    }

    public void enqueue(Item item) { // add the item
        if (item == null) {
            throw new NullPointerException();
        }
        if (count >= q.length) {
            resize(q.length << 1);
        }
        q[count++] = item;
    }

    public Item dequeue() { // delete and return a random item
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int random = StdRandom.uniform(count);
        count--;
        Item tmp = q[random];
        if (random != count) {
            q[random] = q[count];
            q[count] = tmp;
        }
        if (count*4 < q.length && q.length > 1) { // size of q must be > 0
            resize(q.length >> 1);
        }
        return tmp;
    }
    public Item sample() { // return (but do not delete) a random item
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return q[StdRandom.uniform(count)];
    }

    public java.util.Iterator<Item> iterator() { // return an independent iterator over items in random order
        return new RandomizedIterator();
    }

    private void resize(int size) {
        Item[] tmp = q;
        q = (Item[]) new Object[size];
        for (int i = 0; i < count; i++) {
            q[i] = tmp[i];
        }
    }

    /**
     * The order of two or more iterators to the same randomized queue should be mutually independent.
     * Each iterator must maintain its own random order.
     */
    private class RandomizedIterator implements java.util.Iterator<Item> {
        private Item[] copy;
        private int lastIndex;

        public RandomizedIterator() { // construction in time linear in the number of items
            copy = (Item[]) new Object[count];
            for (int i = 0; i < count; i++) {
                copy[i] = q[i];
            }
            lastIndex = count-1;
        }

        public boolean hasNext() { // constant worst-case time
            return lastIndex >= 0;
        }

        public Item next() { // constant worst-case time
        	if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            int random = StdRandom.uniform(lastIndex+1);
            Item tmp = copy[random];
            copy[random] = copy[lastIndex];
            copy[lastIndex--] = tmp;
            return tmp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) { // unit testing

    }
}