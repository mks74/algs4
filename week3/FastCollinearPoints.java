import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    private int count;
    // private LineSegment[] segments;
    private ArrayList<LineSegment> seglist;
    
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
        Point[] copy = points.clone();
        Point[] result = new Point[points.length];
        Double ref;
        seglist = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(copy, points[i].slopeOrder());
            // StdOut.println(Arrays.toString(copy));
            int k = 1;
            ref = Double.NEGATIVE_INFINITY;
            result[0] = copy[0];
            for (int j = 1; j < copy.length; j++) {
                // StdOut.println("j: " + j + " " + points[i].slopeTo(copy[j]) + " " + ref);
                if (points[i].slopeTo(copy[j]) == ref) {
                    result[k++] = copy[j];
                    // StdOut.println("same!\n" + k);
                }
                else {
                    // StdOut.println("new!\n" + k);
                    if (k >= 3) {
                        result[k] = points[i];
                        // StdOut.println("k >= 3, i: " + i + " pre sort result: " + Arrays.toString(result));
                        Arrays.sort(result, 0, k + 1);
                        // StdOut.println("k >= 3, i: " + i + " post sort result: " + Arrays.toString(result));
                        if (result[0] == points[i]) {
                            seglist.add(new LineSegment(result[0], result[k]));
                            count++;
                            // StdOut.println((k + 1)+ " point segment for i " + i + ": " + result[0] + "-> " + result[k] + "\n");
                        }
                    }
                    // StdOut.println("reset! " + k + "new ref:" + points[i].slopeTo(copy[j]));
                    ref = points[i].slopeTo(copy[j]);
                    k = 0;
                    result[k++] = copy[j];
                }
            }
        }
    }
    public int numberOfSegments() {       // the number of line segments
        return count;
    }
    public LineSegment[] segments() {               // the line segments
        LineSegment[] segments = new LineSegment[count];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = seglist.get(i);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            // StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}