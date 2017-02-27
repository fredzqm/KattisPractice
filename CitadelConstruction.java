import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/citadelconstruction
 * 
 * Geometric, convexhull
 * 
 * @author zhang
 *
 */
public class CitadelConstruction {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numTestCase = in.nextInt();
		for (int tc = 0; tc < numTestCase; tc++) {
			int N = in.nextInt();
			List<Point> pts = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				int x = in.nextInt();
				int y = in.nextInt();
				pts.add(new Point(x, y));
			}
			ConvexHull convex = new ConvexHull(pts);
			N = convex.size();
			if (N < 3) {
				printOuput(0);
			} else if (N == 3) {
				int doubleArea = doubleArea(convex.get(0), convex.get(1), convex.get(2));
				printOuput(doubleArea);
			} else if (N == 4) {
				int doubleArea = doubleArea(convex.get(0), convex.get(1), convex.get(2))
						+ doubleArea(convex.get(2), convex.get(3), convex.get(0));
				printOuput(doubleArea);
			} else {
				ConvexHull.ConvexIterator a = convex.getConvexIteratorAt(0);
				ConvexHull.ConvexIterator b = convex.getConvexIteratorAt(1);
				ConvexHull.ConvexIterator c = convex.getConvexIteratorAt(2);
				ConvexHull.ConvexIterator d = convex.getConvexIteratorAt(3);
				boolean changed = true;
				int x = 0;
				while (changed) {
					changed = false;
//					System.out.println("" + (x++) + " "+changed+" 1\n" + a + "\n" + b + "\n" + c + "\n" + d);
					changed = changed || findMax(c.get(), d, a.get());
//					System.out.println("" + (x++) + " "+changed+" 2\n" + a + "\n" + b + "\n" + c + "\n" + d);
					changed = changed || findMax(d.get(), a, b.get());
//					System.out.println("" + (x++) + " "+changed+" 3\n" + a + "\n" + b + "\n" + c + "\n" + d);
					changed = changed || findMax(a.get(), b, c.get());
//					System.out.println("" + (x++) + " "+changed+" 4\n" + a + "\n" + b + "\n" + c + "\n" + d);
					changed = changed || findMax(b.get(), c, d.get());
//					System.out.println("" + (x++) + " " + changed + " 5\n" + a + "\n" + b + "\n" + c + "\n" + d);
				}
				int maxDoubleArea = doubleArea(a.get(), b.get(), c.get()) + doubleArea(c.get(), d.get(), a.get());
				printOuput(maxDoubleArea);
			}
		}
	}

	private static void printOuput(int doubleArea) {
		if (doubleArea % 2 == 0)
			System.out.println(doubleArea / 2);
		else
			System.out.println(doubleArea / 2 + ".5");
	}

	private static boolean findMax(Point a, ConvexHull.ConvexIterator itr, Point b) {
		int area = doubleArea(a, itr.get(), b);
		itr.previous();
		int preArea = doubleArea(a, itr.get(), b);
		itr.next();
		itr.next();
		int nextArea = doubleArea(a, itr.get(), b);
		itr.previous();
		if (area > preArea && area > nextArea) {
			return false;
		} else if (area > preArea && area == nextArea) {
			itr.next();
			return true;
		} else if (area == preArea && area > nextArea) {
			return false;
		} else if (preArea > area) {
			while (true) {
				itr.previous();
				int area2 = doubleArea(a, itr.get(), b);
				if (area2 < area) {
					itr.next();
					break;
				} else if (area2 == area) {
					break;
				}
				area = area2;
			}
		} else {
			while (true) {
				itr.next();
				int area2 = doubleArea(a, itr.get(), b);
				if (area2 <= area) {
					itr.previous();
					break;
				}
				area = area2;
			}
		}
		return true;
	}

	public static int doubleArea(Point a, Point b, Point c) {
		return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y);
	}

	static class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return String.format("(%d, %d)", x, y);
		}
	}

	/**
	 * import file from the root dictory and solve the convex hull problem with
	 * two difference methods
	 * 
	 * @author FredZhang
	 */
	public static class ConvexHull implements Iterable<Point> {
		private List<Point> convex;

		public ConvexHull(List<Point> points) {
			this.convex = run(points);
		}

		public Point get(int i) {
			return convex.get(i);
		}

		public int size() {
			return convex.size();
		}

		@Override
		public Iterator<Point> iterator() {
			return convex.iterator();
		}

		public ConvexIterator getConvexIteratorAt(int index) {
			return new ConvexIterator(index);
		}

		public class ConvexIterator {
			private int index;

			public ConvexIterator(int index) {
				this.index = index;
			}

			public void next() {
				index++;
				if (index == size())
					index = 0;
			}

			public void previous() {
				index--;
				if (index == -1)
					index = size() - 1;
			}

			public Point get() {
				return convex.get(index);
			}

			@Override
			public String toString() {
				return String.format("index: %d point: %s", index, get());
			}
		}

		/**
		 * Use quick hull method to solve this problem. Divide-and-conquer.
		 * Divide set into two parts by an exteme line and run the same
		 * algorithm on two sets separtely.
		 * 
		 * @param points
		 * @return
		 */
		private static List<Point> run(Collection<Point> points) {
			// get the left-most and right-most points in the set
			int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
			Point minP = null, maxP = null;
			for (Point p : points) {
				if (p.x < min) {
					min = p.x;
					minP = p;
				} else if (p.x == min) {
					if (p.y < minP.y)
						minP = p;
				}
				if (p.x > max) {
					max = p.x;
					maxP = p;
				} else if (p.x == max) {
					if (p.y < maxP.y)
						maxP = p;
				}
			}

			// recursively call helper method, and concatanate all vertice
			// together in order.
			List<Point> conv = new ArrayList<Point>();
			conv.add(minP);
			conv.addAll(VerticeOnRight(points, minP, maxP));
			conv.add(maxP);
			conv.addAll(VerticeOnRight(points, maxP, minP));
			return conv;
		}

		/**
		 * 
		 * @param points
		 * @param p1
		 * @param p2
		 * @return all vertece on the right side of P1P2
		 */
		private static List<Point> VerticeOnRight(Collection<Point> points, Point p1, Point p2) {
			List<Point> m = new ArrayList<Point>();
			int a = p2.y - p1.y;
			int b = p1.x - p2.x;
			int c = p1.x * p2.y - p1.y * p2.x;
			int max = 0;
			Point maxP = null;
			for (Point p : points) {
				int s = a * p.x + b * p.y - c;
				if (s > 0) {
					m.add(p);
					if (s > max) {
						max = s;
						maxP = p;
					} else if (s == max) {
						if (distsqr(p1, p) < distsqr(p1, maxP)) {
							max = s;
							maxP = p;
						}
					}
				}
			}
			List<Point> conv = new ArrayList<Point>();
			// basis case, no more points on the right side
			if (maxP == null)
				return conv;
			conv.addAll(VerticeOnRight(m, p1, maxP));
			conv.add(maxP);
			conv.addAll(VerticeOnRight(m, maxP, p2));
			return conv;
		}

		/**
		 * 
		 * @param a
		 * @param b
		 * @return the square of distance between a and b
		 */
		private static int distsqr(Point a, Point b) {
			return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
		}

	}

}
