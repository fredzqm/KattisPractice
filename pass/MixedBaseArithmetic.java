package pass;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/mixedbasearithmetic
 * 
 * 5.4, number theory
 * 
 * @author zhang
 *
 */
public class MixedBaseArithmetic {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		while (in.hasNext()) {
			String ctStr = in.next();
			int inc = in.nextInt();
			Counter ct = null;
			for (int i = 0; i < ctStr.length(); i++) {
				ct = Counter.parse(ctStr.charAt(i), ct);
			}
			ct.increment(inc);
			System.out.println(ct.toString());
		}
	}

	static class Counter {
		private int base;
		private char start;
		private char value;
		private Counter prev;

		public Counter(int base, char start, char value, Counter prev) {
			this.base = base;
			this.start = start;
			this.value = value;
			this.prev = prev;
		}

		public void increment(int inc) {
			int remain = inc % base;
			int carry = inc / base;
			value += remain;
			if (value - start >= base) {
				carry++;
				value -= base;
			}
			if (carry == 0)
				return;
			if (prev == null) {
				if (base == 10)
					prev = new Counter(base, start, (char) (start + 1), null);
				else
					prev = new Counter(base, start, start, null);
				carry--;
			}
			prev.increment(carry);
		}

		static Counter parse(char c, Counter prev) {
			if (c >= '0' && c <= '9') {
				return new Counter(10, '0', c, prev);
			} else if (c >= 'a' && c <= 'z') {
				return new Counter(26, 'a', c, prev);
			} else if (c >= 'A' && c <= 'Z') {
				return new Counter(26, 'A', c, prev);
			}
			throw new RuntimeException();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			toString(sb);
			return sb.toString();
		}

		private void toString(StringBuilder sb) {
			if (prev != null)
				prev.toString(sb);
			sb.append(value);
		}

	}
}
