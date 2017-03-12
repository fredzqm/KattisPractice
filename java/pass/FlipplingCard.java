package pass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/flippingcards
 * 
 * 6.8, graph
 * 
 * @author zhang
 *
 */
public class FlipplingCard {

	static class Pattern {
		List<Pattern> derived;

		public Pattern() {
			this.derived = new ArrayList<>();
		}

		void add(Pattern p) {
			derived.add(p);
		}

		public int size() {
			return derived.size();
		}

		public void remove() {
			this.derived.get(0).derived.remove(this);
		}
	}

	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numOfCase = in.nextInt();
		next: for (int tc = 0; tc < numOfCase; tc++) {
			int n = in.nextInt();

			Pattern[] pt = new Pattern[2 * n];
			for (int i = 0; i < n; i++) {
				int a = in.nextInt() - 1;
				int b = in.nextInt() - 1;
				if (pt[a] == null)
					pt[a] = new Pattern();
				if (pt[b] == null)
					pt[b] = new Pattern();
				Pattern pta = pt[a];
				Pattern ptb = pt[b];
				pta.add(ptb);
				ptb.add(pta);
			}
			LinkedList<Pattern> ptls = new LinkedList<>();
			for (int i = 0; i < 2 * n; i++) {
				if (pt[i] != null) {
					ptls.add(pt[i]);
				}
			}
			if (ptls.size() < n) {
				System.out.println("impossible");
				continue next;
			}
			boolean changed = true;
			while (changed) {
				changed = false;
				Iterator<Pattern> itr = ptls.iterator();
				while (itr.hasNext()) {
					Pattern p = itr.next();
					if (p.size() == 1) {
						changed = true;
						p.remove();
						itr.remove();
						n--;
					} else if (p.size() == 0) {
						changed = true;
						itr.remove();
					}
				}
			}
			if (ptls.size() < n) {
				System.out.println("impossible");
			} else {
				System.out.println("possible");
			}
		}
	}
}
