import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int count;
    // private LineSegment[] segments;
    private ArrayList<LineSegment> seglist;
    
    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (points == null) throw new java.lang.IllegalArgumentException();
        if (points[0] == null) throw new java.lang.IllegalArgumentException();
        seglist = new ArrayList<LineSegment>();
        for (int d = 0; d < points.length; d++) {
            for (int e = d + 1; e < points.length; e++) {
                if (d != e && points[d] == null) throw new java.lang.IllegalArgumentException();
                if (d != e && points[e] == null) throw new java.lang.IllegalArgumentException();
                if (d != e && points[d].equals(points[e])) throw new java.lang.IllegalArgumentException();
            }
        }
        Point[] copy = points.clone();
        Arrays.sort(copy);
        // StdOut.println(Arrays.toString(points));
        for (int i = 0; i < copy.length; i++) {
            for (int j = i + 1; j < copy.length; j++) {
                for (int k = j + 1; k < copy.length; k++) {
                    if (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[k])) { // i->j == i->k, we have a candidate
                        // StdOut.println(i + " " + j + " " + k + " -- points[i].slopeTo(points[j]) = " + points[i].slopeTo(points[j]) + "points[i].slopeTo(points[k]) = " + points[i].slopeTo(points[k]) );
                        for (int m = k + 1; m < copy.length; m++) {
                            if (copy[i].slopeTo(copy[k]) == copy[i].slopeTo(copy[m])) { // we have a winner, or a duplicate
                                // for (int a = 0; a < count; a++) {
                                    // if (seglist.get(a)
                                seglist.add(new LineSegment(copy[i], copy[m]));
                                count++;
                                // StdOut.println(i + " " + j + " " + k + " " + l +"++");
                            }
                        }
                    }
                }
            }
        }
        
    }
    public           int numberOfSegments() {       // the number of line segments
        return count;
    }
    public LineSegment[] segments() {               // the line segments
        LineSegment[] segments = new LineSegment[seglist.size()];
        // LineSegment buffer;
        // int j = 0;
        // seglist.sort(null);
        for (int i = 0; i < segments.length; i++) {
            /* buffer = seglist.get(j);
            while (seglist.get(j) == buffer) {
                j++;
                StdOut.println(j);
            } */
            segments[i] = seglist.get(i);
            // StdOut.println(segments[i]);
        }
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
    // FastCollinearPoints collinear = new FastCollinearPoints(points);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            // StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}