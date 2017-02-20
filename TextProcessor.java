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
	static String str;
	static Map<Character, Node> root;

	public static void main(String[] args) {
		in.hasNext();
		str = in.next();
		int Q = in.nextInt();
		int W = in.nextInt();

		ArrayList<Answer> answers = new ArrayList<>();
		for (int i = 0; i < Q; i++)
			answers.add(new Answer(in.nextInt() + W - 2, i));
		Collections.sort(answers, new Comparator<Answer>() {
			@Override
			public int compare(Answer o1, Answer o2) {
				return o1.end - o2.end;
			}
		});

		Iterator<Answer> itr = answers.iterator();
		Answer nextToSolve = itr.next();
		root = new HashMap<>();
		Queue<Node> nodes = new LinkedList<>();
		for (int i = 0; i < str.length(); i++) {
			// addNode
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
			// removeNode
			if (i >= W) {
				nodes.poll().remove();
			}
			// output result
			if (i == nextToSolve.end) {
				long count = 0;
				for (Node x : nodes) {
					count += x.length();
				}
				nextToSolve.value = count;
				if (itr.hasNext())
					nextToSolve = itr.next();
			}
		}

		Collections.sort(answers, new Comparator<Answer>() {
			@Override
			public int compare(Answer o1, Answer o2) {
				return o1.index - o2.index;
			}
		});
		for (Answer a : answers)
			System.out.println(a.value);
	}

	static class Node {
		LinkedList<Character> s = new LinkedList<>();
		Node extend;
		Node parent;

		public Node(Node node) {
			extend = node;
			node.parent = this;
			s.offer(extend.s.poll());
		}

		public Node(char c) {
			s.offer(c);
		}

		public void addChar(char c) {
			if (extend != null) {
				if (extend.s.peek() == c) {
					s.offer(extend.s.poll());
					return;
				}
				extend = null;
			}
			s.offer(c);
		}

		public void remove() {
			if (parent != null) {
				parent.extend = null;
			} else {
				root.remove(s.peek());
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

	static class Answer {
		int end;
		int index;
		long value;

		public Answer(int end, int i) {
			this.end = end;
			this.index = i;
		}
	}
}
