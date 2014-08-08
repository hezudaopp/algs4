import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class PointSET {
	private Set<Point2D> set;
	
	public PointSET() {
		// construct an empty set of points
		set = new TreeSet<Point2D>();
	}
	public boolean isEmpty() {
		// is the set empty?
		return set.isEmpty();
	}
	public int size() {
		// number of points in the set
		return set.size();
	}
	public void insert(Point2D p) {
		// add the point p to the set (if it is not already in the set)
		set.add(p);
	}
	public boolean contains(Point2D p) {
		// does the set contain the point p?
		return set.contains(p);
	}
	public void draw() {
		// draw all of the points to standard draw
		for (Point2D point : set) {
			StdDraw.point(point.x(), point.y());
		}
		StdDraw.show();
	}
	public Iterable<Point2D> range(RectHV rect) {
		// all points in the set that are inside the rectangle
		List<Point2D> list = new LinkedList<Point2D>();
		for (Point2D point : set) {
			if (rect.contains(point)) {
				list.add(point);
			}
		}
		return list;
	}
	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to p; null if set is empty
		double minDistance = Double.MAX_VALUE;
		Point2D nearestPoint = null;
		for (Point2D point : set) {
			double curDistance = point.distanceTo(p);
			if (curDistance < minDistance) {
				nearestPoint = point;
				minDistance = curDistance;
			}
		}
		return nearestPoint;
	}
}
