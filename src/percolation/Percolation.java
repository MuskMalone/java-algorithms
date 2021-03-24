import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private static final int VIRTUALBOTTOMTILE = 1;
    private static final int VIRTUALTOPTILE = 0;
    private int openSites; // assumes that grid is instantiated with all sites closed
    private final int dim;
    private final int[][] grid;
    private boolean[][] vacancy;
    private final WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n <= 0");

        grid = new int[n + 1][n + 1];
        vacancy = new boolean[n + 1][n + 1];
        uf = new WeightedQuickUnionUF((n * n) + 2);
        dim = n;
        openSites = 0;

        int id = 2;
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[i].length; j++) {
                vacancy[i][j] = false;
                grid[i][j] = id;
                if (id <= (n * n))
                    id++;
                else break;
            }
        }
    }

    private void connectAdjacentTiles(int row, int col) {
        if (isTileValid(row + 1, col)) uf.union(grid[row][col], grid[row + 1][col]);
        if (isTileValid(row - 1, col)) uf.union(grid[row][col], grid[row - 1][col]);
        if (isTileValid(row, col + 1)) uf.union(grid[row][col], grid[row][col + 1]);
        if (isTileValid(row, col - 1)) uf.union(grid[row][col], grid[row][col - 1]);
    }

    private boolean isTileValid(int row, int col) {
        if (row > dim || col > dim) return false;
        if (row <= 0 || col <= 0) return false;
        if (!vacancy[row][col]) return false;
        return true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > dim || col > dim)
            throw new IllegalArgumentException("row or col <= 0");
        if (!vacancy[row][col]) {
            vacancy[row][col] = true;
            this.connectAdjacentTiles(row, col);
            openSites++;
            if (row == dim) uf.union(grid[row][col], VIRTUALBOTTOMTILE);
            if (row == 1) uf.union(grid[row][col], VIRTUALTOPTILE);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > dim || col > dim)
            throw new IllegalArgumentException("row or col <= 0");
        if (!vacancy[row][col]) return false;
        return true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > dim || col > dim)
            throw new IllegalArgumentException("row or col <= 0");
        if (uf.find(grid[row][col]) == uf.find(VIRTUALTOPTILE)) return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(VIRTUALBOTTOMTILE) == uf.find(VIRTUALTOPTILE)) return true;
        return false;
    }

    public static void main(String[] args) {
        // do something
    }
}
