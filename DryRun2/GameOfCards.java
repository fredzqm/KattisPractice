package DryRun2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * https://open.kattis.com/problems/gameofcards
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
		List<Pile> piles = new ArrayList<>();
		for (int pileNum = 0; pileNum < P; pileNum++) {
			int N = in.nextInt();
			piles.add(new Pile(N));
		}

		int value = 0;
		for (Pile p : piles)
			value ^= p.process();
		if (value == 0){
			System.out.println("Bob will win.");
		}else{
			System.out.println("Alice can win.");
		}
	}

	static class Pile {
		int[] cards;
		ArrayList<State> states;

		public Pile(int n) {
			cards = new int[n];
			states = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				cards[i] = in.nextInt();
				states.add(new State(i));
			}
			states.add(new State(n));
		}

		public int process() {
			return states.get(states.size() - 1).solve();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("\n");
			sb.append("cards: " + Arrays.toString(cards));
			sb.append("  states: " + states);
			return sb.toString();
		}

		class State {
			int left;
			int nimber = -1;
			ArrayList<State> nextStates = new ArrayList<>();

			public State(int left) {
				this.left = left;
				for (int i = 0; i <= K; i++) {
					if (left - i < 1)
						break;
					int cardValue = cards[left - i - 1];
					if (left - i - cardValue >= 0)
						nextStates.add(states.get(left - i - cardValue));
				}
			}

			public int solve() {
				if (nimber != -1)
					return nimber;
				TreeSet<Integer> ls = new TreeSet<>();
				for (State s: nextStates){
					ls.add(s.solve());
				}
				Iterator<Integer> itr = ls.iterator();
				nimber = 0;
				while(itr.hasNext()){
					if (nimber != itr.next())
						break;
					nimber++;
				}
				return nimber;
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder(left + " (" + nimber + ") =>");
				for (State s : nextStates)
					sb.append(" " + s.left);
				return sb.toString();
			}
		}

	}
}
