/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] thresholds;
    private Percolation p;

    private int T;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int N, int T) {
        this.T = T;
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        thresholds = new double[T];

        for (int i = 0; i < T; i++) {
            p = new Percolation(N);
            int count = 0;
            while (!p.percolates()) {
                int x = StdRandom.uniform(N) + 1;
                int y = StdRandom.uniform(N) + 1;
                if (!p.isOpen(x, y)) {
                    p.open(x, y);
                    count++;
                }
            }
            thresholds[i] = Integer.valueOf(count).doubleValue() / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);

    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);

    }

    public static void main(String[] args) {
        PercolationStats p;
        p = new PercolationStats(100, 10);
        System.out.println(p.mean());

        System.out.println("good boy");
    }
}
