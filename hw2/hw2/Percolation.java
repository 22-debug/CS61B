package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    //including virtual top and bottom for checking percolation
    private WeightedQuickUnionUF unionUF;
    //only including virtual top for checking fullness
    private WeightedQuickUnionUF fullUF;
    private final int n;
    private int openNum;
    private boolean[] isOpen;
    private final int virTop;
    private final int virBottom;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if (N < 0) {
            throw new IllegalArgumentException();
        }
        unionUF = new WeightedQuickUnionUF(N * N + 2);
        fullUF = new WeightedQuickUnionUF(N * N + 1);
        //virtual top: n * n; virtual bottom: n * n + 1;
        isOpen = new boolean[N * N + 2];
        for (int i = 0; i < N * N + 2; i++) {
            isOpen[i] = false;
        }
        n = N;
        openNum = 0;
        virTop = n * n;
        virBottom = n * n + 1;
        //connect top row to virtual top site and bottom row to virtual bottom site
        for (int i = 0; i < n; i++) {
            unionUF.union(i, virTop);
            fullUF.union(i, virTop);
            unionUF.union(i + (n - 1) * n, virBottom);
        }
    }

    private int xyTo1D(int row, int col) {
        return row * n + col;
    }

    private void assertValid(int row, int col) {
        if (row < 0 || row > n || col < 0 || col > n) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        assertValid(row, col);
        int pos = xyTo1D(row, col);
        if (!isOpen[pos]) {
            isOpen[pos] = true;
            openNum += 1;
            connectOpenSite(row, col);
        }
    }

    private void connectOpenSite(int row, int col) {
        int pos = xyTo1D(row, col);

        // Try to connect to the neighboring open site above (row - 1, col)
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            int up = xyTo1D(row - 1, col);
            unionUF.union(pos, up);
            fullUF.union(pos, up);
        }

        // Try to connect to the neighboring open site below (row + 1, col)
        if (row + 1 < n && isOpen(row + 1, col)) {
            int down = xyTo1D(row + 1, col);
            unionUF.union(pos, down);
            fullUF.union(pos, down);
        }

        // Try to connect to the neighboring open site to the left (row, col - 1)
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            int left = xyTo1D(row, col - 1);
            unionUF.union(pos, left);
            fullUF.union(pos, left);
        }

        // Try to connect to the neighboring open site to the right (row, col + 1)
        if (col + 1 < n && isOpen(row, col + 1)) {
            int right = xyTo1D(row, col + 1);
            unionUF.union(pos, right);
            fullUF.union(pos, right);
        }
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        assertValid(row, col);
        return isOpen[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        assertValid(row, col);
        return isOpen(row, col) && fullUF.connected(virTop, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        // number of open sites
        return openNum;
    }

    public boolean percolates() {
        // does the system percolate?
        if (openNum == 0) { //when N = 1,virtual top and bottom is connected
            return false;
        }
        return unionUF.connected(virTop, virBottom);
    }

    public static void main(String[] args) {
        // use for unit testing (not required)
    }
}
