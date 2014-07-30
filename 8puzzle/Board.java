import java.util.ArrayDeque;
import java.util.Queue;

public class Board {
	private int[][] mBlocks;	// private can be accessed by some Class
	private final int N;
	
    // construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
    	N = blocks.length;
    	this.mBlocks = new int[N][N];
    	for (int i = 0; i < N; i++) {
    		if (blocks[i].length != N) continue;
    		for (int j = 0; j < N; j++) {
    			this.mBlocks[i][j] = blocks[i][j];
    		}
    	}
    }
    
    // board dimension N
    public int dimension() {
    	return N;
    }
    
    // number of blocks out of place
    public int hamming() {
    	int count = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (mBlocks[i][j] == 0) continue;
				int value = i * N + j + 1;
				value = (i == N-1 && j == N-1)
						? 0
						: value;
				if (mBlocks[i][j] != value) count++;
			}
		}
		return count;
    }
    
    // 1 2 3 1 2 0 0 1 2
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
    	int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int di = (mBlocks[i][j] - 1) / N;
				di = (mBlocks[i][j] == 0)
					 ? N - 1
					 : di;
				int dj = (mBlocks[i][j] + N - 1) % N;
				int manhattanDistance = abs(di - i) + abs(dj - j);
				sum += manhattanDistance;
			}
		}
		return sum;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
    	if (hamming() == 0 || manhattan() == 0) return true;
    	return false;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N-1; j++) {
				if (mBlocks[i][j] != 0 && mBlocks[i][j+1] != 0) {
					swap(mBlocks, i, j, i, j+1);
					Board newBoard = new Board(mBlocks);
					swap(mBlocks, i, j, i, j+1);
					return newBoard;
				}
			}
		}
		return null;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
    	if (y == this) return true;
    	if (y instanceof Board) {
    		Board that = (Board) y;
    		if (that.N != this.N) return false;
    		for (int i = 0; i < N; i++) {
    			for (int j = 0; j < N; j++) {
    				if (mBlocks[i][j] != that.mBlocks[i][j]) return false;
    			}
    		}
    		return true;
    	}
    	return false;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
    	Queue<Board> q = new ArrayDeque<Board>();
    	for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (mBlocks[i][j] != 0) continue;
				if (i > 0) {
					swap(mBlocks, i, j, i-1, j);
					q.add(new Board(mBlocks));
					swap(mBlocks, i, j, i-1, j);
				}
				
				if (j > 0) {
					swap(mBlocks, i, j, i, j-1);
					q.add(new Board(mBlocks));
					swap(mBlocks, i, j, i, j-1);
				}
				
				if (i < N-1) {
					swap(mBlocks, i, j, i+1, j);
					q.add(new Board(mBlocks));
					swap(mBlocks, i, j, i+1, j);
				}
				
				if (j < N-1) {
					swap(mBlocks, i, j, i, j+1);
					q.add(new Board(mBlocks));
					swap(mBlocks, i, j, i, j+1);
				}
			}
    	}
    	return q;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(N + "\n");
    	for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sb.append(String.format("%2d ", mBlocks[i][j]));
			}
			sb.append("\n");
    	}
    	return sb.toString();
    }
    
    private int abs(int v) {
    	return v > 0
    		   ? v 
    		   : -v;
    }
    
    private void swap(int[][] blocks, int i, int j, int ii, int jj) {
    	if (i == ii && j == jj) return;
    	int tmp = blocks[i][j];
    	blocks[i][j] = blocks[ii][jj];
    	blocks[ii][jj] = tmp;
    }
}
