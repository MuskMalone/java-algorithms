import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private ArrayList<LineSegment> lsArray = new ArrayList<LineSegment>();

    private Comparator<Point> currentComparator;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("ARGUMENT IS NULL");
        Point[] pointsCopy = checkInvalidPoints(points);
        Point current;

        for (int i = 0; i < points.length; i++) {
            current = points[i];
            currentComparator = current.slopeOrder();
            sort(pointsCopy);
            int subsegmentCount = 1;
            for (int j = 0; j < pointsCopy.length; j++) {
                if (j > 0) {
                    if (current.slopeTo(pointsCopy[j]) == current.slopeTo(pointsCopy[j - 1]))
                        subsegmentCount++;
                    else subsegmentCount = 1;
                }
                if (subsegmentCount == 3) {
                    if (current.compareTo(pointsCopy[j]) > 0) lsArray.add(
                            new LineSegment(pointsCopy[j - 2],
                                            current)); // if current point is highest point

                    subsegmentCount = 1;
                }
            }
        }


    }

    public int numberOfSegments() {
        return lsArray.size();
    }

    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[lsArray.size()];
        copy = lsArray.toArray(copy);
        return copy;
    }

    private void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    private void sort(Point[] a, Point[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private void sort(Point[] a) {
        Point[] aux = new Point[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private boolean less(Point a, Point b) {
        if (currentComparator.compare(a, b) < 0) return true;
        else if (currentComparator.compare(a, b) == 0)
            if (a.compareTo(b) < 0) return true;
        return false;
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
