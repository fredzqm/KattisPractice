import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/textprocessor
 * 
 * 
 * @author zhang
 *
 */
public class TextProcessor {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		in.hasNext();
		String str = in.next();
		int Q = in.nextInt();
		int W = in.nextInt();
		int[] ques = new int[Q];
		for (int i = 0; i < Q; i++) {
			ques[i] = in.nextInt();
		}

		long[] x = solve(str, W, ques);

		for (long l : x)
			System.out.println(l);
	}

	public static long[] solve(String str, int W, int[] ques) {
		int Q = ques.length;
		ArrayList<Answer> answers = new ArrayList<>();
		for (int i = 0; i < Q; i++)
			answers.add(new Answer(ques[i] + W - 2, i));

		Collections.sort(answers, new Comparator<Answer>() {
			@Override
			public int compare(Answer o1, Answer o2) {
				return o1.end - o2.end;
			}
		});

		Iterator<Answer> itr = answers.iterator();
		Answer nextToSolve = itr.next();
		Map<Character, Node> root = new HashMap<>();
		Queue<Node> nodes = new LinkedList<>();
		for (int i = 0; i < str.length() + W; i++) {
			// removeNode
			if (i >= W) {
				Node removed = nodes.poll();
				if (removed.parent != null) {
					removed.parent.extend = null;
				} else {
					root.remove(removed.s.peek());
				}
			}
			// addNode
			if (i < str.length()) {
				char c = str.charAt(i);
				for (Node n : nodes)
					n.addChar(c);
				Node added;
				if (root.containsKey(c)) {
					Node o = root.get(c);
					added = new Node(o);
				} else {
					added = new Node(c);
				}
				root.put(c, added);
				nodes.offer(added);
			}
			// output result
			if (i == nextToSolve.end) {
				long count = 0;
				for (Node x : nodes) {
					count += x.length();
				}
				nextToSolve.value = count;
				while (itr.hasNext()) {
					nextToSolve = itr.next();
					if (i == nextToSolve.end)
						nextToSolve.value = count;
					else
						break;
				}
			}
		}

		Collections.sort(answers, new Comparator<Answer>() {
			@Override
			public int compare(Answer o1, Answer o2) {
				return o1.index - o2.index;
			}
		});

		long[] ans = new long[Q];
		for (int i = 0; i < Q; i++)
			ans[i] = answers.get(i).value;
		return ans;
	}

	private static void printTree(Map<Character, Node> root, Queue<Node> nodes, int i) {
		System.out.println("Index: " + i);
		for (Character c : root.keySet()) {
			System.out.println(" " + c + " ->");
			printNode(root.get(c), 2);
		}
		System.out.println(" Nodes");
		for (Node n : nodes) {
			printNode(n, 3);
		}
	}

	private static void printNode(Node n, int l) {
		for (int i = 0; i < l; i++)
			System.out.print(" ");
		for (Character c : n.s)
			System.out.print(c);
		if (n.parent != null) {
			System.out.print("  parent: ");
			for (Character c : n.parent.s)
				System.out.print(c);
		}
		System.out.println();
		if (n.extend != null)
			printNode(n.extend, l + n.s.size());
	}

	static class Node {
		LinkedList<Character> s = new LinkedList<>();
		Node extend;
		Node parent;

		public Node(Node node) {
			s.offer(node.s.poll());
			node.parent = this;
		}

		public Node(char c) {
			s.offer(c);
		}

		public void addChar(char c) {
			if (extend != null) {
				if (extend.s.peek() == c) {
					s.offer(extend.s.poll());
					return;
				} else {
					extend = null;
				}
			} else {
				s.offer(c);
			}
		}

		public int length() {
			return s.size();
		}

		@Override
		public String toString() {
			return s.toString();
		}
	}

	public static class Answer {
		int end;
		int index;
		long value;

		public Answer(int end, int i) {
			this.end = end;
			this.index = i;
		}
	}
}
