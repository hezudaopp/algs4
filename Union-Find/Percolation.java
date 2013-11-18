
public class Percolation {
    private boolean[][] sites;
    private int size;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int unionSize;
    private int openCount;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        size = N;
        unionSize = N*N;
        sites = new boolean[N][];
        for (int i = 0; i < N; i++) {
            sites[i] = new boolean[N];
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sites[i][j] = false;
            }
        }
        // +2: one for virtual top point, the other for virtual bottom point
        weightedQuickUnionUF = new WeightedQuickUnionUF(unionSize+2);
    }
    
    public int getOpenCount() {
        return openCount;
    }
    
    public void open(int i, int j) {
        // open site (row i, column j) if it is not already
        if (i < 1 || i > size || j < 1 || j > size)
            throw new java.lang.IndexOutOfBoundsException();
        sites[i-1][j-1] = true;
        // left
        if (j > 1) {
            int left = j-1;
            if (isOpen(i, left)) {
                weightedQuickUnionUF.union((i-1)*size+left-1, (i-1)*size+j-1);
            }
        }
        
        // right
        if (j < size) {
            int right = j+1;
            if (isOpen(i, right)) {
                weightedQuickUnionUF.union((i-1)*size+j-1, (i-1)*size+right-1);
            }
        }
        
        // up
        if (i > 1) {
            int up = i-1;
            if (isOpen(up, j)) {
                weightedQuickUnionUF.union((up-1)*size+j-1, (i-1)*size+j-1);
            }
        } else {
            // connect top point to virtual top point
            weightedQuickUnionUF.union(unionSize, (i-1)*size+j-1); 
        }
        
        // down
        if (i < size) {
            int bottom = i+1;
            if (isOpen(bottom, j)) {
                weightedQuickUnionUF.union((i-1)*size+j-1, (bottom-1)*size+j-1);
            }
        } else {
            // connect bottom point to virtual bottom point
            weightedQuickUnionUF.union(unionSize+1, (i-1)*size+j-1); 
        }
        
        openCount++;
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {   
        if (i < 1 || i > size || j < 1 || j > size)
            throw new java.lang.IndexOutOfBoundsException();
        return sites[i-1][j-1];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        
        if (i < 1 || i > size || j < 1 || j > size)
            throw new java.lang.IndexOutOfBoundsException();
        return weightedQuickUnionUF.connected(unionSize, (i-1)*size+j-1);
    }
    
    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(unionSize, unionSize+1);
    }
}