import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/fire2
 * 
 * @author zhang
 *
 */
public class FireSimulator {
	static Scanner in = new Scanner(System.in);
	static final char ROOM = '.', WALL = '#', START = '@', FIRE = '*';

	private int W, H;
	private char[][] map;
	private Collection<Point> fireEdge;
	private Collection<Point> reachable;

	public FireSimulator(int W, int H, char[][] map) {
		this.W = W;
		this.H = H;
		this.map = map;
		this.fireEdge = new ArrayList<>();
		this.reachable = new ArrayList<>();
		for (int i = 0; i < W; i++) {
			for (int j = 0; j < H; j++) {
				switch (map[i][j]) {
				case START:
					reachable.add(new Point(i, j));
					break;
				case FIRE:
					fireEdge.add(new Point(i, j));
				default:
					break;
				}
			}
		}
	}

	public int solve() {
		int time = 1;
		while (!fireEdge.isEmpty() || !reachable.isEmpty()) {
			Collection<Point> reach = new ArrayList<>();
			Collection<Point> fire = new ArrayList<>();
			for (Point f : fireEdge) {
				for (Point a : f.getAdjacent()) {
					switch (a.getValue()) {
					case ROOM:
					case START:
						a.set(FIRE);
						fire.add(a);
						break;
					default:
						break;
					}
				}
			}
			fireEdge = fire;
			for (Point r : reachable) {
				if (r.isEdge())
					return time;
				for (Point a : r.getAdjacent()) {
					switch (a.getValue()) {
					case ROOM:
						reach.add(a);
						break;
					default:
						break;
					}
				}
			}
			reachable = reach;
			time++;
		}
		return -1;
	}

	class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public char getValue() {
			return map[x][y];
		}

		public void set(char fire) {
			map[x][y] = fire;
		}

		public Iterable<Point> getAdjacent() {
			ArrayList<Point> ls = new ArrayList<>(4);
			if (x != 0)
				ls.add(new Point(x - 1, y));
			if (x != W - 1)
				ls.add(new Point(x + 1, y));
			if (y != 0)
				ls.add(new Point(x, y - 1));
			if (y != H - 1)
				ls.add(new Point(x, y + 1));
			return ls;
		}

		public boolean isEdge() {
			if (x == 0)
				return true;
			if (x == W - 1)
				return true;
			if (y == 0)
				return true;
			if (y == H - 1)
				return true;
			return false;
		}
	}

	public static void main(String[] args) {
		int numTestCase = in.nextInt();
		for (int tc = 0; tc < numTestCase; tc++) {
			int H = in.nextInt();
			int W = in.nextInt();
			char[][] map = new char[W][H];
			for (int i = 0; i < W; i++) {
				String s = in.next();
				for (int j = 0; j < H; j++) {
					map[i][j] = s.charAt(j);
				}
			}

			FireSimulator fireSimulator = new FireSimulator(W, H, map);
			int time = fireSimulator.solve();
			if (time < 0)
				System.out.println("IMPOSSIBLE");
			else
				System.out.println(time);
		}
	}
}
