import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int n;
    
    public RandomizedQueue() {                 // construct an empty randomized queue !! resizing array
        arr = (Item[]) new Object[2];
        n = 0;
    }
    public boolean isEmpty() {                // is the randomized queue empty?
        return n == 0;
    }
    public int size() {                       // return the number of items on the randomized queue
        return n;
    }
    private void scale(int capacity) {
        assert capacity >= n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }

    public void enqueue(Item item) {          // add the item
        if (n == arr.length) scale(2*arr.length);
        arr[n++] = item;
    }
    public Item dequeue() {                   // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int r = StdRandom.uniform(n);
        Item item = arr[r];
        arr[r] = arr[n-1];
        arr[n-1] = null;
        n--;
        if (n > 0 && n == arr.length/4) scale(arr.length/2);
        return item;
    }
    public Item sample() {                    // return a random item (but do not remove it)
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int r = StdRandom.uniform(n);
        Item item = arr[r];
        return item;
    }
    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] copy;
        private int i;
        
        public RandomizedQueueIterator() {
            copy = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                copy[j] = arr[j];
            }
            i = n-1;
            StdRandom.shuffle(copy);
        }
        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy[i--];
        }
    }
    public static void main(String[] args) {    // unit testing (optional)
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        for (int i = 0; i < 20; i++) {
            q.enqueue(i);
        }
        StdOut.printf("size: %d\n", q.size());
        Iterator<Integer> it = q.iterator();
        while (it.hasNext()) {
            StdOut.printf("%d\n", it.next());
        }
        StdOut.printf("size: %d\n", q.size());
        for (int i = 0; i < 10; i++) {
            q.enqueue(i);
            StdOut.printf("%d\n", q.dequeue());
        }
        StdOut.printf("size: %d\n", q.size());
        it = q.iterator();
        while (it.hasNext()) {
            StdOut.printf("%d\n", it.next());
        }
    }
}