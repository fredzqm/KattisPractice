package pass;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * https://open.kattis.com/problems/sheldon
 * 
 * 5.6, brutal force
 * 
 * @author zhang
 *
 */
public class SheldonNumber {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		in.hasNextLine();
		BigInteger ai = in.nextBigInteger();
		BigInteger bi = in.nextBigInteger();
		
		String lower = ai.toString(2);
		String upper = bi.toString(2);
		
		int limit = upper.length() + 1;
		TreeSet<String> ls = new TreeSet<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int a = o1.length() - o2.length();
				if (a != 0)
					return a;
				return o1.compareTo(o2);
			}
		});
		
		StringBuilder A = new StringBuilder();
		for (int a = 1; a < limit; a++) {
			A.append('1');
			ls.add(A.toString());
			
			StringBuilder B = new StringBuilder();
			for (int b = 1; b < limit - a; b++) {
				B.append('0');
				
				StringBuilder sb = new StringBuilder(A);
				for (int rep = 1; rep < limit; rep += a + b) {
					sb.append(B);
					ls.add(sb.toString());
					sb.append(A);
					ls.add(sb.toString());
				}
			}
		}

		SortedSet<String> subSet = ls.subSet(lower, upper);
		
		int count = subSet.size();
		if (ls.contains(upper))
			count++;
		
		System.out.println(count);
	}

}
