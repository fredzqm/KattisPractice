package pass;

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * https://open.kattis.com/problems/gameofcards
 * 
 * 5.8, nimber (Grundy number), game like chess
 * 
 * @author zhang
 *
 */
public class GameOfCards {
	static Scanner in = new Scanner(System.in);
	static int K;

	public static void main(String[] args) {
		in.hasNext();
		int P = in.nextInt();
		K = in.nextInt();
		int value = 0;
		for (int pileNum = 0; pileNum < P; pileNum++) {
			int N = in.nextInt();
			State state = null;
			for (int i = 0; i < N; i++) {
				state = new State(state, in.nextInt());
			}
			value ^= state.getNimber();
		}
		if (value == 0) {
			System.out.println("Bob will win.");
		} else {
			System.out.println("Alice can win.");
		}
	}

	static class State {
		State below;
		int nimber;
		int number;

		public State(State state, int number) {
			this.below = state;
			this.number = number;

			TreeSet<Integer> ls = new TreeSet<>();
			State x = this;
			nextI: for (int i = 0; i <= K && x != null; i++) {
				State y = x;
				int cardNum = x.number;
				x = x.below;
				for (int j = 0; j < cardNum; j++) {
					if (y == null)
						continue nextI;
					y = y.below;
				}
				if (y != null)
					ls.add(y.getNimber());
				else
					ls.add(0);
			}
			this.nimber = 0;
			Iterator<Integer> itr = ls.iterator();
			while (itr.hasNext()) {
				if (nimber != itr.next())
					break;
				nimber++;
			}
		}

		public int getNimber() {
			return nimber;
		}

	}

}
