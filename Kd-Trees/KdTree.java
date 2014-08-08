import java.util.LinkedList;
import java.util.List;

public class KdTree {
	private static final boolean VERTICAL = true;
	private static final boolean HORIZONTAL = false;
	
	private int size = 0;
	private Node root;
	
	public KdTree() {
		// construct an empty set of points
		root = null;
	}
	public boolean isEmpty() {
		// is the set empty?
		return size == 0;
	}
	public int size() {
		// number of points in the set
		return size;
	}
	public void insert(Point2D p) {
		// add the point p to the set (if it is not already in the set)
		if (root == null) {
			root = new Node(p, VERTICAL, new RectHV(0, 0, 1, 1));
			size++;
			return;
		}
		Node cur = root;
		Node parent = null;
		boolean isChildInLeftOfParent = true;
		while (cur != null) {
			Point2D curPoint = cur.mPoint;
			// exists 
			if (curPoint.equals(p)) return;
			parent = cur;
			if (cur.mDirection == VERTICAL) {
				if (p.x() <= curPoint.x()) {
					cur = cur.left;
					isChildInLeftOfParent = true;
				} else {
					cur = cur.right;
					isChildInLeftOfParent = false;
				}
			} else {
				if (p.y() <= curPoint.y()) {
					cur = cur.left;
					isChildInLeftOfParent = true;
				} else {
					cur = cur.right;
					isChildInLeftOfParent = false;
				}
			}
		}
		if (isChildInLeftOfParent) {
			parent.left = new Node(p, !parent.mDirection, parent.minRect);
		} else {
			parent.right = new Node(p, !parent.mDirection, parent.maxRect);
		}
		size++;
	}
	
	public boolean contains(Point2D p) {
		// does the set contain the point p?
		Node cur = root;
		while (cur != null) {
			Point2D curPoint = cur.mPoint;
			// exists 
			if (curPoint.equals(p)) return true;
			if (cur.mDirection == VERTICAL) {
				if (p.x() <= curPoint.x()) {
					cur = cur.left;
				} else {
					cur = cur.right;
				}
			} else if (cur.mDirection == HORIZONTAL) {
				if (p.y() <= curPoint.y()) {
					cur = cur.left;
				} else {
					cur = cur.right;
				}
			}

		}
		return false;
	}
	
	public void draw() {
		// draw all of the points to standard draw
		draw(root);
	}
	
	private void draw(Node node) {
		if (node == null) return;
		Point2D curPoint = node.mPoint;
		StdDraw.setPenColor(0, 0, 0);
		StdDraw.setPenRadius(.01);
		curPoint.draw();
		StdDraw.setPenRadius();
		if (node.mDirection == VERTICAL) {
			StdDraw.setPenColor(255, 0, 0);
		} else {
			StdDraw.setPenColor(0, 0, 255);
		}
		StdDraw.line(node.maxRect.xmin(), node.maxRect.ymin(), node.minRect.xmax(), node.minRect.ymax());
		draw(node.left);
		draw(node.right);
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		// all points in the set that are inside the rectangle
		List<Point2D> list = new LinkedList<Point2D>();
		range(list, rect, root);
		return list;
	}
	
	private void range(List<Point2D> list, RectHV rect, Node curNode) {
		if (curNode == null) return;
		if (!rect.intersects(curNode.parentRect)) return;
		Point2D curPoint = curNode.mPoint;
		if (rect.contains(curPoint)) {
			list.add(curPoint);
		}
		range(list, rect, curNode.left);
		range(list, rect, curNode.right);
	}
	
	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to p; null if set is empty
		Point2D curNearestPoint = nearest(p, null, root);
		return curNearestPoint;
	}
	
	private Point2D nearest(Point2D p, Point2D nearestPoint, Node curNode) {
		if (curNode == null) return null;
		double curMinDistance = Double.MAX_VALUE;
		Point2D curNearestPoint = nearestPoint;
		if (curNearestPoint != null) {
			curMinDistance = p.distanceTo(curNearestPoint);
		}
		if (curMinDistance < curNode.parentRect.distanceTo(p)) return null;
		Point2D curPoint = curNode.mPoint;
		if (p.distanceTo(curPoint) < curMinDistance) {
			curNearestPoint = curPoint;
		}
		if (curNode.minRect.distanceTo(p) <= curNode.maxRect.distanceTo(p)) {
			Point2D tmp = nearest(p, curNearestPoint, curNode.left);
			if (tmp != null) curNearestPoint = tmp;
			tmp = nearest(p, curNearestPoint, curNode.right);
			if (tmp != null) curNearestPoint = tmp;
		} else {
			Point2D tmp = nearest(p, curNearestPoint, curNode.right);
			if (tmp != null) curNearestPoint = tmp;
			tmp = nearest(p, curNearestPoint, curNode.left);
			if (tmp != null) curNearestPoint = tmp;
		}
		return curNearestPoint;
	}
	
	private class Node {
		private Point2D mPoint;
		private boolean mDirection;
		private Node left;
		private Node right;
		private RectHV minRect;
		private RectHV maxRect;
		private RectHV parentRect;
		
		public Node(Point2D point, boolean direction, RectHV parentRect) {
			this.mPoint = point;
			this.mDirection = direction;
			this.left = null;
			this.right = null;
			this.parentRect = parentRect;
			if (this.mDirection == VERTICAL) {
				this.minRect = new RectHV(parentRect.xmin(), parentRect.ymin(), mPoint.x(), parentRect.ymax());
				this.maxRect = new RectHV(mPoint.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
			} else {
				this.minRect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), mPoint.y());
				this.maxRect = new RectHV(parentRect.xmin(), mPoint.y(), parentRect.xmax(), parentRect.ymax());
			}
		}
		
	}
}
