import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int xCoord;
    private final int yCoord;

    public Point(int x, int y)  // constructs the point (x, y)
    {
        xCoord = x;
        yCoord = y;
    }

    public void draw()  // draws this point
    {
        StdDraw.point(this.xCoord, this.yCoord);
    }

    public void drawTo(Point that)  // draws the line segment from this point to that point
    {
        // LineSegment ls = new LineSegment(this, that);
        StdDraw.line(this.xCoord, this.yCoord, that.xCoord, that.yCoord);
    }

    public String toString()    // string representation
    {
        return "(" + String.valueOf(this.xCoord) + ", " + String.valueOf(this.yCoord) + ")";
    }

    public int compareTo(
            Point that)    // compare two points by y-coordinates, breaking ties by x-coordinates
    {
        if (this.yCoord < that.yCoord) {
            return -1;  // if y axis is higher than this
        }
        else if (this.yCoord > that.yCoord) {
            return 1;   // if y axis is lower than this
        }
        else {
            if (this.xCoord > that.xCoord) return 1;
            else if (this.xCoord < that.xCoord) return -1;
            else return 0;

        }
    }

    public double slopeTo(Point that)   // the slope between this point and that point
    {
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        else if (this.yCoord == that.yCoord) return 0.00;
        else if (this.xCoord == that.xCoord) return Double.POSITIVE_INFINITY;
        return ((double) this.yCoord - (double) that.yCoord) / ((double) this.xCoord
                - (double) that.xCoord);
    }

    public Comparator<Point> slopeOrder()   // compare two points by slopes they make with this point
    {
        Point invokingPt = this;
        Comparator<Point> com = new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                double o1Slope = invokingPt.slopeTo(o1);
                double o2Slope = invokingPt.slopeTo(o2);
                if (o1Slope > o2Slope) return 1;
                else if (o1Slope < o2Slope) return -1;
                else return 0;
            }
        };
        return com;
    }

}
