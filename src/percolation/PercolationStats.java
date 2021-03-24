import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int tileCount;
    private final int epoch;
    private double[] results;
    private final double marginOfErr;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        tileCount = n;
        epoch = trials;
        results = new double[trials];
        marginOfErr = (1.96 / Math.sqrt(epoch));
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - marginOfErr;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + marginOfErr;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);
        int totalTiles = n * n;
        PercolationStats ps = new PercolationStats(n, trial);
        for (int i = 0; i < ps.epoch; i++) {
            Percolation p = new Percolation(ps.tileCount);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, ps.tileCount + 1),
                       StdRandom.uniform(1, ps.tileCount + 1));
            }
            int openSites = p.numberOfOpenSites();
            double openSitePercentage = ((double) openSites) / ((double) totalTiles);
            ps.results[i] = openSitePercentage;
        }
        StdOut.println("mean                            : " + ps.mean());
        StdOut.println("stddev                          : " + ps.stddev());
        StdOut.println(
                "95% confidence interval         : [" + ps.confidenceLo() + ", " + ps.confidenceHi()
                        + "]");
    }
}
