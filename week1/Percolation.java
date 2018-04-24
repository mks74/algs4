// #nocomment
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int enn;
    private int total;
    private int numOpen;
    private WeightedQuickUnionUF grid;
    private boolean[] open;
    
    public Percolation(int n) {  // create n-by-n grid, with all sites blocked
        if (n < 1) throw new IllegalArgumentException("index " + n + " is =< 0");
        enn = n;
        total = (n*n)+2;
        numOpen = 0;
        grid = new WeightedQuickUnionUF(total);
        open = new boolean[total];
        for (int i = 1; i <= enn; i++) { 
            grid.union(0, i);
        }
        for (int i = (((enn - 1) * enn) + 1); i < (total - 1); i++) {
            grid.union(total - 1, i);
        }
    }
    private void validate(int p) {
        if (p < 1 || p > enn) throw new IllegalArgumentException("index " + p + " is not between 1 and " + (enn));
    }
    private int rowcol2i(int row, int col) {
        validate(row);
        validate(col);
        return (((row - 1) * enn) + col);
    }
    public void open(int row, int col)  {  // open site (row, col) if it is not open already
        int id = rowcol2i(row, col);  
        int up = id - enn;
        int down = id + enn;
        int left = id - 1;
        int right = id + 1;
        if (!open[id]) {
            if (up > 0) {
                if (open[up]) grid.union(id, up);
            }
            if (down < (total - 1)) {
                if (open[down]) grid.union(id, down);
            }
            if (left > 0 && (left % enn != 0)) {
                if (open[left]) grid.union(id, left);
            }
            if (right < (total -1) && (right % enn != 1)) {
                if (open[right]) grid.union(id, right);
            }
            open[id] = true;
            numOpen++;
        }
    }
    public boolean isOpen(int row, int col) { // is site (row, col) open?
        return open[rowcol2i(row, col)];
    }
    public boolean isFull(int row, int col) {  // is site (row, col) full?
        if (open[rowcol2i(row, col)]) {
            return grid.connected(0, rowcol2i(row, col));
        }
        return false;
    }
    public     int numberOfOpenSites() {      // number of open sites
        return numOpen;
    }
    public boolean percolates() {             // does the system percolate?
        if (enn == 1) return open[1];
        return grid.connected(0, total - 1);
    }
    public static void main(String[] args)  { // test client (optional)
    }
}

