import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.util.ArrayDeque;

public class Solver {
    private boolean solvable;
    private Node current;
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new java.lang.IllegalArgumentException();
        
        MinPQ<Node> pqI = new MinPQ<Node>(new OrderByPrio());
        MinPQ<Node> pqT = new MinPQ<Node>(new OrderByPrio());
        
        Board twin = initial.twin();
        
        Node inode = new Node();
        inode.board = initial;
        inode.prev = null;
        inode.priority = initial.manhattan();
        inode.moves = 0;
        // StdOut.println("inserting inital board\n" + initial.toString());
        pqI.insert(inode);
        
        Node tnode = new Node();
        tnode.board = twin;
        tnode.prev = null;
        tnode.priority = twin.manhattan();
        tnode.moves = 0;
        // StdOut.println("inserting twin\n" + twin.toString());
        pqT.insert(tnode);
        
        int moves = 0;
        Node newborn;
        while (true) {
            // moves++;
            // StdOut.println("moves: " + moves);
            current = pqI.delMin();
            // StdOut.println("dequeued pqI, prio:" + current.priority + "\n" + current.board.toString());
            if (!current.board.isGoal()) {
                for (Board neighbor : current.board.neighbors()) {
                    if ((current.prev != null) && (neighbor.equals(current.prev.board))) {
                        // StdOut.println("ignoring\n" + neighbor.toString());
                    } else {
                        newborn = new Node();
                        newborn.board = neighbor;
                        newborn.prev = current;
                        newborn.moves = current.moves + 1;
                        newborn.priority = (neighbor.manhattan() + newborn.moves);
                        // StdOut.println("inserting inital neighbor, manhattan: " + neighbor.manhattan() + "prio: " + newborn.priority + "\n" + neighbor.toString());
                        pqI.insert(newborn);
                    }
                }
            } else {
                solvable = true;
                // StdOut.println("Solved!" + current.board.toString());
                // StdOut.println("current.moves: " + current.moves);
                // StdOut.println("current.priority: " + current.priority);
                break;
            }
            current = pqT.delMin();
            // StdOut.println("dequeued pqT, got:\n" + current.board.toString());
            if (!current.board.isGoal()) {
                for (Board neighbor : current.board.neighbors()) {
                    if ((current.prev != null) && (neighbor.equals(current.prev.board))) {
                        // StdOut.println("ignoring\n" + neighbor.toString());
                    } else {
                        newborn = new Node();
                        newborn.board = neighbor;
                        newborn.prev = current;
                        newborn.moves = current.moves + 1;
                        newborn.priority = (neighbor.manhattan() + newborn.moves);
                        // StdOut.println("inserting twin neighbor, prio: " + newborn.priority + "\n" + neighbor.toString());
                        pqT.insert(newborn);
                    }
                }
            } else {
                solvable = false;
                // StdOut.println("Unsolvable!");
                break;
            }
        }
        
    }
    private class Node {
        Board board;
        Node prev;
        int priority;
        int moves;
    }
    private class OrderByPrio implements Comparator<Node> {
        public int compare(Node a, Node b) {
          int pri = a.priority - b.priority;
          if (pri == 0) return (a.board.manhattan() - b.board.manhattan());
          return pri;
        }
    }
    public boolean isSolvable() {           // is the initial board solvable?
        return solvable;
    }
    
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        if (!solvable) return -1;
        return current.moves;
    }
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (!solvable) return null;
        // ArrayList<Board> solution = new ArrayList<Board>(); 
        Node blerg = current;
        ArrayDeque<Board> solution = new ArrayDeque<Board>(); 
        
        // StdOut.println("solution: current.moves: " + current.moves);
        int j = blerg.moves + 1;
        for (int i = 0; i < j; i++) {
            // StdOut.println("i: " + i);
            solution.addFirst(blerg.board);
            blerg = blerg.prev;
        }
        return solution;
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
