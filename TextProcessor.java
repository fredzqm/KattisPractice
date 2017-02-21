import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * https://open.kattis.com/problems/textprocessor
 * 
 * https://gist.github.com/makagonov/22ab3675e3fc0031314e8535ffcbee2c
 * http://stackoverflow.com/questions/9452701/ukkonens-suffix-tree-algorithm-in-plain-english
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
	private long count, leafCount;

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
		count = 0;
		leafCount = 0;
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
			root.active = true;
			acitveNodes.add(n);
			if (n.isLeaf())
				leafCount++;
			count += leafCount;
			// System.out.println(this);
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
		return String.format("Index: %d\ncount: %d\nleafCount: %d\nTrees:%s ", last, count, leafCount, root);
	}

	class Node {
		private boolean active = true;
		private Node parent;
		private int start;
		private Map<Character, Node> map;

		/**
		 * create a root
		 */
		public Node() {
			map = new TreeMap<>();
		}

		/**
		 * create a leaf
		 * 
		 * @param start
		 */
		public Node(Node parent, int start) {
			this.parent = parent;
			this.start = start;
		}

		/**
		 * create an interior node
		 * 
		 * @param start
		 * @param node
		 */
		public Node(Node parent, int start, Node node) {
			this(parent, start);
			map = new TreeMap<>();
			map.put(node.getStartChar(), node);
			node.parent = this;
		}

		public long removeAndGetLength() {
			long len = 1;
			if (isLeaf()) {
				len = last - start;
			}
			parent.map.remove(getStartChar());
			if (!parent.active && parent.map.size() == 0) {
				len += parent.removeAndGetLength();
			}
			return len;
		}

		public Node advance() {
			char toBeAdd = str.charAt(last);
			this.active = false;
			if (map.containsKey(toBeAdd)) {
				Node e = map.get(toBeAdd);
				if (e.isLeaf()) {
					e.start++;
					e = new Node(this, e.start - 1, e);
				} else {
					e.active = true;
				}
				map.put(toBeAdd, e);
				return e;
			} else {
				Node n = new Node(this, last);
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
			return toString("");
		}

		public String toString(String prefix) {
			if (isLeaf())
				return "\n" + prefix + str.substring(start, last + 1) + "*";
			StringBuilder sb = new StringBuilder("\n" + prefix);
			if (parent != null) {
				sb.append(getStartChar());
				prefix += ' ';
			}
			if (active)
				sb.append('*');
			else
				sb.append(' ');
			prefix += " ";
			for (Node n : map.values()) {
				sb.append(n.toString(prefix));
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
