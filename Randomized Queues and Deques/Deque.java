import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int count;
    
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }
    
    // return the number of items on the deque
    public int size() {
        return count;
    }
    
    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node oldFirst = first;
        Node newFirst = new Node(item);
        newFirst.next = oldFirst;
        if (oldFirst != null) oldFirst.pre = newFirst;
        if (isEmpty()) last = newFirst;
        first = newFirst;
        count++;
    }
   
    // insert the item at the end
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node newLast = new Node(item);
        if (isEmpty()) {
            first = newLast;
        } else {
            last.next = newLast;
        }
        newLast.pre = last;
        last = newLast;
        count++;
    }
   
    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item ret = first.item;
        first = first.next;
        count--;
        if (isEmpty())
            last = null;
        return ret;
    }
   
    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        Item ret = last.item;
        last = last.pre;
        count--;
        if (isEmpty())
            first = null;
        return ret;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
       
    private class DequeIterator implements Iterator<Item> {
        private Node cur = first;
        
        public boolean hasNext() {
            return cur != null;
        }
       
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item ret = cur.item;
            cur = cur.next;
            return ret;
        }
    }
    
    private class Node {
        private Item item;
        private Node next;
        private Node pre;
        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.pre = null;
        }
    }
}