/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    Point[] points;
    LineSegment[] lineSegments;
    int lineSegmentsIndex;

    public BruteCollinearPoints(Point[] points) {
        this.points = points;
        this.lineSegments = new LineSegment[1000000];
        this.lineSegmentsIndex = 0;
        generateSegments();
    }

    public int numberOfSegments() {
        return lineSegmentsIndex;
    }

    public LineSegment[] segments() {

        LineSegment[] ls = new LineSegment[lineSegmentsIndex];
        for (int i = 0; i < lineSegmentsIndex; i++) {
            ls[i] = lineSegments[i];
        }
        return ls;

    }

    private void generateSegments() {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p = points[i], q = points[j], r = points[k], s = points[l];
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            LineSegment ls = new LineSegment(p, s);
                            lineSegments[lineSegmentsIndex++] = ls;

                        }
                    }
                }
            }
        }
    }

    private static Point[] readPoints(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        return points;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();

        //Point[] points = BruteCollinearPoints.readPoints(args[0]);

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

        //print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

