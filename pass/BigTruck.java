package pass;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/bigtruck
 * 
 * 3.7, Dijkstra
 * 
 * @author zhang
 *
 */
public class BigTruck {

	public static Scanner in = new Scanner(System.in);

	static class Node {
		int index;
		int pack;
		ArrayList<Edge> edges;

		int path;
		int weight;

		public Node(int i, int p) {
			index = i;
			pack = p;
			edges = new ArrayList<>();
			path = Integer.MAX_VALUE;
		}

		public void add(Node n, int d) {
			edges.add(new Edge(n, d));
		}

		public String toString() {
			return "" + index;
		}

		public boolean updateIfBetter(int dis, int wei) {
			if (dis > path) {
				return false;
			} else if (dis < path) {
				path = dis;
				weight = wei;
				return true;
			} else {
				if (wei <= weight)
					return false;
				weight = wei;
				return true;
			}
		}

	}

	public static class Edge {
		public Node to;
		public int dis;

		public Edge(Node n, int d) {
			to = n;
			dis = d;
		}
	}

	public static class NodeWrapper implements Comparable<NodeWrapper> {
		public Node node;
		public int path;
		public int weight;

		public NodeWrapper(Node n) {
			node = n;
			path = n.path;
			weight = n.weight;
		}

		@Override
		public int compareTo(NodeWrapper o) {
			int x = path - o.path;
			if (x != 0)
				return x;
			return weight - o.weight;
		}

		public boolean upToDate() {
			return path == node.path && weight == node.weight;
		}
	}

	public static void main(String[] args) {
		in.hasNext();
		int size = in.nextInt();
		Node[] p = new Node[size];
		for (int i = 0; i < size; i++) {
			p[i] = new Node(i, in.nextInt());
		}
		int s = in.nextInt();
		for (int i = 0; i < s; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			int d = in.nextInt();
			p[a - 1].add(p[b - 1], d);
			p[b - 1].add(p[a - 1], d);
		}

		PriorityQueue<NodeWrapper> pq = new PriorityQueue<>();
		p[0].path = 0;
		p[0].weight = p[0].pack;
		pq.add(new NodeWrapper(p[0]));
		while (!pq.isEmpty()) {
			NodeWrapper nw = pq.poll();
			if (!nw.upToDate())
				continue;

			Node x = nw.node;
			for (Edge e : x.edges) {
				Node y = e.to;
				int dis = x.path + e.dis;
				int weight = x.weight + y.pack;
				if (y.updateIfBetter(dis, weight)) {
					pq.add(new NodeWrapper(y));
				}
			}
		}
		if (p[size - 1].path == Integer.MAX_VALUE)
			System.out.println("impossible");
		else
			System.out.println(p[size - 1].path + " " + p[size - 1].weight);
	}
}
