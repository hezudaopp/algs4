import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Solver {
	private MinPQ<GameTree> pq = new MinPQ<GameTree>(new BoardComparator());
	private MinPQ<GameTree> twinPQ = new MinPQ<GameTree>(new BoardComparator());
	private int moves;
	private boolean solvable;
	private GameTree goal;
	
	
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	Board twinBoard = initial.twin();
    	pq.insert(new GameTree(initial, 0, null));
    	twinPQ.insert(new GameTree(twinBoard, 0, null));
    	this.solvable = solve();
    }
    
    private boolean solve() {
    	while (true) {
    		GameTree current = pq.delMin();
    		
    		if (current.board.isGoal()) {
    			this.goal = current;
    			this.moves = this.goal.deep;
    			this.solvable = true;
    			return true;
    		}
    		
    		for (Board neighbor : current.board.neighbors()) {
    			GameTree gameTree = new GameTree(neighbor, current.deep + 1, current);
    			if (gameTree.equals(current.parentTree)) {
    				continue;
    			}
    			pq.insert(gameTree);
    		}
    		
    		
    		GameTree twinCurrent = twinPQ.delMin();
    		
    		if (twinCurrent.board.isGoal()) {
    			this.goal = null;
    			this.moves = -1;
    			this.solvable = false;
    			return false;
    		}
    		
    		for (Board neighbor : twinCurrent.board.neighbors()) {
    			GameTree gameTree = new GameTree(neighbor, twinCurrent.deep + 1, twinCurrent);
    			if (gameTree.equals(twinCurrent.parentTree)) {
    				continue;
    			}
    			twinPQ.insert(gameTree);
    		}
    	}
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
    	return this.solvable;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
    	return this.moves;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
    	if (this.goal == null) return null;
    	List<Board> list = new LinkedList<Board>();
    	GameTree node = this.goal;
    	while (node != null) {
			list.add(0, node.board);
			node = node.parentTree;
		}
    	return list;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
    	// create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
    private class GameTree {
    	private Board board;
    	private int deep;
    	private GameTree parentTree;
    	private int hash = 0;
    	
    	public GameTree(Board b, int d, GameTree p) {
    		this.board = b;
    		this.deep = d;
    		this.parentTree = p;
    	}
    	
    	public boolean equals(Object that) {
            if (that == this) return true;
            if (that == null) return false;
            if (that.getClass() != this.getClass()) return false;
            
            GameTree tmp = (GameTree) that;
            if (tmp.board.equals(board)) return true;
            return false;
        }

    	public int hashCode() {
    		if (this.hash != 0) return this.hash;
    		this.hash = board.toString().hashCode();
    		return hash;
    	}
    }
    
    private class BoardComparator implements Comparator<GameTree> {

		@Override
		public int compare(GameTree g1, GameTree g2) {
			return g1.board.manhattan() + g1.deep - g2.board.manhattan() - g2.deep;
		}
    	
    }
}