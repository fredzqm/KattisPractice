package pass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * https://open.kattis.com/problems/bundles
 * 
 * 6.0, tree-shape dynamic programming
 * 
 * @author zhang
 *
 */
public class BundlesOfJoy {
	static Scanner in = new Scanner(System.in);

	static class Bundle implements Comparable<Bundle> {
		int price;
		int height;
		Set<Integer> items;
		ArrayList<Bundle> subBundles;

		public Bundle(int p, Set<Integer> its) {
			price = p;
			height = 0;
			items = its;
			subBundles = new ArrayList<>();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < height; i++) {
				sb.append("->");
			}
			sb.append(price + " " + items + "\n");
			for (Bundle b : subBundles) {
				sb.append(b);
			}
			return sb.toString();
		}

		private Bundle MergeBundle(Bundle o) {
			for (Integer i : o.items) {
				if (items.contains(i)) {
					o.height = height + 1;
					addSubBundle(o);
					return this;
				}
			}
			return null;
		}

		private void addSubBundle(Bundle o) {
			Bundle merged = null;
			for (int j = 0; j < subBundles.size(); j++) {
				Bundle oldBundle = subBundles.get(j);
				merged = oldBundle.MergeBundle(o);
				if (merged != null) {
					subBundles.set(j, merged);
					break;
				}
			}
			if (merged == null)
				subBundles.add(o);
		}

		public int findTheBestPrice() {
			int sum = 0;
			int num = 0;
			for (Bundle b : subBundles) {
				sum += b.findTheBestPrice();
				num += b.items.size();
			}
			if (items != null && num != items.size())
				return price;
			return Math.min(price, sum);
		}

		@Override
		public int compareTo(Bundle o) {
			return o.items.size() - items.size();
		}

	}

	public static void main(String[] args) {
		int numOfCase = in.nextInt();
		for (int tc = 0; tc < numOfCase; tc++) {
			int numType = in.nextInt();
			int numBundle = in.nextInt();
			List<Bundle> ls = new ArrayList<>();
			for (int i = 0; i < numBundle; i++) {
				int price = in.nextInt();
				int numItem = in.nextInt();
				Set<Integer> its = new HashSet<>();
				for (int j = 0; j < numItem; j++) {
					its.add(in.nextInt());
				}
				ls.add(new Bundle(price, its));
			}
			Collections.sort(ls);
			Bundle topBundle = new Bundle(Integer.MAX_VALUE, null);
			for (Bundle b : ls)
				topBundle.addSubBundle(b);
			int bestPrice = topBundle.findTheBestPrice();
			System.out.println(bestPrice);
		}
	}

}
