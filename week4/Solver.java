import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class Solver {
    private boolean solvable = false;
    private int moves = 0;
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new java.lang.IllegalArgumentException();
        
        MinPQ<Node> pqI = new MinPQ<Node>(new orderByPrio());
        MinPQ<Node> pqT = new MinPQ<Node>(new orderByPrio());
        
        Board twin = initial.twin();
        
        Node inode = new Node();
        inode.board = initial;
        inode.prev = null;
        inode.priority = initial.manhattan();
        
        pqI.insert(inode);
        
        Node tnode = new Node();
        tnode.board = twin;
        tnode.prev = null;
        tnode.priority = twin.manhattan();
        
        pqT.insert(tnode);
        
        while (true) {
        Node current = pqI.delMin();
        if (!current.board.isGoal()) {
            for (Board neighbor : current.board.neighbors()) {
                if (!neighbor.equals(current.prev)) {
                    Node newborn = new Node();
                    newborn.board = neighbor;
                    newborn.prev = current;
                    newborn.moves = moves;
                    newborn.priority = (neighbor.manhattan() + moves);
                }
            }
        }
        }
        
    }
    private class Node {
        Board board;
        Node prev;
        int priority;
        int manhattan;
        int moves;
    }
    private class orderByPrio implements Comparator<Node> {
        public int compare(Node a, Node b) {
          int pri = a.priority - b.priority;
          if (pri == 0) return (a.manhattan - b.manhattan);
          return pri;
        }
    }
    public boolean isSolvable() {           // is the initial board solvable?
        return solvable;
    }
    
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (!isSolvable()) return -1;
        return moves;
    }
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        return null;
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
