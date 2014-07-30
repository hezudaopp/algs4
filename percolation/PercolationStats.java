public class PercolationStats {
    private double[] stats;

    public PercolationStats(int N, int T) {    // perform T independent computational experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.stats = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            int count = 0;
            do {
                int p = StdRandom.uniform(1, N+1);
                int q = StdRandom.uniform(1, N+1);
                if (!percolation.isOpen(p, q)) {
                    percolation.open(p, q);
                    count++;
                } else {
                    continue;
                }
            } while(!percolation.percolates());
            stats[i] = count*1.0/(N*N);
        }
    }

    public double mean() {    // sample mean of percolation threshold
        double sum = 0.0;
        for (int i = 0; i < stats.length; i++) {
            sum += stats[i];
        }
        return sum/stats.length;
    }

    public double stddev() { // sample standard deviation of percolation threshold
        double mean = mean();
        double sum = 0.0;
        for (int i = 0; i < stats.length; i++) {
            sum += (stats[i]-mean)*(stats[i]-mean);
        }
        return Math.sqrt(sum/(stats.length-1));
    }

    public double confidenceLo() {    // returns lower bound of the 95% confidence interval
        double mean = mean();
        double stddev = stddev();
        return mean - (1.96*stddev/(Math.sqrt(stats.length)));
    }

    public double confidenceHi() {    // returns upper bound of the 95% confidence interval
        double mean = mean();
        double stddev = stddev();
        return mean + (1.96*stddev/(Math.sqrt(stats.length)));
    }

    public static void main(String[] args) {    // test client, described below
        if (args.length != 2) return;
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        // Stopwatch stopwatch = new Stopwatch();
        PercolationStats percolationStats = new PercolationStats(N, T);
        // StdOut.println("CPU elapsedTime    = " + stopwatch.elapsedTime());
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}