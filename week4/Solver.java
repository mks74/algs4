import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        MinPQ pqI = new MinPQ();
        MinPQ pqT = new MinPQ();
        int moves;
        
        Board twin = initial.twin();
        
        Node inode = new Node();
        inode.board = initial;
        inode.prev = null;
        inode.priority = 1;
        
    }
    private class Node {
        Board board;
        Board prev;
        int priority;
        int manhattan;
        int hamming;
        int moves;
    }
    class prioByManhattan implements comparator<Node> {
    }
    
    class prioByHamming implements Comparator<Node> {
    }
    
    public boolean isSolvable() {           // is the initial board solvable?
    }
    
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (!isSolvable()) return -1;
        
    }
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
    }
    public static void main(String[] args) { // solve a slider puzzle (given below)
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
