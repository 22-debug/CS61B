package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int n;
    private int t;
    private double mean;
    private double deviation;
    private double[] threshold;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        n = N;
        t = T;
        threshold = new double[t];
        for (int i = 0; i < T; i++) {
            threshold[i] = simulation(pf.make(N));
        }
        mean = StdStats.mean(threshold);
        deviation = StdStats.stddev(threshold);
    }

    private double simulation(Percolation percolation) {
        int openedSites = 0;
        while (!percolation.percolates()) {
            randomlyOpen(percolation);
            openedSites++;
        }
        return (double) openedSites / (n * n);
    }

    private void randomlyOpen(Percolation percolation) {
        int row = StdRandom.uniform(n);
        int col = StdRandom.uniform(n);
        while (percolation.isOpen(row, col)) {
            row = StdRandom.uniform(n);
            col = StdRandom.uniform(n);
        }
        percolation.open(row, col);
    }

    public double mean() {
        // sample mean of percolation threshold
        return mean;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return deviation;
    }

    private static final double CONFIDENCE_95_ZSCORE = 1.96;

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return (mean - (CONFIDENCE_95_ZSCORE * deviation / Math.sqrt((double) t)));
    }

    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        return (mean + (CONFIDENCE_95_ZSCORE * deviation / Math.sqrt((double) t)));
    }
}
