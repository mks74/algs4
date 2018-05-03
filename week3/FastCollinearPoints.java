import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    private int count;
    // private LineSegment[] segments;
    
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        count = 0;
        if (points == null) throw new java.lang.IllegalArgumentException();
        if (points[0] == null) throw new java.lang.IllegalArgumentException();
        for (int d = 0; d < points.length; d++) {
            for (int e = d + 1; e < points.length; e++) {
                if (d != e && points[d] == null) throw new java.lang.IllegalArgumentException();
                if (d != e && points[e] == null) throw new java.lang.IllegalArgumentException();
                if (d != e && points[d].equals(points[e])) throw new java.lang.IllegalArgumentException();
            }
        }
        Point[] copy = points;
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(copy, points[i].slopeOrder());
            // StdOut.println(Arrays.toString(copy));
            for (int j = 0; j < copy.length; j++) {
                if (points[i].slopeTo(copy[j + 1]) == points[i].slopeTo(copy[j])) {
                    count++;
                    
                }
            }
        }      
    }
    public int numberOfSegments() {       // the number of line segments
        return count;
    }
    public LineSegment[] segments() {               // the line segments
        LineSegment[] segments = new LineSegment[count];
        return segments;
    }
    public static void main(String[] args) {

    // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

    // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

    // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            // StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}