import java.util.Scanner;

/**
 * https://open.kattis.com/problems/polygonarea
 * P
 * 
 * @author zhang
 *
 */
public class PolygonArea {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		while (true) {
			int num = in.nextInt();
			if (num == 0)
				break;
			Node[] nodes = new Node[num];
			for (int i = 0; i < num; i++) {
				nodes[i] = new Node(in.nextInt(), in.nextInt());
			}
			int area = 0;
			Node first = nodes[0];
			for (int i = 2; i < nodes.length; i++) {
				int a = triangleArea(first, nodes[i - 1], nodes[i]);
				area += a;
			}
			if (area > 0)
				System.out.println(String.format("CCW %.1f", area / 2.0));
			else
				System.out.println(String.format("CW %.1f", -area / 2.0));
		}
	}

	private static int triangleArea(Node a, Node b, Node c) {
		return (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));
	}

	public static class Node {
		int x, y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}
}
