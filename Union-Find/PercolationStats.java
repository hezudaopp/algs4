public class PercolationStats {
    private double[] xt;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
       if (N <= 0 || T <= 0) {
          throw new java.lang.IllegalArgumentException();
       }
       xt = new double[T];
       int sitesCount = N*N;
       int[] randoms = new int[sitesCount];
       for (int i = 0; i < sitesCount; i++) {
           randoms[i] = i;
       }
       for (int i = 0; i < T; i++) {
           StdRandom.shuffle(randoms);
           Percolation percolation = new Percolation(N);
           for (int j = 0; j < sitesCount; j++) {
               int randomNumber = randoms[j];
               int row = randomNumber/N+1;
               int col = randomNumber % N+1;
               percolation.open(row, col);
               if (percolation.percolates())
                   break;
           }
           xt[i] = (percolation.getOpenCount()*1.0)/(sitesCount*1.0);
       }
   }
    
    // sample mean of percolation threshold
    public double mean() {                    
        return StdStats.mean(xt);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {                   
        return StdStats.stddev(xt);
    }
    
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {            
        return mean()-1.96*stddev()/(Math.sqrt(xt.length));
    }
    
    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {            
        return mean()+1.96*stddev()/(Math.sqrt(xt.length));
    }
    public static void main(String[] args) {  // test client, described below
        if (args.length == 2) {
            int N = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);

            Stopwatch stopwatch = new Stopwatch();
            PercolationStats percolationStats = new PercolationStats(N, T);
            StdOut.println(stopwatch.elapsedTime());
            StdOut.println("mean\t= " + percolationStats.mean());
            StdOut.println("stddev\t= " + percolationStats.stddev());
            StdOut.println("95% confidence interval\t= " 
                               + percolationStats.confidenceLo() + ", " 
                               + percolationStats.confidenceHi());
        }

        else {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
    }
}