package pass;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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

	static class Card {
		Pattern a;
		Pattern b;
		int id;

		public Card(Pattern a, Pattern b, int i) {
			this.a = a;
			this.b = b;
			id = i;
			a.add(this);
			b.add(this);
		}

		public void remove() {
			a.remove(this);
			b.remove(this);
		}
		
		@Override
		public String toString() {
			return ""+id;
		}
	}

	static class Pattern extends ArrayList<Card> {
		
	}

	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numOfCase = in.nextInt();
		next: for (int tc = 0; tc < numOfCase; tc++) {
			int n = in.nextInt();

			HashSet<Card> cs = new HashSet<>();
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
				new Card(pta, ptb, i);
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
						Card c = p.get(0);
						c.remove(); n--;
						itr.remove();
					} else if (p.size() == 0){
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
