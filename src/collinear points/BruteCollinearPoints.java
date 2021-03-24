/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lsArray = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) throw new IllegalArgumentException("NULL ARGUMENT IN CONSTRUCTOR");
        Point[] p = checkInvalidPoints(points);
        int n = p.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point o = p[i];
                        if (o.slopeTo(p[j]) == o.slopeTo(p[k])
                                && o.slopeTo(p[l]) == o.slopeTo(p[k]))
                            lsArray.add(new LineSegment(o, p[l]));
                    }
                }
            }
        }
    }

    public int numberOfSegments()   // the number of line segments
    {
        return lsArray.size();
    }

    public LineSegment[] segments() // the line segments
    {
        LineSegment[] copy = new LineSegment[lsArray.size()];
        copy = lsArray.toArray(copy);
        return copy;
    }

    private Point[] checkInvalidPoints(Point[] p) {
        Point[] pCopy = new Point[p.length];
        System.arraycopy(p, 0, pCopy, 0, p.length);
        for (Point k : pCopy) {
            if (k == null) throw new IllegalArgumentException("NULL POINT");
        }
        Arrays.sort(pCopy);
        if (p.length > 0) {
            for (int i = 0; i < p.length; i++) {
                if (i > 0)
                    if (pCopy[i].compareTo(pCopy[i - 1]) == 0)
                        throw new IllegalArgumentException("DUPLICATE POINTS");

            }
        }
        return pCopy;
    }
}
