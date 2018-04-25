import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Element head;
    private Element tail;
    
    private class Element {
        private Item item;
        private Element next;
        private Element prior;
    }
    public Deque() {                          // construct an empty deque !! linked list
        head = null;
        tail = null;
        size = 0;
    }
       
    public boolean isEmpty() {                // is the deque empty?
        return size == 0;
    }
    
    public int size() {                       // return the number of items on the deque
        return size;
    }
    
    public void addFirst(Item item) {         // add the item to the front
        if (item == null) throw new java.lang.IllegalArgumentException("item cannot be null");
        Element prevhead = head;
        head = new Element();
        head.item = item;
        head.next = prevhead;
        if (prevhead != null) prevhead.prior = head;
        if (tail == null) tail = head;
        size++;
    }
    public void addLast(Item item) {          // add the item to the end
        if (item == null) throw new java.lang.IllegalArgumentException("item cannot be null");
        Element prevtail = tail;
        tail = new Element();
        tail.item = item;
        tail.prior = prevtail;
        if (prevtail != null) prevtail.next = tail;
        if (head == null) head = tail;
        size++;
    }
    public Item removeFirst() {               // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException("deque empty");
        Item item = head.item;
        if (size == 1) {
            tail = null;
            head = null;
        }
        else {
        head = head.next;
        head.prior = null;
        }
        size--;
        return item;
    }
    public Item removeLast() {                // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException("deque empty");
        Item item = tail.item;
        if (size == 1) {
            tail = null;
            head = null;
        }
        else {
            tail = tail.prior;
            tail.next.prior = null;
            tail.next = null;
        }
        size--;
        return item;
    }
    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Element current = head;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException();  }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    public static void main(String[] args)  { // unit testing (optional)
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < 5; i++) {
            d.addFirst(i);
        }
        StdOut.printf("size: %d\n", d.size());
        for (int i = 0; i < 5; i++) {
            // d.addFirst(i);
            StdOut.printf("%d\n", d.removeLast());
        }
        for (int i = 0; i < 5; i++) {
            d.addLast(i);
        }
        StdOut.printf("size: %d\n", d.size());
        for (int i = 0; i < 5; i++) {
            // d.addFirst(i);
            StdOut.printf("%d\n", d.removeFirst());
        }
    }
}