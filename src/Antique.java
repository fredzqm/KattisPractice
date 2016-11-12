import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/antiques
 * 
 * @author zhang
 *
 */
public class Antique {
	public int s1;
	public int p1;
	public int s2;
	public int p2;

	static int n, m, k;
	static Antique[] ls;

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		String l = scan.nextLine();
		String[] f = l.split("\\s+");
		n = Integer.parseInt(f[0]);
		m = Integer.parseInt(f[1]);
		k = Integer.parseInt(f[2]);

		ls = new Antique[n];
		for (int i = 0; i < n; i++) {
			l = scan.nextLine();
			f = l.split("\\s+");
			ls[i] = new Antique();
			ls[i].s1 = Integer.parseInt(f[0]);
			ls[i].p1 = Integer.parseInt(f[1]);
			ls[i].s2 = Integer.parseInt(f[2]);
			ls[i].p2 = Integer.parseInt(f[3]);
		}
		ArrayList<Integer> visitedStore = new ArrayList<Integer>();
		boolean[] stores = new boolean[m];
		System.out.println(result(0, 0, stores, 0));
	}

	private static int result(int search, int price, boolean[] stores, int count) {
		if (count > k)
			return -1;
		if (search == n) {
			return price;
		}
		Antique an = ls[search];
		int r1, r2;
		if (!stores[an.s1 - 1]) {
			boolean[] stores2 = Arrays.copyOf(stores, m);
			stores2[an.s1 - 1] = true;
			r1 = result(search + 1, price + an.p1, stores2, count + 1);
		} else {
			r1 = result(search + 1, price + an.p1, stores, count);
		}

		if (!stores[an.s2 - 1]) {
			boolean[] stores2 = Arrays.copyOf(stores, m);
			stores2[an.s2 - 1] = true;
			r2 = result(search + 1, price + an.p2, stores2, count + 1);
		} else {
			r2 = result(search + 1, price + an.p2, stores, count);
		}
		
		if (r1 == -1)
			return r2;
		if (r2 == -1)
			return r1;
		return Math.min(r1, r2);
	}
}
