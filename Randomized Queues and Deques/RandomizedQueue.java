import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int count;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        count = 0;
        items = (Item[]) new Object[2];
    }
    
    private void resize(int size) {
        Item[] oldItems = items;
        items = (Item[]) new Object[size];
        for (int i = 0; i < count; i++) {
            items[i] = oldItems[i];
        }
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return count == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return count;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (count >= items.length) {
            resize(items.length << 1);
        }
        items[count++] = item;
    }
    
    // delete and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        if (count > 0 && count <= (items.length >> 2)) {
            resize(items.length >> 1);
        }
        int randomIndex = StdRandom.uniform(count--);
        Item tmp = items[randomIndex];
        items[randomIndex] = items[count];
        items[count] = tmp;
        return items[count];
    }
    
    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return items[StdRandom.uniform(count)];
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int num = 0;
        
        public RandomizedQueueIterator() {
            StdRandom.setSeed(System.currentTimeMillis());
        }
        
        public boolean hasNext() {
            return num <= size();
        }
        
        public Item next() {
            if (num == size())
                throw new java.util.NoSuchElementException();
            return items[StdRandom.uniform(num++)];
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}   