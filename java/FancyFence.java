import java.util.Scanner;

/**
 * https://open.kattis.com/problems/fancyeasy
 * 
 * @author zhang
 *
 */
public class FancyFence {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int N = in.nextInt();
		int M = in.nextInt();
		int K = in.nextInt();
		Node[] onions = new Node[N];
		for (int i = 0; i < N; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			onions[i] = new Node(x, y);
		}
		Node[] post = new Node[M];
		for (int i = 0; i < M; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			post[i] = new Node(x, y);
		}

		// int[][] malloc = new int[M][M];

	}

	public boolean out(Node a, Node b, Node c) {
		return a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y) > 0;
	}

	static class Node {
		private int x, y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}
}
