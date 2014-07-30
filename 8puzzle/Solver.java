import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Solver {
	private MinPQ<GameTree> pq = new MinPQ<GameTree>(new BoardComparator());
	private MinPQ<GameTree> twinPQ = new MinPQ<GameTree>(new BoardComparator());
	private GameTree previousGameTree = null;
	private GameTree previousTwinGameTree = null;
	private Board twinBoard;
	private int moves;
	private Iterable<Board> it;
	
	
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	twinBoard = initial.twin();
    	pq.insert(new GameTree(initial, 0, null));
    	twinPQ.insert(new GameTree(twinBoard, 0, null));
    	it = solution();
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
    	return this.moves != -1;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
    	return this.moves;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
    	if (it != null) return it;
    	while (!pq.isEmpty() && !twinPQ.isEmpty()) {
    		GameTree currentGameTree = pq.delMin();
    		GameTree currentTwinGameTree = twinPQ.delMin();
    		if (currentGameTree.board.isGoal()) {
    			List<Board> list = new LinkedList<Board>();
    			while (currentGameTree != null) {
    				list.add(0, currentGameTree.board);
    				currentGameTree = currentGameTree.parentTree;
    			}
    			this.moves = list.size()-1;
    			return list;
    		}
    		if (currentGameTree.board.isGoal()) {
    			this.moves = -1;
    			return null;
    		}
    		for (Board neighbor : currentGameTree.board.neighbors()) {
    			if (previousGameTree != null && neighbor.equals(previousGameTree.board)) continue;
    			pq.insert(new GameTree(neighbor, currentGameTree.deep + 1, currentGameTree));
    		}
    		for (Board neighbor : currentTwinGameTree.board.neighbors()) {
    			if (previousTwinGameTree != null && neighbor.equals(previousTwinGameTree.board)) continue;
    			twinPQ.insert(new GameTree(neighbor, currentTwinGameTree.deep + 1, currentTwinGameTree));
    		}
    		previousGameTree = currentGameTree;
    		previousTwinGameTree = currentTwinGameTree;
    	}
    	this.moves = -1;
    	return null;
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
    	public Board board;
    	public int deep;
    	public GameTree parentTree;
    	
    	public GameTree(Board b, int d, GameTree p) {
    		this.board = b;
    		this.deep = d;
    		this.parentTree = p;
    	}
    }
    
    private class BoardComparator implements Comparator<GameTree> {

		@Override
		public int compare(GameTree g1, GameTree g2) {
			return g1.board.manhattan() + g1.deep - g2.board.manhattan() - g2.deep;
		}
    	
    }
}