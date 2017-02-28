package pass;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/classy
 * 
 * 3.6, Graph, topographic ordering
 * 
 * @author zhang
 *
 */
public class AClassyProblem {
	static class People implements Comparable<People> {
		String name;
		String rank;

		public People(String name, String cls) {
			this.name = name.substring(0, name.length() - 1);
			rank = cls;
		}

		@Override
		public int compareTo(People o) {
			int c = rank.compareTo(o.rank);
			if (c != 0)
				return c;
			return name.compareTo(o.name);
		}

		@Override
		public String toString() {
			return name;
		}

		public void fillup(int max) {
			StringBuilder sb = new StringBuilder(rank);
			while (sb.length() < max) {
				sb.append(2);
			}
			rank = sb.toString();
		}
	}

	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numOfCase = in.nextInt();
		for (int tc = 0; tc < numOfCase; tc++) {
			int n = in.nextInt();
			in.nextLine();

			People[] ps = new People[n];
			int max = 0;
			for (int i = 0; i < n; i++) {
				String s = in.nextLine();
				String[] sp1 = s.split(" ");

				StringBuilder sb = new StringBuilder();
				String[] sp = sp1[1].split("-");
				for (String s2 : sp) {
					switch (s2) {
					case "upper":
						sb.append(1);
						break;
					case "middle":
						sb.append(2);
						break;
					case "lower":
						sb.append(3);
						break;
					default:
						throw new RuntimeException(s2);
					}
				}
				max = Math.max(max, sb.length());
				ps[i] = new People(sp1[0], sb.reverse().toString());
			}
			for (int i = 0; i < n; i++) {
				ps[i].fillup(max);
			}
			Arrays.sort(ps);
			for (int i = 0; i < n; i++) {
				System.out.println(ps[i]);
			}
			System.out.println("==============================");
		}
	}

}
