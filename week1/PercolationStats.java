import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double CONFIDENCE_MAGIC = 1.96;
    private double[] results;

    public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
        if (n <= 0) throw new java.lang.IllegalArgumentException("size cannot be less than 1");
        if (trials <= 0) throw new java.lang.IllegalArgumentException("trials cannot be less than 1");

        results = new double[trials];       
        int row;
        int col;
        int opens = 0;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    opens++;
                }
            }
            results[i] = ((double) opens / (n * n));
            opens = 0;
        }
    }
    public double mean() {                         // sample mean of percolation threshold
        return StdStats.mean(results);
    }
    public double stddev() {                       // sample standard deviation of percolation threshold
        return StdStats.stddev(results);
    }
    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        double m = mean();
        double s = stddev();
        return (m - (CONFIDENCE_MAGIC * s) / Math.sqrt(results.length));
    }
    public double confidenceHi() {                 // high endpoint of 95% confidence interval
        double m = mean();
        double s = stddev();
        return (m + (CONFIDENCE_MAGIC * s) / Math.sqrt(results.length)); 
    }
    public static void main(String[] args) {       // test client (described below)
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean\t\t\t\t= %f%nstddev\t\t\t\t= %f%n", ps.mean(), ps.stddev());
        StdOut.printf("95%% confidence interval\t\t= [%f, %f]%n", ps.confidenceLo(), ps.confidenceHi());
    }
}