package DryRun1;
import java.util.Scanner;

/**
 * https://open.kattis.com/problems/recipes
 * 
 * P
 * @author zhang
 *
 */
public class ScalingRecipes {

	static class Item {
		String name;
		double scale;

		public Item(String string, double parseDouble) {
			name = string;
			scale = parseDouble / 100;
		}
	}

	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		int numOfCase = in.nextInt();
		for (int tc = 0; tc < numOfCase; tc++) {
			int n = in.nextInt();
			int a = in.nextInt();
			int b = in.nextInt();

			Item[] items = new Item[n];
			double weight = 0;
			in.nextLine();
			for (int i = 0; i < n; i++) {
				String s = in.nextLine();
				String[] sp = s.split(" ");
				double perc = Double.parseDouble(sp[2]);
				items[i] = new Item(sp[0], perc);
				if (perc == 100) {
					weight = Double.parseDouble(sp[1]);
				}
			}
			weight = weight * b / a;
			System.out.printf("Recipe # %d\n", tc+1);
			for (int i = 0; i < n; i++) {
				System.out.printf("%s %.1f\n", items[i].name, items[i].scale * weight);
			}
			System.out.println("----------------------------------------");
		}

	}
}
