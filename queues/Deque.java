public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int count;

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

    public Deque() { // construct an empty deque
        first = new Node(null);
        last = new Node(null);
        first.next = last;
        last.pre = first;
        count = 0;
    }

    public boolean isEmpty() { // is the deque empty?
        return first.next == last;
    }

    public int size() { // return the number of items on the deque
        return count;
    }

    public void addFirst(Item item) { // insert the item at the front
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = new Node(item);
        node.next = first.next;
        first.next.pre = node;
        first.next = node;
        node.pre = first;
        count++;
    }

    public void addLast(Item item) { // insert the item at the end
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = new Node(item);
        node.pre = last.pre;
        last.pre.next = node;
        last.pre = node;
        node.next = last;
        count++;
    }

    public Item removeFirst() { // delete and return the item at the front
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node current = first.next;    // move current to 1st item
        first.next = current.next;    // make head node's next point to new 1st item
        current.next.pre = first;    // 
        count--;
        return current.item;
    }

    public Item removeLast() { // delete and return the item at the end
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node current = last.pre;
        last.pre = current.pre;
        current.pre.next = last;
        count--;
        return current.item;
    }

    public java.util.Iterator<Item> iterator() { // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    private class DequeIterator implements java.util.Iterator<Item> {
        private Node curNext;

        public DequeIterator() {
            curNext = first;
        }

        public boolean hasNext() {
            return curNext.next != last;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Node tmp = curNext.next;
            curNext = curNext.next;
            return tmp.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) { // unit testing

    }
}