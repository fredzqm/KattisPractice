import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
	private String str;
	private int W;
	private int addIndex;

	public TextProcessor(String str, int W) {
		this.str = str;
		this.W = W;
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
		Root root = new Root();
		// LinkedList<Node> nodes = new LinkedList<>();
		for (addIndex = 0; addIndex < str.length(); addIndex++) {
			long count = root.process();

			// printTree(root, i);
			if (addIndex == nextToSolve.end) {
				nextToSolve.value = count;
				while (itr.hasNext()) {
					nextToSolve = itr.next();
					if (addIndex == nextToSolve.end)
						nextToSolve.value = count;
					else
						break;
				}
				if (addIndex + W < nextToSolve.end) {
					root = new Root();
					addIndex = nextToSolve.end - W;
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

	private static void printTree(Root root, int i) {
		System.out.println("Index: " + i);
		System.out.print(" Trees:\n");
		System.out.println(root.toString());
	}

	class Node {
		private int start, end;
		private Map<Character, Node> map = new HashMap<>(1);
		private boolean active = true;

		public Node() {
			start = addIndex + 1;
			end = addIndex;
		}

		public int getStart() {
			return start;
		}

		public void setStart(int v) {
			start = v;
		}

		public int getEnd() {
			return end;
		}

		public char getStartChar() {
			return str.charAt(getStart());
		}

		public int size() {
			return getEnd() - getStart() + 1;
		}

		public void setActive(boolean x) {
			active = x;
		}

		public long process(int level) {
			level -= 1 + size();
			if (level <= 0)
				return 0;
			long ct = 0;
			Iterator<Map.Entry<Character, Node>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Character, Node> entry = it.next();
				Node n = entry.getValue();
				long c = n.process(level);
				if (c == 0) {
					it.remove();
				} else {
					ct += c;
				}
			}
			ct += 1 + size();

			char toBeAdd = str.charAt(addIndex);
			if (active) {
				if (map.containsKey(toBeAdd)) {
					Node e = map.get(toBeAdd);
					if (e.size() == 0) {
						e.setActive(true);
						setActive(false);
					} else if (map.size() == 1) {
						map.remove(toBeAdd);
						setStart(getStart() - getEnd() + addIndex - 1);
						end = addIndex;
						map.put(e.getStartChar(), e);
						e.setStart(e.getStart() + 1);
					} else {
						Node x = new Node();
						map.put(toBeAdd, x);
						x.map.put(e.getStartChar(), e);
						e.setStart(e.getStart() + 1);
						setActive(false);
					}
				} else {
					if (map.size() == 0) { // leaf
						setStart(getStart() - getEnd() + addIndex - 1);
						end = addIndex;
					} else { // create leaf
						setActive(false);
						map.put(toBeAdd, new Node());
					}
					ct++;
				}
			}
			if (!active && map.size() == 0)
				return 0;
			return ct;
		}

		@Override
		public String toString() {
			return "  " + toString("");
		}

		public String toString(String prefix) {
			StringBuilder sb = new StringBuilder();
			Iterator<Map.Entry<Character, Node>> it = map.entrySet().iterator();
			for (int i = getStart(); i <= getEnd(); i++) {
				sb.append(str.charAt(i));
				prefix += " ";
			}
			prefix += " ";
			if (active) {
				sb.append('*');
			} else {
				sb.append(' ');
			}
			if (it.hasNext()) {
				Map.Entry<Character, Node> entry = it.next();
				sb.append(entry.getKey() + entry.getValue().toString(""));
				prefix += " ";
				while (it.hasNext()) {
					entry = it.next();
					sb.append("\n" + prefix + entry.getKey() + entry.getValue().toString(prefix));
				}
			}
			return sb.toString();
		}
	}

	class Root {
		private Map<Character, Node> map = new HashMap<>(1);

		public long process() {
			long ct = 0;
			Iterator<Map.Entry<Character, Node>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Character, Node> entry = it.next();
				Node n = entry.getValue();
				long c = n.process(W);
				if (c == 0) {
					it.remove();
				} else {
					ct += c;
				}
			}

			char toBeAdd = str.charAt(addIndex);
			if (map.containsKey(toBeAdd)) {
				Node e = map.get(toBeAdd);
				if (e.size() == 0) {
					e.setActive(true);
				} else {
					Node x = new Node();
					map.put(toBeAdd, x);
					x.map.put(e.getStartChar(), e);
					e.setStart(e.getStart() + 1);
				}
			} else {
				map.put(toBeAdd, new Node());
				ct++;
			}
			return ct;
		}

		@Override
		public String toString() {
			return " " + toString("");
		}

		public String toString(String prefix) {
			StringBuilder sb = new StringBuilder();
			Iterator<Map.Entry<Character, Node>> it = map.entrySet().iterator();
			if (it.hasNext()) {
				Map.Entry<Character, Node> entry = it.next();
				sb.append(entry.getKey() + entry.getValue().toString(""));
				prefix += " ";
				while (it.hasNext()) {
					entry = it.next();
					sb.append("\n" + prefix + entry.getKey() + entry.getValue().toString(prefix));
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
}
