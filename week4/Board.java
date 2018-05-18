import java.util.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Board {
    private int n;
    private int m;
    private int[][] tiles;
    // private int[][] goal;
    
    public Board(int[][] blocks) {          // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
        n =  blocks.length;
        tiles = new int[n][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
               if (tiles[i][j] != 0) { //
                   int targetX = (tiles[i][j] - 1) / n; 
                   int targetY = (tiles[i][j] - 1) % n; 
                   int dx = i - targetX; 
                   int dy = j - targetY; 
                   m += Math.abs(dx) + Math.abs(dy);
               }
            } 
        }
    }
    public int dimension() {                // board dimension n
        return n;
    }
    public int hamming() {                  // number of blocks out of place
        int h = 0;
        int k = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (k == (n * n)) k = 0;
                if ((tiles[i][j] != 0) && (tiles[i][j] != k)) h++;
                k++;
                // StdOut.println("tile: " + tiles[i][j] + "k: " + k + "h: " + h);
            }
        }
        return h;
    }
    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        return m;
    }
    public boolean isGoal() {               // is this board the goal board?
        return (this.hamming() == 0);
    }
    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        int[][] blerg = new int[n][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blerg[i][j] = tiles[i][j];
            }
        }
        int b;
        if (blerg[0][0] != 0 && blerg[0][1] != 0) {
            b = blerg[0][0];
            blerg[0][0] = blerg[0][1];
            blerg[0][1] = b;
        } else {
            b = blerg[1][0];
            blerg[1][0] = blerg[1][1];
            blerg[1][1] = b;
        }

        Board twin = new Board(blerg);
        return twin;
    }
    public boolean equals(Object y) {       // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        
        if (that.tiles.length != n) return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }
    public Iterable<Board> neighbors() {    // all neighboring boards
        Stack<Board> neighbors = new Stack<Board>();
        int[][] temp = new int[n][n];
        
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = tiles[i][j];
            }
        }
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (temp[i][j] == 0) {
                    if (i > 0) {
                        temp[i][j] = temp[i - 1][j];
                        temp[i - 1][j] = 0;
                        Board neighborOne = new Board(temp);
                        neighbors.push(neighborOne);
                        temp[i - 1][j] = temp[i][j];
                        temp[i][j] = 0;
                    }
                    if (i < n - 1) {
                        temp[i][j] = temp[i + 1][j];
                        temp[i + 1][j] = 0;
                        Board neighborTwo = new Board(temp);
                        neighbors.push(neighborTwo);
                        temp[i + 1][j] = temp[i][j];
                        temp[i][j] = 0;
                    }
                    if (j > 0) {
                        temp[i][j] = temp[i][j - 1];
                        temp[i][j - 1] = 0;
                        Board neighborThree = new Board(temp);
                        neighbors.push(neighborThree);
                        temp[i][j - 1] = temp[i][j];
                        temp[i][j] = 0;
                    }
                    if (j < n - 1) {
                        temp[i][j] = temp[i][j + 1];
                        temp[i][j + 1] = 0;
                        Board neighborFour = new Board(temp);
                        neighbors.push(neighborFour);
                        temp[i][j + 1] = temp[i][j];
                        temp[i][j] = 0;
                    }
                }
            }
        }
        return neighbors;
    }
    public String toString() {              // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            int[][] temp = new int[n][n];
        
            for(int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    temp[i][j] = tiles[i][j];
                }
            }
            
            // solve the slider puzzle
            Board initial = new Board(tiles);
            Stack<Board> neighbors;
            //Solver solver = new Solver(initial);
            StdOut.println(filename + ": " + initial.toString());
            StdOut.println(filename + ": dimension: " + initial.dimension());
            StdOut.println(filename + ": hamming: " + initial.hamming());
            StdOut.println(filename + ": manhattan: " + initial.manhattan());
            StdOut.println(filename + ": twin: " + initial.twin().toString());
            StdOut.println(filename + ": isgoal: " + initial.isGoal());
            StdOut.println(filename + ": equals(temp): " + initial.equals(new Board(temp)));
            StdOut.println(filename + ": neighbors: ");
            // neighbors = (Stack<Board>) initial.neighbors();
            for (Board neighbor : initial.neighbors()) {
                System.out.println(neighbor.toString());
            }

            // while (!neighbors.empty()) {
               // StdOut.println(neighbors.pop().toString());
            // }
        }
    }
}