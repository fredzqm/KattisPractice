import java.util.Scanner;
import java.util.Arrays;

/**
 * https://open.kattis.com/problems/pachydermpeanutpacking
 * 
 * @author zhang
 *
 */
public class PPPacking {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {

		while (true) {
			int numB = in.nextInt();
			if (numB == 0)
				break;
			Box[] boxes = new Box[numB];
			for (int i = 0; i < numB; i++) {
				boxes[i] = new Box(in.nextDouble(), in.nextDouble(), in.nextDouble(), in.nextDouble(), in.next());
			}
			Arrays.sort(boxes);
			int numP = in.nextInt();
			for (int i = 0; i < numP; i++) {
				double x = in.nextDouble();
				double y = in.nextDouble();
				String where = in.next();
				String printWhere = search(numB, boxes, x, y);
				if (printWhere.equals(where))
					printWhere = "correct";
				System.out.println(where + " " + printWhere);
			}
			System.out.println();
		}
	}

	private static String search(int numB, Box[] boxes, double x, double y) {
		String printWhere = "floor";
		int i = 0, j = boxes.length;
		while (i < j - 1) {
			int m = (i + j) / 2;
			if (boxes[m].within(x, y)) {
				return boxes[m].where;
			}
			if (boxes[m].x1 <= x) {
				i = m;
			} else {
				j = m;
			}
		}
		j--;
		while (j >= 0) {
			if (boxes[j].within(x, y)) {
				return boxes[j].where;
			}
			j--;
		}
		return printWhere;
	}

	static class Box implements Comparable<Box> {
		double x1, y1, x2, y2;
		String where;

		public Box(double x1, double y1, double x2, double y2, String type) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.where = type;
		}

		boolean within(double x, double y) {
			return x1 <= x && x <= x2 && y1 <= y && y <= y2;
		}

		@Override
		public String toString() {
			return "Box [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + ", where=" + where + "]";
		}

		@Override
		public int compareTo(Box o) {
			return (int) (x1 - o.x1);
		}
	}

}
