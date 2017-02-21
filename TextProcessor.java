import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/textprocessor
 * 
 * @author zhang
 *
 */
public class TextProcessor {
	static Scanner in = new Scanner(System.in);
	private final String str;
	private final int W;
	private int last;
	private Node root;
	private LinkedList<Node> acitveNodes;

	public TextProcessor(String str, int W) {
		this.str = str;
		this.W = W;
	}

	public long[] solve(int[] ques) {
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

		// initialize
		acitveNodes = new LinkedList<>();
		last = 0;
		root = new Node();
		long count = 0;
		long leafCount = 0;
		while (last < str.length()) {
			// remove
			if (last >= W) {
				Node n = acitveNodes.remove();
				count -= n.removeAndGetLength();
				leafCount--;
			}
			// update interior nodes
			ListIterator<Node> nitr = acitveNodes.listIterator();
			while (nitr.hasNext()) {
				Node n = nitr.next();
				if (!n.isLeaf()) {
					n = n.advance();
					nitr.set(n);
					if (n.isLeaf())
						leafCount++;
				}
			}
			// add new node from root
			Node n = root.advance();
			acitveNodes.add(n);
			if (n.isLeaf())
				leafCount++;
			count += leafCount;
			System.out.println(this);
			if (last == nextToSolve.end) {
				nextToSolve.value = count;
				while (itr.hasNext()) {
					nextToSolve = itr.next();
					if (last == nextToSolve.end)
						nextToSolve.value = count;
					else
						break;
				}
			}
			last++;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Index: " + last + "\n");
		sb.append("Trees: \n" + root.toString());
		return sb.toString();
	}

	class Node {
		private int start;
		private Map<Character, Node> map;

		/**
		 * create a root
		 */
		public Node() {
			map = new HashMap<>();
		}

		/**
		 * create a leaf
		 * 
		 * @param start
		 */
		public Node(int start) {
			this.start = start;
		}

		/**
		 * create an interior node
		 * 
		 * @param start
		 * @param node
		 */
		public Node(int start, Node node) {
			this.start = start;
			map = new HashMap<>();
			map.put(node.getStartChar(), node);
		}

		public long removeAndGetLength() {
			int len = last - start;
			return len;
		}

		public Node advance() {
			char toBeAdd = str.charAt(last);
			if (map.containsKey(toBeAdd)) {
				Node e = map.get(toBeAdd);
				if (e.isLeaf()) {
					e.start++;
					e = new Node(e.start - 1, e);
				}
				map.put(toBeAdd, e);
				return e;
			} else {
				Node n = new Node(last);
				map.put(toBeAdd, n);
				return n;
			}
		}

		public boolean isLeaf() {
			return map == null;
		}

		public char getStartChar() {
			return str.charAt(start);
		}

		@Override
		public String toString() {
			return "  " + toString("");
		}

		public String toString(String prefix) {
			StringBuilder sb = new StringBuilder();
			if (!isLeaf()) {
				sb.append(getStartChar());
				Iterator<Map.Entry<Character, Node>> it = map.entrySet().iterator();
				if (it.hasNext()) {
					Map.Entry<Character, Node> entry = it.next();
					sb.append(" " + entry.getValue().toString(""));
					prefix += "  ";
					while (it.hasNext()) {
						entry = it.next();
						sb.append("\n" + prefix + entry.getValue().toString(prefix));
					}
				}
			} else {
				for (int i = start; i <= last; i++) {
					sb.append(str.charAt(i));
				}
			}
			return sb.toString();
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

	public static void main(String[] args) {
		in.hasNext();
		String str = in.next();
		int Q = in.nextInt();
		int W = in.nextInt();
		int[] ques = new int[Q];
		for (int i = 0; i < Q; i++) {
			ques[i] = in.nextInt();
		}
		TextProcessor textProcessor = new TextProcessor(str, W);

		long[] x = textProcessor.solve(ques);

		for (long l : x)
			System.out.println(l);
	}
}
