public class Percolation {
    private static final boolean BLOCKED = false;
    private static final boolean OPEN = true;

    private int n;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF fullWeightedQuickUnionUF;    // has top virt, but no bottom virt, used for "isFull"
    private boolean[] status;

    public Percolation(int N) {    // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        int size = N*N+2;
        this.n = N;
        weightedQuickUnionUF = new WeightedQuickUnionUF(size);
        fullWeightedQuickUnionUF = new WeightedQuickUnionUF(size-1);
        status = new boolean[size];
        status[0] = Percolation.OPEN;
        for (int i = 1; i < size-1; i++) {
            status[i] = Percolation.BLOCKED;
        }
        status[size-1] = Percolation.OPEN;
    }

    public void open(int i, int j) {    // open site (row i, column j) if it is not already
        checkBound(i, j);
        if (isOpen(i, j)) return;

        int k = convertNumberFromTwoDimentionalToOneDimentional(i, j);

        status[k] = Percolation.OPEN;

        int upSite = convertNumberFromTwoDimentionalToOneDimentional(i-1, j);
        if (i == 1) upSite = 0;
        if (status[upSite] != Percolation.BLOCKED) {
            weightedQuickUnionUF.union(k, upSite);
            fullWeightedQuickUnionUF.union(k, upSite);
        }
        
        if (j != 1) {
            int leftSite = convertNumberFromTwoDimentionalToOneDimentional(i, j-1);
            if (status[leftSite] != Percolation.BLOCKED) {
                weightedQuickUnionUF.union(k, leftSite);
                fullWeightedQuickUnionUF.union(k, leftSite);
            }
        }

        if (j != this.n) {
            int rightSite = convertNumberFromTwoDimentionalToOneDimentional(i, j+1);
            if (status[rightSite] != Percolation.BLOCKED) {
                weightedQuickUnionUF.union(k, rightSite);
                fullWeightedQuickUnionUF.union(k, rightSite);
            }
        }

        int downSite = convertNumberFromTwoDimentionalToOneDimentional(i+1, j);
        if (i == this.n) downSite = this.n*this.n+1;
        if (status[downSite] != Percolation.BLOCKED) {
            weightedQuickUnionUF.union(k, downSite);
            if (i != this.n) {
                fullWeightedQuickUnionUF.union(k, downSite);
            }
        }
    }

    public boolean isOpen(int i, int j) {    // is site (row i, column j) open?
        checkBound(i, j);
        int k = convertNumberFromTwoDimentionalToOneDimentional(i, j);
        return status[k] == Percolation.OPEN;
    }

    public boolean isFull(int i, int j) {    // is site (row i, column j) full?
        checkBound(i, j);
        int k = convertNumberFromTwoDimentionalToOneDimentional(i, j);
        return fullWeightedQuickUnionUF.connected(k, 0);
    }

    public boolean percolates() {    // does the system percolate?
        return weightedQuickUnionUF.connected(this.n*this.n+1, 0);
    }

    private int convertNumberFromTwoDimentionalToOneDimentional(int i, int j) {
        return (i-1)*this.n+j;
    }

    private void checkBound(int i, int j) {
        if (i < 1 || i > this.n || j < 1 || j > this.n) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }
}